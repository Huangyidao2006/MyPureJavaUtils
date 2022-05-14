package com.hj.thread;

public class Looper {
    private static final ThreadLocal<Looper> sLooper = new ThreadLocal<>();

    private final MessageQueue mQueue;

    private boolean mIsCancel;

    public Looper() {
        mQueue = new MessageQueue();
    }

    public MessageQueue getMessageQueue() {
        return mQueue;
    }

    public void cancel() {
        mIsCancel = true;
        mQueue.cancelPoll();
    }

    static Looper prepare() {
        Looper looper = new Looper();
        sLooper.set(looper);

        return looper;
    }

    static Looper myLooper() {
        return sLooper.get();
    }

    private long mPollTimeMs = 1000;

    public void loop() {
        while (!mIsCancel) {
            Message message = mQueue.poll(mPollTimeMs);
            if (message != null) {
                long now = System.currentTimeMillis();
                long diff = message.timeMs - now;

                if (diff <= 0) {
                    mQueue.pop();

                    if (message.callback != null) {
                        message.callback.run();
                    } else {
                        Handler handler = message.handler;
                        if (handler != null) {
                            handler.handleMessage(message);
                        }
                    }

                    message.handler = null;
                } else {
                    mPollTimeMs = diff;
                }
            } else {
                mPollTimeMs = 1000;
            }
        }

        sLooper.set(null);
    }
}
