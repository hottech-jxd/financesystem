package com.cunyn.android.financesystem.base

import android.app.ActivityManager
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

        if (isMainProcess()) {//只初始化一次
            XinYanSDK.getInstance().init(this)
            XinYanSDK.getInstance().isdebug = true
        }

        //LocalManageUtil.setApplicationLanguage(applicationContext)
    }

    /**
     * 包名判断是否为主进程
     *
     * @param
     * @return
     */
    fun isMainProcess(): Boolean {
        return applicationContext.packageName == getCurrentProcessName()
    }

    /**
     * 获取当前进程名
     */
    private fun getCurrentProcessName(): String {
        val pid = android.os.Process.myPid()
        var processName = ""
        val manager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid == pid) {
                processName = process.processName
            }
        }
        return processName
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