package com.cunyn.android.financesystem.util

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object GsonUtils {

    private val Gson = GsonBuilder().serializeNulls().create()

    /**
     * 对象转换为json
     *
     * @param value
     * @return
     */
    fun toJsonString(value: Any): String {
        return Gson.toJson(value)
    }

    /**
     * json 转换为对象
     *
     * @param jsonString
     * @param cls
     * @return
     */
    fun <T> toObject(jsonString: String, clazz: Class<T>): T? {
        var t: T? = null
        try {
            t = Gson.fromJson(jsonString, clazz)
        } catch (e: Exception) {
            Log.e("JSON转换成对象失败!!{}{}", jsonString, e)
        }
        return t
    }

    /**
     * json转换为List<Object>
     * @param jsonString
     * @return
     */
    fun <T> toList(jsonString: String): List<T> {
        var list: List<T> = ArrayList()
        try {
            list = Gson.fromJson(jsonString, object : TypeToken<List<T>>() {}.type)
        } catch (e: Exception) {
            Log.e("JSON转换成对象失败!!{}{}", jsonString, e)
        }
        return list
    }

    /**
     * json转换为List<Map<String, Object>>
     * @param jsonString
     * @return
     */
    fun toListKeyMaps(jsonString: String): List<Map<String, Any>> {
        var list: List<Map<String, Any>> = ArrayList()
        try {
            list = Gson.fromJson(jsonString, object : TypeToken<List<Map<String, Any>>>() {
            }.type)
        } catch (e: Exception) {
            Log.e("JSON转换成对象失败!!{}{}", jsonString, e)
        }
        return list
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    fun <T> mapToJson(map: Map<String, T>): String {
        return Gson.toJson(map)
    }

}