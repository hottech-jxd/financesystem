package com.cunyn.android.financesystem.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64
import java.io.ByteArrayOutputStream


object ImageUtils {

    fun stringtoBitmap (base64String: String?): Bitmap? {

        if(TextUtils.isEmpty(base64String)) return null

        //将字符串转换成Bitmap类型
        var bitmap: Bitmap? = null
        try {
            var bitmapArray = Base64.decode(base64String, Base64.DEFAULT)

            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

    fun  bitmaptoString( bitmap :Bitmap ):String? {

        //将Bitmap转换成字符串
        var string: String? = null
        var bStream  = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream)
        var bytes = bStream.toByteArray()
        string = Base64.encodeToString(bytes, Base64.DEFAULT)
        return string
    }
}