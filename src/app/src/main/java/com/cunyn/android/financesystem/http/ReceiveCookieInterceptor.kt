package com.cunyn.android.financesystem.http

import android.content.Context
import com.cunyn.android.financesystem.bean.Constants
import com.cunyn.android.financesystem.bean.Variable
import com.cunyn.android.financesystem.util.SPUtils

import java.io.IOException
import java.util.HashSet
import java.util.prefs.Preferences

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 此拦截器是用于接受 http返回的cookie，并保存到本地
 * Created by Administrator on 2017/10/11.
 */
class ReceiveCookieInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
        if (response.headers("Set-Cookie").isEmpty()) return response

        val cookies = HashSet<String>()
        for (header in response.headers("Set-Cookie")) {
            cookies.add(header)
        }

        //SPUtils.getInstance(context, Constants.PREF_FILENAME).writeStringSet( Constants.PREF_COOKIE, cookies)
        Variable.CookiesSet = cookies

        return response
    }
}
