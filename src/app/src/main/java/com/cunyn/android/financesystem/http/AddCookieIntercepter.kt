package com.cunyn.android.financesystem.http

import android.content.Context
import android.provider.SyncStateContract
import android.util.Log
import com.cunyn.android.financesystem.bean.Constants
import com.cunyn.android.financesystem.bean.Variable
import com.cunyn.android.financesystem.util.SPUtils
import java.io.IOException
import java.util.HashSet
import java.util.prefs.Preferences

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.Console

/**
 * 此拦截器是用于在http请求头添加cookie
 * Created by Administrator on 2017/10/11.
 */

class AddCookieIntercepter() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        //val preferences = SPUtils.getInstance(context,Constants.PREF_FILENAME).readStringSet( Constants.PREF_COOKIE, HashSet<String>()) as HashSet<String>

        var cookiesSet = Variable.CookiesSet
        if(cookiesSet!=null) {
            for (cookie in cookiesSet) {
                builder.addHeader("Cookie", cookie)
                Log.v("OkHttp", "Adding Header: $cookie") // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }

        return chain.proceed(builder.build())
    }
}
