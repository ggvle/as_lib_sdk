package com.yline.utils.provider

import java.util.concurrent.ConcurrentHashMap

object Provider {
    private val provision = ConcurrentHashMap<Class<*>, Any>()

    fun provide(any: Any) {
        provision[any.javaClass] = any
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> acquire(clz: Class<T>): T? {
        return try {
            provision[clz] as T
        } catch (th: Throwable) {
            null
        }
    }

    fun <T> remove(clz: Class<T>) {
        provision.remove(clz)
    }

    fun clear() {
        provision.clear()
    }
}