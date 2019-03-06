package com.yline.utils.crypt;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 实现MD5加密
 *
 * @author yline 2018/8/28 -- 14:34
 */
public class MD5Utils {
    /* 算法 */
    private static final String ALGORITHM = "MD5";

    /**
     * 字符串 加密成 32位十六进制字符串
     *
     * @param valueBytes such as "yline".getBytes()
     * @return such as "971C47E6457B026249A1927AF4B4A93F"
     */
    public static String encrypt(byte[] valueBytes) {
        byte[] digestBytes = encryptInner(valueBytes);
        return HexUtils.encodeHex(digestBytes, false);
    }

    /**
     * 字符串 加密成 32位十六进制字符串
     *
     * @param valueBytes  such as "yline".getBytes()
     * @param toLowerCase 是否小写输出
     * @return such as "971C47E6457B026249A1927AF4B4A93F"
     */
    public static String encrypt(byte[] valueBytes, boolean toLowerCase) {
        byte[] digestBytes = encryptInner(valueBytes);
        return HexUtils.encodeHex(digestBytes, toLowerCase);
    }

    /**
     * MD5 加密
     *
     * @param valueBytes 原始数组
     * @return 加密后16位数组
     */
    private static byte[] encryptInner(byte[] valueBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            return digest.digest(valueBytes);
        } catch (NoSuchAlgorithmException neverHappened) {
            return null;
        }
    }

    /**
     * 文件 加密成 32位十六进制字符串
     *
     * @param file such as "sdcard/xmind_show.jpg"
     * @return such as "4B70C44EE2D7684BBBB8CDC18A971FF6"
     */
    public static String encrypt(File file) {
        FileInputStream in = null;
        FileChannel ch = null;
        try {
            in = new FileInputStream(file);
            ch = in.getChannel();

            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(byteBuffer);

            byte[] encodeBytes = digest.digest();
            return HexUtils.encodeHex(encodeBytes, false);
        } catch (IOException | NoSuchAlgorithmException neverHappened) {
            return null;
        } finally {
            closeQuietly(in);
            closeQuietly(ch);
        }
    }

    /**
     * 关闭IO流
     *
     * @param closeable IO流操作对象
     */
    private static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Throwable ignored) {
            }
        }
    }
}
