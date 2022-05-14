package com.hj.buffer;

/**
 * Created by com.hj at 2022/4/11 22:10
 *
 * 循环队列缓存。
 */
public class QueueBuffer {
    private int mCapacity;
    private int mStart;
    private int mEnd;

    private byte[] mBuffer;

    public QueueBuffer(int capacity) {
        mCapacity = capacity;
        mBuffer = new byte[capacity + 1];
    }

    /**
     * 获取容量。
     *
     * @return
     */
    public int getCapacity() {
        return mCapacity;
    }

    /**
     * 获取缓存区。
     *
     * @return
     */
    public byte[] getBuffer() {
        return mBuffer;
    }

    /**
     * 队列是否为空。
     *
     * @return
     */
    public boolean isEmpty() {
        return mEnd == mStart;
    }

    /**
     * 队列是否已满。
     *
     * @return
     */
    public boolean isFull() {
        return mStart == (mEnd + 1 + mBuffer.length) % (mBuffer.length);
    }

    /**
     * 获取已用字节数。
     *
     * @return
     */
    public int getSize() {
        return (mEnd - mStart + mBuffer.length) % (mBuffer.length);
    }

    /**
     * 获取剩余字节数。
     *
     * @return
     */
    public int getLeft() {
        return getCapacity() - getSize();
    }

    /**
     * 从data的offset位置起，写入len字节数据到队列。
     *
     * @param data 待写入数据
     * @param offset 偏移量
     * @param len 待写入长度
     * @return 实际写入长度
     */
    public int write(byte[] data, int offset, int len) {
        if (offset + len > data.length) {
            return 0;
        }

        int left = getLeft();
        if (left == 0) {
            return 0;
        }

        int writeLen = Math.min(len, left);

        if (mEnd + writeLen <= mCapacity) {
            System.arraycopy(data, offset, mBuffer, mEnd, writeLen);
        } else {
            int writeLen1 = mCapacity - mEnd + 1;
            int writeLen2 = writeLen - writeLen1;

            System.arraycopy(data, offset, mBuffer, mEnd, writeLen1);
            System.arraycopy(data, offset + writeLen1, mBuffer, 0, writeLen2);
        }

        mEnd = (mEnd + writeLen + mBuffer.length) % mBuffer.length;

        return writeLen;
    }

    /**
     * 获取len个字节，存到buffer中起始位置为offset的区域。
     *
     * 注意：该操作不会使数据出队。
     *
     * @param buffer 缓存
     * @param offset 偏移量
     * @param len 待获取长度
     * @return 实际获取长度
     */
    public int get(byte[] buffer, int offset, int len) {
        if (offset + len > buffer.length) {
            return 0;
        }

        int size = getSize();
        if (size == 0) {
            return 0;
        }

        int readLen = Math.min(size, len);

        if (mStart + readLen <= mCapacity) {
            System.arraycopy(mBuffer, mStart, buffer, offset, readLen);
        } else {
            int readLen1 = mCapacity - mStart + 1;
            int readLen2 = readLen - readLen1;

            System.arraycopy(mBuffer, mStart, buffer, offset, readLen1);
            System.arraycopy(mBuffer, 0, buffer, offset + readLen1, readLen2);
        }

        return readLen;
    }

    /**
     * len字节数据出队
     *
     * @param len 数据长度
     * @return 实际出队长度
     */
    public int pop(int len) {
        int popLen = Math.min(len, getSize());
        mStart = (mStart + popLen + mBuffer.length) % mBuffer.length;

        return popLen;
    }

    /**
     * 从buffer中读取数据，相当于get + pop。
     *
     * @param buffer 缓存区域
     * @param offset 偏移量
     * @param len 待读取长度
     * @return 实际读取长度
     */
    public int read(byte[] buffer, int offset, int len) {
        int readLen = get(buffer, offset, len);

        return pop(readLen);
    }
}