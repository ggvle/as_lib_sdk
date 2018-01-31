package com.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yline.log.LogFileUtil;
import com.yline.test.BaseTestFragment;
import com.yline.utils.FileSizeUtil;
import com.yline.utils.FileUtil;
import com.yline.utils.IOUtil;
import com.yline.utils.LogUtil;
import com.yline.utils.SPUtil;
import com.yline.utils.TimeConvertUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UtilFragment extends BaseTestFragment {
    private static final String TAG = "UtilFragment";

    @Override
    public void testStart(View view, Bundle savedInstanceState) {
        addButton("TimeConvertUtil", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long oldTime = 1490411992L * 1000;

                String result = TimeConvertUtil.stamp2FormatTime(oldTime);
                LogFileUtil.v("result = " + result);
            }
        });

        addButton("LogUtil", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLogUtil();
            }
        });

        addButton("LogFileUtil", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLogFileUtil();
            }
        });

        addButton("FileUtil", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testFileUtil();
            }
        });

        addButton("SPUtil", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testSPUtil(getContext());
            }
        });

        addButton("测试 IOUtil", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = getContext().getExternalFilesDir("test");
                File file = FileUtil.create(dir, "sample.txt");

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                    IOUtil.write(System.currentTimeMillis() + ";汉字;;", fileOutputStream);
                    IOUtil.close(fileOutputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void testSPUtil(Context context) {
        SPUtil.put(context, "null", null);
        String result = (String) SPUtil.get(context, "null", null);
        LogFileUtil.v(TAG, "put -> value = " + result);

        // 增加两条数据
        SPUtil.put(context, "username", "utilUsername");
        SPUtil.put(context, "password", "utilPassword");
        LogFileUtil.v(TAG, "put -> value - utilUsername");
        LogFileUtil.v(TAG, "put -> value - utilPassword");

        // 更新两条数据
        SPUtil.put(context, "username", "utilUpdateUsername");
        SPUtil.put(context, "password", "utilUpdatePassword");
        LogFileUtil.v(TAG, "put -> value - utilUpdateUsername");
        LogFileUtil.v(TAG, "put -> value - utilUpdatePassword");

        // 删除一条数据
        SPUtil.remove(context, "password");
        LogFileUtil.v(TAG, "remove -> key - password");

        // 获取两条数据
        String username = (String) SPUtil.get(context, "username", "");
        String password = (String) SPUtil.get(context, "password", "");
        LogFileUtil.v(TAG, "get -> key - username");
        LogFileUtil.v(TAG, "get -> key - password");
        LogFileUtil.i(TAG, "usrname = " + username + ",password = " + password);
    }

    private void testLogUtil() {
        LogUtil.v("test -> v");
        LogUtil.v("test -> v", LogUtil.LOG_LOCATION_PARENT);

        LogUtil.d("test -> d");
        LogUtil.d("test -> d", LogUtil.LOG_LOCATION_PARENT);

        LogUtil.i("test -> i");
        LogUtil.i("test -> i", LogUtil.LOG_LOCATION_PARENT);
        LogUtil.i("test -> i", new Exception("test -> i -> Exception"));
        LogUtil.i("test -> i", LogUtil.LOG_LOCATION_PARENT, new Exception("test -> i -> Exception"));

        LogUtil.w("test -> w");
        LogUtil.w("test -> w", LogUtil.LOG_LOCATION_PARENT);

        LogUtil.e("test -> e");
        LogUtil.e("test -> e", LogUtil.LOG_LOCATION_PARENT);
        LogUtil.e("test -> e", new Exception("test -> e -> Exception"));
        LogUtil.e("test -> e", LogUtil.LOG_LOCATION_PARENT, new Exception("test -> e -> Exception"));
    }

    private void testFileUtil() {
        LogUtil.v(TAG + " -> FileUtil.getPath() = " + FileUtil.getPathTop());

        String path = String.format("%s/%s", LogFileUtil.getLogDirPath(), "Utils");
        File fileDir = FileUtil.createDir(path);
        LogUtil.d(TAG + " -> createFileDir success, path = " + path);

        File file = FileUtil.create(fileDir, "log.txt");
        LogUtil.i(TAG + " -> createFile success");

        // 太多了,就会黑屏,因为大量占用了文件资源
        for (int i = 0; i < 1024; i++) {
            FileUtil.write(file, "content i = " + i);
        }

        int size = (int) FileSizeUtil.getFileSize(file);
        LogUtil.w(TAG + " -> getFileSize size = " + size);

        boolean renameResult = FileUtil.rename(fileDir, "log.txt", "log1.txt");
        LogUtil.w(TAG + " -> renameFile renameResult = " + renameResult);

        boolean deleteResult = FileUtil.delete(fileDir, "log.txt");
        LogUtil.e(TAG + " -> deleteFile deleteResult = " + deleteResult);

        String pathRoot = FileUtil.getPathRoot();
        LogUtil.v(TAG + " -> pathRoot = " + pathRoot);

        long blockSize = FileSizeUtil.getFileBlockSize(FileUtil.getPathTop());
        long availableSize = FileSizeUtil.getFileAvailableSize(FileUtil.getPathTop());
        LogUtil.v(TAG + " -> blockSize = " + blockSize + ", availableSize = " + availableSize);
        LogUtil.v(TAG + " -> blockSize = " + FileSizeUtil.formatFileAutoSize(blockSize)
                + ", availableSize = " + FileSizeUtil.formatFileAutoSize(availableSize));
    }

    private void testLogFileUtil() {
        LogFileUtil.m("m");

        LogFileUtil.v(TAG, "v");
        LogFileUtil.v(TAG, "v", LogFileUtil.LOG_LOCATION_PARENT);

        LogFileUtil.d(TAG, "d");
        LogFileUtil.d(TAG, "d", LogFileUtil.LOG_LOCATION_PARENT);

        LogFileUtil.i(TAG, "i");
        LogFileUtil.i(TAG, "i", LogFileUtil.LOG_LOCATION_PARENT);
        LogFileUtil.i(TAG, "i", new Exception("i -> Exception"));
        LogFileUtil.i(TAG, "i", LogFileUtil.LOG_LOCATION_PARENT, new Exception("i -> Exception"));

        LogFileUtil.w(TAG, "w");
        LogFileUtil.w(TAG, "w", LogFileUtil.LOG_LOCATION_PARENT);

        LogFileUtil.e(TAG, "e");
        LogFileUtil.e(TAG, "e", LogFileUtil.LOG_LOCATION_PARENT);
        LogFileUtil.e(TAG, "e", new Exception("e -> Exception"));
        LogFileUtil.e(TAG, "e", LogFileUtil.LOG_LOCATION_PARENT, new Exception("e -> Exception"));
    }
}
