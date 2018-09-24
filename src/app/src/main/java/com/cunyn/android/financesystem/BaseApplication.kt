package com.cunyn.android.financesystem.base

import android.app.Application
import android.app.ApplicationErrorReport
import android.content.Context
import android.content.res.Configuration
import com.cunyn.android.financesystem.util.CrashHandler
import com.facebook.drawee.backends.pipeline.Fresco
import com.xinyan.bigdata.XinYanSDK

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize( this )

        CrashHandler.instance.init(this)

        XinYanSDK.getInstance().init(this)


        //LocalManageUtil.setApplicationLanguage(applicationContext)
    }

    override fun attachBaseContext(base: Context?) {

        super.attachBaseContext( base)

        //MultiDex.install(this)

        //把初始化工作放到单独的一个线程中处理。
        //InitService.start(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        //保存系统选择语言
        //LocalManageUtil.onConfigurationChanged(applicationContext)
    }

    companion object {
        var instance :BaseApplication?=null
    }
}