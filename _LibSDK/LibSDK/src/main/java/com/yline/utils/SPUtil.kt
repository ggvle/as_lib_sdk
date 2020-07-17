package com.yline.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * SharedPreferences封装类
 *
 *
 * 所有的commit操作使用了SharedPreferencesCompat.apply进行了替代;尽可能异步操作
 */
object SPUtil {

    /**
     * 保存在手机里面的文件名
     */
    private const val DEFAULT_FILE_NAME = "default"

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * if object == null, 默认作为String
     *
     * @param context  上下文
     * @param key      关键字
     * @param object   数据
     * @param fileName 文件名
     */
    @JvmOverloads
    fun put(context: Context, key: String, `object`: Any?, fileName: String = DEFAULT_FILE_NAME) {
        var fileName = fileName
        if (TextUtils.isEmpty(fileName) && "" == fileName) {
            fileName = DEFAULT_FILE_NAME
        }

        val sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()

        // instanceof 用来 指出对象是否是特定类的一个实例
        if (null == `object`) {
            editor.putString(key, "")
        } else if (`object` is String) {
            editor.putString(key, `object` as String?)
        } else if (`object` is Int) {
            editor.putInt(key, (`object` as Int?)!!)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, (`object` as Boolean?)!!)
        } else if (`object` is Float) {
            editor.putFloat(key, (`object` as Float?)!!)
        } else if (`object` is Long) {
            editor.putLong(key, (`object` as Long?)!!)
        } else {
            editor.putString(key, `object`.toString())
        }

        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context       上下文
     * @param key           关键字
     * @param defaultObject 默认值
     * @param fileName      文件名
     * @return 如果key参数错误，则返回空
     */
    @JvmOverloads
    operator fun get(context: Context, key: String, defaultObject: Any?, fileName: String = DEFAULT_FILE_NAME): Any? {
        var fileName = fileName
        if (TextUtils.isEmpty(fileName)) {
            fileName = DEFAULT_FILE_NAME
        }

        val sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return if (defaultObject is String) {
            sp.getString(key, defaultObject as String?)
        } else if (defaultObject is Int) {
            sp.getInt(key, (defaultObject as Int?)!!)
        } else if (defaultObject is Boolean) {
            sp.getBoolean(key, (defaultObject as Boolean?)!!)
        } else if (defaultObject is Float) {
            sp.getFloat(key, (defaultObject as Float?)!!)
        } else if (defaultObject is Long) {
            sp.getLong(key, (defaultObject as Long?)!!)
        } else {
            sp.getString(key, defaultObject?.toString())
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context  上下文
     * @param key      关键字
     * @param fileName 文件名
     */
    @JvmOverloads
    fun remove(context: Context, key: String, fileName: String = DEFAULT_FILE_NAME) {
        var fileName = fileName
        if (TextUtils.isEmpty(fileName)) {
            fileName = DEFAULT_FILE_NAME
        }

        val sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 清除所有数据
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    @JvmOverloads
    fun clear(context: Context, fileName: String = DEFAULT_FILE_NAME) {
        var fileName = fileName
        if (TextUtils.isEmpty(fileName) && "" == fileName) {
            fileName = DEFAULT_FILE_NAME
        }

        val sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     *
     * apply相当于commit来说是new API呢，为了更好的兼容，我们做了适配
     */
    private object SharedPreferencesCompat {
        private val sApplyMethod = findApplyMethod()

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {

            }

            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
                // todo
            } catch (ignored: IllegalAccessException) {
                // todo
            } catch (e: InvocationTargetException) {
                // todo
            }

            editor.commit()
        }
    }
}

