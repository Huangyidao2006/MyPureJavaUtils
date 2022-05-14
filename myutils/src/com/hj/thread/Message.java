package com.hj.thread;

public class Message {
    public int what;

    public int arg1;

    public int arg2;

    public Object obj;

    public Callback callback;

    long timeMs;

    Handler handler;

    Message pre;

    Message next;

    public Message(int _what) {
        what = _what;
    }

    public Message(int _what, Object _obj) {
        what = _what;
        obj = _obj;
    }

    @Override
    public String toString() {
        return "Message{" +
                "what=" + what +
                ", arg1=" + arg1 +
                ", arg2=" + arg2 +
                ", obj=" + obj +
                ", callback=" + callback +
                ", timeMs=" + timeMs +
                ", handler=" + handler +
                ", preHash=" + (pre == null ? null : pre.hashCode()) +
                ", curHash=" + hashCode() +
                ", nextHash=" + (next == null ? null : next.hashCode()) +
                '}';
    }
}
