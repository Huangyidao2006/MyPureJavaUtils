package com.hj.thread;

public class Handler {
    private final Looper mLooper;

    public Handler(Looper looper) {
        mLooper = looper;
    }

    public void handleMessage(Message message) {

    }

    public void removeMessage(int what) {
        if (mLooper != null) {
            mLooper.getMessageQueue().removeMessage(this, what);
        }
    }

    public void printMessages() {
        if (mLooper != null) {
            mLooper.getMessageQueue().printMessages(this);
        }
    }

    public void sendMessage(Message message) {
        sendMessageDelayed(message, 0);
    }

    public void sendMessageDelayed(Message message, long delayMillis) {
        if (mLooper != null) {
            message.timeMs = System.currentTimeMillis() + delayMillis;
            message.handler = this;
            mLooper.getMessageQueue().add(message, false);
        }
    }

    public void sendMessageToHead(Message message) {
        if (mLooper != null) {
            message.timeMs = 0;
            message.handler = this;
            mLooper.getMessageQueue().add(message, true);
        }
    }

    public void clear() {
        if (mLooper != null) {
            mLooper.getMessageQueue().clear(this);
        }
    }
}
