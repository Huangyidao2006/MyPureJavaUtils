package com.hj.thread;

public class HandlerThread extends Thread {
    private Looper mLooper;

    public HandlerThread(String name) {
        super(name);
    }

    public Looper getLooper() {
        if (mLooper == null) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return mLooper;
    }

    public void quit() {
        if (mLooper != null) {
            mLooper.cancel();
            mLooper = null;
        }
    }

    @Override
    public void run() {
        super.run();

        mLooper = Looper.prepare();

        synchronized (this) {
            notify();
        }

        mLooper.loop();
    }
}
