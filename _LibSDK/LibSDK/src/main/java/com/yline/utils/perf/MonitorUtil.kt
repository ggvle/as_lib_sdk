package com.yline.utils.perf

import android.app.ActivityManager
import android.content.Context
import android.os.*
import android.telephony.TelephonyManager
import android.text.format.Formatter
import com.yline.log.LogUtil
import java.io.*
import java.lang.StringBuilder
import java.math.BigDecimal
import java.net.InetAddress
import java.net.URL

object MonitorUtil {

    /**
     * SIM 卡运营商
     * such as : China Unicom
     */
    fun getOperatorName(context: Context?): String {
        val telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
        return telephonyManager?.simOperatorName ?: ""
    }

    /**
     * 通过 推流URL, 获取 IP
     */
    fun getCdnIp(url: String?): String? {
        var ip: String? = ""
        try {
            val uri = URL(url)
            val address = InetAddress.getByName(uri.host)
            if (address != null) {
                ip = address.hostAddress
            }
        } catch (e: Exception) {
            // do nothing
        }
        return ip
    }

    /**
     * 是否有 SIM 卡
     */
    fun hasSimCard(context: Context?): Boolean {
        val telMgr = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
        val simState = telMgr?.simState
        var result = false
        when (simState) {
            TelephonyManager.SIM_STATE_ABSENT -> result = false // 没有SIM卡
            TelephonyManager.SIM_STATE_UNKNOWN -> result = false
            else -> result = true
        }
        return result
    }

    /**
     * 是否是省电模式
     */
    fun getBatterySaveStatus(context: Context?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val powerManager = context?.getSystemService(Context.POWER_SERVICE) as PowerManager?
            return powerManager?.isPowerSaveMode ?: false
        }
        return false
    }

    /**
     * 获取当前的电量
     * such as : 80
     */
    fun getBattery(context: Context?): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager = context?.getSystemService(Context.BATTERY_SERVICE) as BatteryManager?
            val capacity = batteryManager?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            return capacity?.toString()
        }
        return null
    }

    /**
     * 2: 充电状态
     * 1: 非充电状态
     */
    fun getBatteryStatus(context: Context?): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val batteryManager = context?.getSystemService(Context.BATTERY_SERVICE) as BatteryManager?
            val status = batteryManager?.getIntProperty(BatteryManager.BATTERY_PROPERTY_STATUS)
            status?.let {
                return if (it == BatteryManager.BATTERY_STATUS_CHARGING) "2" else "1"
            }
            return ""
        }
        return ""
    }

    /**
     * 应用名称
     * such as : LibSDKDemo
     */
    fun getAppName(context: Context?): String {
        if (null == context) {
            return "***_***"
        }

        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return "***_***"
    }

    /**
     * 获取android总运行内存大小
     * such as : 7.97 GB
     */
    fun getTotalMemory(context: Context?): String {
        val str1 = "/proc/meminfo" // 系统内存信息文件
        var result: Long = 0    // 单位 kb
        try {
            val localFileReader = FileReader(str1)
            val localBufferedReader = BufferedReader(localFileReader, 8192)
            val memInfoSource = localBufferedReader.readLine() // 读取meminfo第一行，系统总内存大小

            // 获得系统总内存，单位是KB
            result = pickUpNumber(memInfoSource) * 1024
            localBufferedReader.close()
        } catch (e: IOException) {
        }
        return Formatter.formatFileSize(context, result) // Byte转换为 KB/MB/GB，内存大小规格化
    }

    /**
     * 获取 android APP 内存大小
     * such as : 100M
     */
    fun getAppMemory(context: Context?): String {
        var result: Long = 0    // 单位 kb
        try {
            val pid = Process.myPid()
            val reader = BufferedReader(InputStreamReader(FileInputStream("/proc/$pid/status")), 1000)
            var load: String
            while (reader.readLine().also { load = it } != null) {
                load = load.replace(" ", "")
                if (load.contains("VmRSS")) {
                    result = pickUpNumber(load) * 1024
                    break
                }
            }
            reader.close()
        } catch (ex: IOException) {
        }
        return Formatter.formatFileSize(context, result) // Byte转换为 KB/MB/GB，内存大小规格化
    }

    /**
     * 获取 内存使用百分比，和 Profile 保持一致
     * 42.11➗7598.87=0.55%
     * such as: 0.55%
     */
    fun getAppMemoryUsage(context: Context?): String {
        if (null == context) return "0%"

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try { // 统计进程的内存信息 totalPss
                val memInfoArray: Array<Debug.MemoryInfo>? = activityManager.getProcessMemoryInfo(intArrayOf(Process.myPid()))
                if (null == memInfoArray || memInfoArray.isEmpty()) {
                    return "0%"
                }

                val memInfo = memInfoArray[0]
                val total = memoryInfo.totalMem //  单位 bytes

                // 读取内存信息, 跟 Android Profiler 分析一致, 单位 kb
                val java_mem: String = memInfo.getMemoryStat("summary.java-heap")
                val native_mem: String = memInfo.getMemoryStat("summary.native-heap")
                val graphics_mem: String = memInfo.getMemoryStat("summary.graphics")
                val stack_mem: String = memInfo.getMemoryStat("summary.stack")
                val code_mem: String = memInfo.getMemoryStat("summary.code")
                val others_mem: String = memInfo.getMemoryStat("summary.system")

                val dalvikPss: Long = (string2Long(java_mem) + string2Long(native_mem)
                        + string2Long(graphics_mem) + string2Long(stack_mem)
                        + string2Long(code_mem) + string2Long(others_mem))

                val totalMB = total * 1.0f / 1024 / 1024    // 单位：MB
                val memMB = dalvikPss * 1.0f / 1024     //  单位: MB
                LogUtil.v(String.format("detail: %.2f➗%.2f=%.2f%%", memMB, totalMB, (memMB / totalMB * 100)))
                return String.format("%.2f%%", (memMB / totalMB * 100))
            } catch (e: java.lang.Exception) {
                return "0%"
            }
        }
        return "0%"
    }

    /**
     * 获取当前可用内存大小/总内存（MB）
     * such as : 35.86%
     */
    fun getSysMemoryUsage(context: Context?): String {
        if (null == context || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return "0%%"
        }

        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        val avail = mi.availMem * 1.0f / 1024 / 1024    // 单位：MB
        val total = mi.totalMem * 1.0f / 1024 / 1024    // 单位：MB
        val usage = total - avail
        return String.format("%.2f%%", (usage / total * 100))
    }

    private fun string2Long(source: String): Long {
        try {
            return source.toLong()
        } catch (ex: java.lang.Exception) {
            return 0L
        }
    }

    /**
     * MomTotal: 12345K -> 12345
     */
    private fun pickUpNumber(source: String): Long {
        val numBuilder = StringBuilder()
        var start = false
        for (ch in source) {
            if (start) {
                if (ch in '0'..'9') {
                    numBuilder.append(ch)
                } else {
                    break   // 结束
                }
            } else {
                if (ch in '0'..'9') {   // 开始计数
                    start = true
                    numBuilder.append(ch)
                }
            }
        }

        try {
            return numBuilder.toString().toLong()
        } catch (ex: java.lang.Exception) {
            return 0L
        }
    }

    /**
     * 当前进程内存上限，单位：MB
     * such as : 384
     */
    fun getMemoryMax(): String {
        return (Runtime.getRuntime().maxMemory() / 1024 / 1024).toString()
    }

    /**
     * 进程 当前使用的总内存，单位：MB
     * such as: 44.58
     */
    fun getPidMemorySize(pid: Int, context: Context?): String {
        if (null == context) return "0"

        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val myMempid = intArrayOf(pid)
        val memoryInfoArray = activityManager.getProcessMemoryInfo(myMempid)
        if (null == memoryInfoArray || memoryInfoArray.isEmpty()) {
            return "0"
        }

        val memSize = memoryInfoArray[0].totalPss
        return String.format("%.2f", memSize * 1.0f / 1024)
    }

    /* ----------------------------------- CPU 值获取一直为空 -------------------------------------- */
    fun getAppCpuRate(): String {
        val totalCpuTime1 = getTotalCpuTime().toFloat()
        val processCpuTime1 = getAppCpuTime().toFloat()
        try {
            Thread.sleep(360)
        } catch (e: java.lang.Exception) {
        }
        val totalCpuTime2 = getTotalCpuTime().toFloat()
        val processCpuTime2 = getAppCpuTime().toFloat()
        if ((totalCpuTime2 - totalCpuTime1) == 0f) {
            return "0"
        } else {
            val bd = BigDecimal(
                    ((processCpuTime2 - processCpuTime1).toDouble() / (totalCpuTime2 - totalCpuTime1)) * 100
            )
            val b: Int = bd.toInt()
            return (b).toString()
        }
    }

    /**
     * 获取总的CPU使用率
     * @return CPU使用率
     */
    fun getTotalCpuRate(): String {
        val totalCpuTime1: Float = getTotalCpuTime().toFloat()
        val totalUsedCpuTime1: Float = totalCpuTime1 - (idletime.toFloat())
        try {
            Thread.sleep(360)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val totalCpuTime2: Float = getTotalCpuTime().toFloat()
        val totalUsedCpuTime2: Float = totalCpuTime2 - (idletime.toFloat())

        if ((totalCpuTime2 - totalCpuTime1) == 0f) {
            return "0"
        } else {
            val bd = BigDecimal(
                    ((totalUsedCpuTime2 - totalUsedCpuTime1).toDouble() / (totalCpuTime2 - totalCpuTime1)) * 100
            )
            val b: Int = bd.toInt()
            return (b).toString()
        }
    }

    private var idletime = 0L

    /**
     * 获取 系统 总CPU 使用时间
     * such as : 0
     */
    private fun getTotalCpuTime(): String { //
        var cpuInfos: Array<String>? = null
        try {
            val reader = BufferedReader(InputStreamReader(FileInputStream("/proc/stat")), 1000)
            val load: String = reader.readLine()
            reader.close()
            cpuInfos = load.split(" ").toTypedArray()
        } catch (ex: IOException) {
            // do nothing
        }

        if (cpuInfos?.isNotEmpty() == true && cpuInfos.size > 2) {
            val cpuTime = cpuInfos[2].toLong() + cpuInfos[3].toLong() + cpuInfos[4].toLong() +
                    cpuInfos[6].toLong() + cpuInfos[5].toLong() + cpuInfos[7].toLong() + cpuInfos[8].toLong()
            idletime = cpuInfos[5].toLong()
            return cpuTime.toString()
        } else {
            return "0"
        }
    }

    private fun getAppCpuTime(): Long { // 获取应用占用的CPU时间
        var cpuInfos: Array<String>? = null
        try {
            val pid = Process.myPid()
            val reader = BufferedReader(
                    InputStreamReader(
                            FileInputStream("/proc/$pid/stat")
                    ), 1000
            )
            val load: String = reader.readLine()
            reader.close()
            cpuInfos = load.split(" ")
                    .toTypedArray()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        if (cpuInfos?.isNotEmpty() == true && cpuInfos.size > 2) {
            return cpuInfos!![13].toLong() + cpuInfos[14].toLong() + cpuInfos[15].toLong() + cpuInfos[16].toLong()
        } else {
            return 0
        }
    }
}