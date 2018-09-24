package com.cunyn.android.financesystem.util

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * Created by Administrator on 2017/11/1.
 */

object MobileUtils {
    /***
     * 检索手机号码是否合法
     * @param phone
     * @return
     */
    fun isPhone(phone: String): Boolean {
        val p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(16[0-9])|(18[0-9])|(17[0-9])|(19[0-9]))\\d{8}$")
        val m = p.matcher(phone)
        //logger.info(m.matches()+"---");
        return m.matches()
    }

    /***
     *
     * @param text
     * @return
     */
     fun dealPhone(text:String):String{
        if(TextUtils.isEmpty( text)) return  text

        var isPh = isPhone(text)
        if(isPh){
            var prefx = text.substring(0,3)
            var len = text.length
            var endfx = text.substring(len-4,len)
            return prefx + "****" + endfx;
        }
        return text
    }
}
