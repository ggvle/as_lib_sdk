package com.yline.test

import java.util.ArrayList
import java.util.Random

/**
 * String 的常量
 *
 * @author yline 2017/9/19 -- 10:16
 * @version 1.0.0
 */
object StrConstant {
    // 常量定义; 都是12个
    private val oneStr = arrayOf("枫", "奔", "当", "梦", "痒", "美", "魂", "空", "海", "陆", "水", "冰")

    private val twoStr = arrayOf("绅士", "存在", "左耳", "天空", "退后", "心墙", "演员", "左边", "看海", "空港", "素颜", "笔记")

    private val threeStr = arrayOf("七里香", "我承认", "天亮了", "尘世美", "我知道", "美人鱼", "拆东墙", "我不配", "狮子座", "看不见", "我忘了", "小幸运")

    private val fourStr = arrayOf("清明雨上", "北京巷弄", "晴天娃娃", "开始懂了", "失落沙洲", "三度和弦", "三年二班", "七号公园", "流浪诗人", "开不了口", "传承乐章", "爱与妒忌")

    private val fiveStr = arrayOf("多余的解释", "红色高跟鞋", "你并不懂我", "一直很安静", "悄悄爱上你", "陪我去流浪", "下个路口见", "爱笑的眼睛", "会呼吸的痛", "如果这是爱", "可念不可说", "终于等到你")

    private val sixStr = arrayOf("你还要我怎样", "忘了时间的钟", "你幸福我幸福", "如果你也听说", "裂缝中的阳光", "谢谢你的温柔", "还能孩子多久", "说好的幸福呢", "北极星的眼泪", "我不会喜欢你", "只对你有感觉", "蒲公英的约定")

    private val sevenStr = arrayOf("分手是需要练习的", "走着走着就散了", "陈淑芬与林志豪", "看得最远的地方", "一不小心爱上你", "有没有人告诉你", "只是忽然很想你", "我知道你都知道", "我怕我会掉眼泪", "一千年后记得我", "你看不到的天空", "我唱着歌会想你")

    private val englishStr = arrayOf("Mine Mine", "Faded", "You Belong With Me", "Begin again", "Blank Space", "Try Everything", "Avril", "Taylor Swift", "The truth that you leave", "Everything In the world", "I really like you", "Drenched")

    private var random = Random()

    val mapArea: Map<String, List<String>>
        get() {
            val provinceMap = HashMap<String, List<String>>()
            provinceMap.put("北京市", listOf("北京"))
            provinceMap.put("天津市", listOf("天津"))
            provinceMap.put("黑龙江省", listOf("哈尔滨市", "齐齐哈尔市", "佳木斯市", "鹤岗市", "大庆市", "鸡西市", "双鸭山市", "伊春市", "牡丹江市", "黑河市", "七台河市", "绥化市和大兴安岭地区"))
            provinceMap.put("河北省", listOf("石家庄", "唐山", "邯郸", "保定", "沧州", "邢台", "廊坊", "承德", "张家口", "衡水", "秦皇岛"))
            provinceMap.put("山西省", listOf("大同", "朔州", "忻州", "太原", "阳泉", "晋中", "吕梁", "长治", "临汾", "晋城", "运城"))
            provinceMap.put("内蒙古自治区", listOf("呼和浩特市", "包头市", "乌海市", "赤峰市", "通辽市", "鄂尔多斯市", "呼伦贝尔市", "巴彦淖尔市", "乌兰察布市"))
            provinceMap.put("吉林省", listOf("长春市", "吉林市", "四平市", "辽源市", "通化市", "白山市", "白城市", "通化市", "松原市"))
            provinceMap.put("江西省", listOf("南昌", "九江", "赣州", "吉安", "萍乡", "鹰潭", "新余", "宜春", "上饶", "景德镇", "抚州"))
            provinceMap.put("海南省", listOf("海口市", "三亚市", "万宁市", "琼海市", "文昌市", "儋州市", "东方市", "五指山市．定安县", "乐东县", "澄迈县", "屯昌县", "临高县", "白沙黎族自治县"))
            provinceMap.put("云南省", listOf("昆明市", "曲靖市", "玉溪市", "昭通市", "楚雄市", "普洱市", "景洪市", "大理市", "保山市", "丽江市", "临沧市", "宣威市", "个旧市", "文山市", "安宁市", "瑞丽市", "芒市"))
            provinceMap.put("陕西省", listOf("铜川市", "宝鸡市", "咸阳市", "渭南市", "汉中市", "安康市", "商洛市", "延安市", "榆林市"))
            provinceMap.put("青海省", listOf("格尔木市", "西宁市", "玉树", "果洛", "海东", "海西", "海南", "海北"))

            return provinceMap
        }

    val intRandom: Int
        get() = random.nextInt()

    /**
     * @return 字符串
     */
    val stringByRandom: String
        get() = getStringByRandom(stringArrayByRandom)

    /**
     * 从自带的 几个字符串数组 随机出一个 字符串数组
     *
     * @return 字符串数组
     */
    val stringArrayByRandom: Array<String>
        get() {
            val number = random.nextInt(8)
            when (number) {
                0 -> return englishStr
                1 -> return oneStr
                2 -> return twoStr
                3 -> return threeStr
                4 -> return fourStr
                5 -> return fiveStr
                6 -> return sixStr
                7 -> return sevenStr
                else -> return englishStr
            }
        }

    fun getListOne(size: Int): List<String> {
        val result = ArrayList<String>()

        val cacheLength = oneStr.size
        for (i in 0 until size) {
            result.add(oneStr[i % cacheLength])
        }

        return result
    }

    fun getListTwo(size: Int): List<String> {
        val result = ArrayList<String>()

        val cacheLength = twoStr.size
        for (i in 0 until size) {
            result.add(twoStr[i % cacheLength])
        }

        return result
    }

    fun getListThree(size: Int): List<String> {
        val result = ArrayList<String>()

        val cacheLength = threeStr.size
        for (i in 0 until size) {
            result.add(threeStr[i % cacheLength])
        }

        return result
    }

    fun getListFour(size: Int): List<String> {
        val result = ArrayList<String>()

        val cacheLength = fourStr.size
        for (i in 0 until size) {
            result.add(fourStr[i % cacheLength])
        }

        return result
    }


    fun getListFive(size: Int): List<String> {
        val result = ArrayList<String>()

        val cacheLength = fiveStr.size
        for (i in 0 until size) {
            result.add(fiveStr[i % cacheLength])
        }

        return result
    }

    fun getListSix(size: Int): List<String> {
        val result = ArrayList<String>()

        val cacheLength = sixStr.size
        for (i in 0 until size) {
            result.add(sixStr[i % cacheLength])
        }

        return result
    }

    fun getListSeven(size: Int): List<String> {
        val result = ArrayList<String>()

        val cacheLength = sevenStr.size
        for (i in 0 until size) {
            result.add(sevenStr[i % cacheLength])
        }

        return result
    }

    fun getListEnglish(size: Int): List<String> {
        val result = ArrayList<String>()

        val cacheLength = englishStr.size
        for (i in 0 until size) {
            result.add(englishStr[i % cacheLength])
        }

        return result
    }

    fun getListRandom(size: Int): List<String> {
        val result = ArrayList<String>()

        for (i in 0 until size) {
            result.add(stringByRandom)
        }

        return result
    }

    fun getIntRandom(max: Int): Int {
        return random.nextInt(max)
    }

    /**
     * @param aimStringArray 目标数组
     * @return 其中某一个字符串
     */
    fun getStringByRandom(aimStringArray: Array<String>): String {
        val number = random.nextInt(12)
        return if (aimStringArray.size < 12) {
            "yline"
        } else aimStringArray[number]
    }
}
