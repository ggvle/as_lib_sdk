package com.yline.test;

import android.util.Log;

import java.util.Random;

public class UrlConstant {
    // 64_64_png
    public static final int Png_64_64_Max = 1;
    public static final String Png_64_64_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/64_64_png/%s.png";

    // 128_128_png
    public static final int Png_128_128_Max = 1;
    public static final String Png_128_128_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/128_128_png/%s.png";

    // 256_256_png
    public static final int Png_256_256_Max = 1;
    public static final String Png_256_256_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/256_256_png/%s.png";

    // 512_512_png
    public static final int Png_512_512_Max = 1;
    public static final String Png_512_512_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/512_512_png/%s.png";

    // 658_1382_jpg
    public static final int Jpg_658_1382_Max = 7;
    public static final String Jpg_658_1382_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/658_1382_jpg/%s.jpg";

    // 938_580_jpg
    public static final int Jpg_938_580_Max = 1;
    public static final String Jpg_938_580_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/938_580_jpg/%s.jpg";

    // 960_652_jpg
    public static final int Jpg_960_652_Max = 1;
    public static final String Jpg_960_652_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/960_652_jpg/%s.jpg";

    // 1024_1024_png
    public static final int Png_1024_1024_Max = 1;
    public static final String Png_1024_1024_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/1024_1024_png/%s.png";

    // 1440_900_jpg
    public static final int Jpg_1440_900_Max = 1;
    public static final String Jpg_1440_900_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/1440_900_jpg/%s.jpg";

    // 1920_1280_jpg
    public static final int Jpg_1920_1280_Max = 9;
    public static final String Jpg_1920_1280_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/1920_1280_jpg/%s.jpg";

    public static Random random = new Random();

    /**
     * 校验位置
     */
    private static int assertPosition(int position, int max) {
        if (position < 1) {
            position = position > 0 ? position : -position;
            return position % max + 1;
        }

        if (position > max) {
            return position % max + 1;
        }

        return position;
    }

    /**
     * 生成一个String
     *
     * @param format   上面定义的 Format
     * @param max      上面定义的 Max
     * @param position 位置
     * @return 可访问的png位置
     */
    public static String get(String format, int max, int position) {
        int newPosition = assertPosition(position, max);
        String resultString = String.format(format, String.format("%02d", newPosition));
        Log.i("xxx-url", "get: urlString = " + resultString);

        return resultString;
    }

    // 分文件夹
    public static String getPng_64_64(int position) {
        return get(Png_64_64_format, Png_64_64_Max, position);
    }

    public static String getPng_128_128(int position) {
        return get(Png_128_128_format, Png_128_128_Max, position);
    }

    public static String getPng_256_256(int position) {
        return get(Png_256_256_format, Png_256_256_Max, position);
    }

    public static String getPng_512_512(int position) {
        return get(Png_512_512_format, Png_512_512_Max, position);
    }

    public static String getJpg_938_580(int position) {
        return get(Jpg_938_580_format, Jpg_938_580_Max, position);
    }

    public static String getJpg_658_1382(int position) {
        return get(Jpg_658_1382_format, Jpg_658_1382_Max, position);
    }

    public static String getJpn_960_652(int position) {
        return get(Jpg_960_652_format, Jpg_960_652_Max, position);
    }

    public static String getPng_1024_1024(int position) {
        return get(Png_1024_1024_format, Png_1024_1024_Max, position);
    }

    public static String getJpn_1440_900(int position) {
        return get(Jpg_1440_900_format, Jpg_1440_900_Max, position);
    }

    public static String getJpn_1920_1280(int position) {
        return get(Jpg_1920_1280_format, Jpg_1920_1280_Max, position);
    }

    // 长方形
    public static String getUrlRec() {
        final int position = random.nextInt();
        final int caseIndex = position % 3;

        switch (caseIndex) {
            case 0:
                return getJpg_658_1382(position);
            case 1:
                return getJpg_938_580(position);
            case 2:
                return getJpn_960_652(position);
            case 3:
                return getJpn_1440_900(position);
            case 4:
                return getJpn_1920_1280(position);
            default:
                return getJpg_658_1382(position);
        }
    }

    // 正方形
    public static String getUrlSquare() {
        final int position = random.nextInt();
        final int caseIndex = position % 5;

        switch (caseIndex) {
            case 0:
                return getPng_64_64(position);
            case 1:
                return getPng_128_128(position);
            case 2:
                return getPng_256_256(position);
            case 3:
                return getPng_512_512(position);
            case 4:
                return getPng_1024_1024(position);
            default:
                return getPng_64_64(position);
        }
    }

    // 随机产生一个
    public static String getUrl() {
        final int position = random.nextInt();
        final int caseIndex = position % 8;

        switch (caseIndex) {
            case 0:
                return getPng_64_64(position);
            case 1:
                return getPng_128_128(position);
            case 2:
                return getPng_256_256(position);
            case 3:
                return getPng_512_512(position);
            case 4:
                return getJpg_938_580(position);
            case 5:
                return getJpn_960_652(position);
            case 6:
                return getPng_1024_1024(position);
            case 7:
                return getJpn_1920_1280(position);
            default:
                return getPng_64_64(position);
        }
    }
}
