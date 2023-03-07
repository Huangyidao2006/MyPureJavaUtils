package com.hj.utils.test;

import com.hj.utils.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hj at 2023/3/2 17:57
 * <p>
 * 批量文件测试类。
 */
public class BatchFileTester {
    /**
     * 监听器。
     */
    public interface TesterListener {
        void onReadStarted(FileItem item);

        void onReadData(FileItem item, byte[] data, int len);

        void onReadStopped(FileItem item, boolean isFinished);

        void onError(int error, String des);
    }

    /**
     * 文件项目。
     */
    public static class FileItem {
        public String mName = "";
        public String mPath = "";

        public FileItem(String name, String path) {
            mName = name;
            mPath = path;
        }
    }

    private final String mDir;

    private TesterListener mListener;

    private final Map<String, FileItem> mFileItemsMap = new HashMap<>();

    public BatchFileTester(String dir) {
        mDir = dir;
    }

    public void setListener(TesterListener listener) {
        mListener = listener;
    }

    public String getDir() {
        return mDir;
    }

    public Map<String, FileItem> listFileItems(boolean subDirs, String... suffix) {
        mFileItemsMap.clear();

        List<File> fileList = FileUtil.listFile(mDir, Arrays.asList(suffix), subDirs);
        for (File file : fileList) {
            FileItem item = new FileItem(file.getName(), file.getAbsolutePath());
            mFileItemsMap.put(item.mName, item);
        }

        return mFileItemsMap;
    }

    public Map<String, FileItem> getFileItems() {
        return mFileItemsMap;
    }

    public boolean hasFileItem() {
        return !mFileItemsMap.isEmpty();
    }

    public int getFileItemSize() {
        return mFileItemsMap.size();
    }

    public FileItem getFileItem(String filename) {
        return mFileItemsMap.get(filename);
    }

    public synchronized int startReading(String filename, int readLen, int intervalMs) {
        if (mReadThread != null) {
            return -1;
        }

        FileItem item = getFileItem(filename);
        if (item == null) {
            return -1;
        }

        mReadThread = new ReadThread(item, readLen, intervalMs);
        mReadThread.start();

        return 0;
    }

    public synchronized void stopReading() {
        if (mReadThread != null) {
            mReadThread.stopRun();
        }
    }

    private ReadThread mReadThread;

    private class ReadThread extends Thread {
        private boolean mNeedStop;

        private final FileItem mFileItem;

        private final int mReadLen;

        private final int mIntervalMs;

        public ReadThread(FileItem item, int readLen, int intervalMs) {
            mFileItem = item;
            mReadLen = readLen;
            mIntervalMs = intervalMs;
        }

        public void stopRun() {
            mNeedStop = true;
            interrupt();
        }

        @Override
        public void run() {
            try {
                FileInputStream fis = new FileInputStream(mFileItem.mPath);
                if (mListener != null) {
                    mListener.onReadStarted(mFileItem);
                }

                byte[] buffer = new byte[mReadLen];
                int readLen = 0;

                while (!mNeedStop && (readLen = fis.read(buffer)) != -1) {
                    if (mListener != null) {
                        mListener.onReadData(mFileItem, buffer, readLen);
                    }

                    try {
                        sleep(mIntervalMs);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (mListener != null) {
                    mListener.onReadStopped(mFileItem, readLen == -1);
                }
            } catch (Exception e) {
                e.printStackTrace();

                if (mListener != null) {
                    mListener.onError(-1, e.getMessage());
                }
            }

            mReadThread = null;
        }
    }
}
