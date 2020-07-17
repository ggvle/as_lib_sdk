package com.yline.test

import android.util.Log

import java.util.Random

object UrlConstant {
    // 64_64_png
    val Png_64_64_Max = 1
    val Png_64_64_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/64_64_png/%s.png"

    // 128_128_png
    val Png_128_128_Max = 3
    val Png_128_128_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/128_128_png/%s.png"

    // 200_200_jpg
    val Jpg_200_200_Max = 71
    val Jpg_200_200_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/200_200_jpg/%s.jpg"

    // 256_256_png
    val Png_256_256_Max = 1
    val Png_256_256_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/256_256_png/%s.png"

    // 300_150_jpg
    val Jpg_300_150_Max = 1
    val Jpg_300_150_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/300_150_jpg/%s.jpg"

    // 420_300_png
    val Png_420_300_Max = 3
    val Png_420_300_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/420_300_png/%s.png"

    // 420_420_jpg
    val Jpg_420_420_Max = 8
    val Jpg_420_420_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/420_420_jpg/%s.jpg"

    // 480_640_jpg
    val Jpg_480_640_Max = 7
    val Jpg_480_640_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/480_640_jpg/%s.jpg"

    // 500_750_jpg
    val Jpg_500_750_Max = 5
    val Jpg_500_750_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/500_750_jpg/%s.jpg"

    // 640_480_jpg
    val Jpg_640_480_Max = 3
    val Jpg_640_480_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/640_480_jpg/%s.jpg"

    // 640_960_jpg
    val Jpg_640_960_Max = 5
    val Jpg_640_960_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/640_960_jpg/%s.jpg"

    // 640_1120_jpg
    val Jpg_640_1120_Max = 4
    val Jpg_640_1120_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/640_1120_jpg/%s.jpg"

    // 960_640_jpg
    val Jpg_960_640_Max = 8
    val Jpg_960_640_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/960_640_jpg/%s.jpg"

    // 1024_1024_png
    val Png_1024_1024_Max = 1
    val Png_1024_1024_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/1024_1024_png/%s.png"

    // 1360_768_jpg
    val Jpg_1366_768_Max = 3
    val Jpg_1366_768_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/1366_768_jpg/%s.jpg"

    // 1440_900_jpg
    val Jpg_1440_900_Max = 2
    val Jpg_1440_900_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/1440_900_jpg/%s.jpg"

    // 1920_1280_jpg
    val Jpg_1920_1280_Max = 9
    val Jpg_1920_1280_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/1920_1280_jpg/%s.jpg"

    // dynamic webp
    val Webp_Dynamic_Max = 1
    val Webp_Dynamic_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/dynamic_webp/%s.webp"

    // gif
    val Gif_Max = 4
    val Gif_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/gif/%s.gif"

    // static webp
    val Webp_Static_Max = 2
    val Webp_Static_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/static_webp/%s.webp"

    // static webp
    val Super_Big_Max = 6
    val Super_Big_format = "https://raw.githubusercontent.com/yline/as_lib_sdk/master/pic/super_big_jpg/%s.jpg"

    var random = Random()

    // 长方形
    val urlRec: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            val caseIndex = position % 11

            when (caseIndex) {
                0 -> return getJpg_300_150(position)
                1 -> return getPng_420_300(position)
                2 -> return getJpg_480_640(position)
                3 -> return getJpg_500_750(position)
                4 -> return getJpg_640_480(position)
                5 -> return getJpg_640_960(position)
                6 -> return getJpg_640_1120(position)
                7 -> return getJpg_960_640(position)
                8 -> return getJpg_1366_768(position)
                9 -> return getJpn_1440_900(position)
                10 -> return getJpn_1920_1280(position)
                else -> return getJpg_300_150(position)
            }
        }

    // 正方形
    val urlSquare: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            val caseIndex = position % 5

            when (caseIndex) {
                0 -> return getPng_64_64(position)
                1 -> return getPng_128_128(position)
                2 -> return getJpg_200_200(position)
                3 -> return getPng_256_256(position)
                4 -> return getJpg_420_420(position)
                5 -> return getPng_1024_1024(position)
                else -> return getJpg_200_200(position)
            }
        }

    // 随机产生一个
    val url: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            val caseIndex = position % 16

            when (caseIndex) {
                0 -> return getPng_64_64(position)
                1 -> return getPng_128_128(position)
                2 -> return getJpg_200_200(position)
                3 -> return getPng_256_256(position)
                4 -> return getJpg_300_150(position)
                5 -> return getPng_420_300(position)
                6 -> return getJpg_420_420(position)
                7 -> return getJpg_480_640(position)
                8 -> return getJpg_500_750(position)
                9 -> return getJpg_640_480(position)
                10 -> return getJpg_640_960(position)
                11 -> return getJpg_640_1120(position)
                12 -> return getJpg_960_640(position)
                13 -> return getPng_1024_1024(position)
                14 -> return getJpg_1366_768(position)
                15 -> return getJpn_1440_900(position)
                16 -> return getJpn_1920_1280(position)
                else -> return getJpg_200_200(position)
            }
        }

    // 衍生出来的
    val png_64_64: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getPng_64_64(position)
        }

    val png_128_128: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getPng_128_128(position)
        }

    val jpg_200_200: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_200_200(position)
        }

    val png_256_256: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getPng_256_256(position)
        }

    val jpg_300_150: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_300_150(position)
        }

    val png_420_300: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getPng_420_300(position)
        }

    val jpg_420_420: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_420_420(position)
        }

    val jpg_480_640: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_480_640(position)
        }

    val jpg_500_750: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_500_750(position)
        }

    val jpg_640_480: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_640_480(position)
        }

    val jpg_640_960: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_640_960(position)
        }

    val jpg_640_1120: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_640_1120(position)
        }

    val jpg_960_640: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_960_640(position)
        }

    val png_1024_1024: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getPng_1024_1024(position)
        }

    val jpg_1366_768: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpg_1366_768(position)
        }

    val jpn_1440_900: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpn_1440_900(position)
        }

    val jpn_1920_1280: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getJpn_1920_1280(position)
        }

    val gif: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return getGif(position)
        }

    val webp_Static: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return get(Webp_Static_format, Webp_Static_Max, position)
        }

    val webp_Dynamic: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return get(Webp_Dynamic_format, Webp_Dynamic_Max, position)
        }

    val super_Big: String
        get() {
            val position = random.nextInt(Integer.MAX_VALUE)
            return get(Super_Big_format, Super_Big_Max, position)
        }

    val avatar: String
        get() = jpg_200_200

    // 分文件夹
    fun getPng_64_64(position: Int): String {
        return get(Png_64_64_format, Png_64_64_Max, position)
    }

    fun getPng_128_128(position: Int): String {
        return get(Png_128_128_format, Png_128_128_Max, position)
    }

    fun getJpg_200_200(position: Int): String {
        return get(Jpg_200_200_format, Jpg_200_200_Max, position)
    }

    fun getPng_256_256(position: Int): String {
        return get(Png_256_256_format, Png_256_256_Max, position)
    }

    fun getJpg_300_150(position: Int): String {
        return get(Jpg_300_150_format, Jpg_300_150_Max, position)
    }

    fun getPng_420_300(position: Int): String {
        return get(Png_420_300_format, Png_420_300_Max, position)
    }

    fun getJpg_420_420(position: Int): String {
        return get(Jpg_420_420_format, Jpg_420_420_Max, position)
    }

    fun getJpg_480_640(position: Int): String {
        return get(Jpg_480_640_format, Jpg_480_640_Max, position)
    }

    fun getJpg_500_750(position: Int): String {
        return get(Jpg_500_750_format, Jpg_500_750_Max, position)
    }

    fun getJpg_640_480(position: Int): String {
        return get(Jpg_640_480_format, Jpg_640_480_Max, position)
    }

    fun getJpg_640_960(position: Int): String {
        return get(Jpg_640_960_format, Jpg_640_960_Max, position)
    }

    fun getJpg_640_1120(position: Int): String {
        return get(Jpg_640_1120_format, Jpg_640_1120_Max, position)
    }

    fun getJpg_960_640(position: Int): String {
        return get(Jpg_960_640_format, Jpg_960_640_Max, position)
    }

    fun getPng_1024_1024(position: Int): String {
        return get(Png_1024_1024_format, Png_1024_1024_Max, position)
    }

    fun getJpg_1366_768(position: Int): String {
        return get(Jpg_1366_768_format, Jpg_1366_768_Max, position)
    }

    fun getJpn_1440_900(position: Int): String {
        return get(Jpg_1440_900_format, Jpg_1440_900_Max, position)
    }

    fun getJpn_1920_1280(position: Int): String {
        return get(Jpg_1920_1280_format, Jpg_1920_1280_Max, position)
    }

    fun getGif(position: Int): String {
        return get(Gif_format, Gif_Max, position)
    }

    fun getWebp_Static(position: Int): String {
        return get(Webp_Static_format, Webp_Static_Max, position)
    }

    fun getWebp_Dynamic(position: Int): String {
        return get(Webp_Dynamic_format, Webp_Dynamic_Max, position)
    }

    fun getSuper_Big(position: Int): String {
        return get(Super_Big_format, Super_Big_Max, position)
    }

    // 基本方法

    /**
     * 校验位置
     */
    private fun assertPosition(position: Int, max: Int): Int {
        var position = position
        if (position < 1) {
            position = if (position > 0) position else -position
            return position % max + 1
        }

        return if (position > max) {
            position % max + 1
        } else position

    }

    /**
     * 生成一个String
     *
     * @param format   上面定义的 Format
     * @param max      上面定义的 Max
     * @param position 位置
     * @return 可访问的png位置
     */
    operator fun get(format: String, max: Int, position: Int): String {
        val newPosition = assertPosition(position, max)
        val resultString = String.format(format, String.format("%02d", newPosition))
        Log.i("xxx-url", "get: urlString = $resultString")

        return resultString
    }
}
