package com.hj.thread;

public class MessageQueue {
    private Message mHead;
    private Message mTail;

    public MessageQueue() {

    }

    public void clear(Handler handler) {
        synchronized (this) {
            if (mHead == null) {
                return;
            }

            Message cur = mHead;
            while (cur != null) {
                if (cur.handler == handler) {
                    if (cur.next != null) {
                        cur.next.pre = cur.pre;
                    } else {
                        mTail = cur.pre;
                    }

                    if (cur.pre != null) {
                        cur.pre.next = cur.next;
                    } else {
                        mHead = cur.next;
                    }
                }

                cur = cur.next;
            }
        }

        cancelPoll();
    }

    public synchronized void removeMessage(Handler handler, int what) {
        if (mHead == null) {
            return;
        }

        Message cur = mHead;
        while (cur != null) {
            if (cur.what == what && cur.handler == handler) {
                if (cur.next != null) {
                    cur.next.pre = cur.pre;
                } else {
                    mTail = cur.pre;
                }

                if (cur.pre != null) {
                    cur.pre.next = cur.next;
                } else {
                    mHead = cur.next;
                }
            }

            cur = cur.next;
        }
    }

    public synchronized void printMessages(Handler handler) {
        synchronized (this) {
            Message cur = mHead;
            while (cur != null) {
                if (handler == cur.handler) {
                    System.out.println(cur.toString());
                }

                cur = cur.next;
            }
        }
    }

    public void add(Message message, boolean atFront) {
        boolean needNotify = false;

        synchronized (this) {
            if (mHead == null) {
                // 插入空链表
                mHead = message;
                mTail = message;

                needNotify = true;
            } else {
                if (atFront || message.timeMs < mHead.timeMs) {
                    // 插到头部
                    message.next = mHead;
                    mHead.pre = message;
                    mHead = message;

                    needNotify = true;
                } else if (message.timeMs >= mTail.timeMs) {
                    // 插到尾部
                    message.pre = mTail;
                    mTail.next = message;
                    mTail = message;
                } else {
                    // 从尾向头找插入位置
                    Message cur = mTail;
                    while (cur != null) {
                        if (message.timeMs < cur.timeMs) {
                            cur = cur.pre;
                        } else {
                            break;
                        }
                    }

                    if (cur == null) {
                        // 插到头部
                        message.next = mHead;
                        mHead.pre = message;
                        mHead = message;

                        needNotify = true;
                    } else {
                        message.next = cur.next;
                        if (cur.next != null) {
                            cur.next.pre = message;
                        }

                        message.pre = cur;
                        cur.next = message;
                    }
                }
            }
        }

        if (needNotify) {
            synchronized (mSyncObj) {
                mSyncObj.notify();
            }
        }
    }

    public synchronized Message peek() {
        return mHead;
    }

    public synchronized Message pop() {
        if (mHead == null) {
            return null;
        }

        Message tmp = mHead;
        mHead = mHead.next;
        if (mHead != null) {
            mHead.pre = null;
        }

        if (mHead == null) {
            mTail = null;
        }

        return tmp;
    }

    public synchronized int size() {
        int count = 0;

        Message cur = mHead;
        while (cur != null) {
            count++;
            cur = cur.next;
        }

        return count;
    }

    private final Object mSyncObj = new Object();

    public void cancelPoll() {
        synchronized (mSyncObj) {
            mSyncObj.notify();
        }
    }

    public Message poll(long timeoutMs) {
        Message message = peek();
        if (message != null) {
            return message;
        }

        synchronized (mSyncObj) {
            try {
                mSyncObj.wait(timeoutMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return peek();
    }
}
