package com.cunyn.android.financesysetm

import android.content.Context
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.cunyn.android.financesystem.R
import com.cunyn.android.financesystem.bean.ApiResult
import com.cunyn.android.financesystem.bean.Constants
import com.cunyn.android.financesystem.mvp.IView
import com.cunyn.android.financesystem.showToast
import com.cunyn.android.financesystem.util.BackHandlerHelper
import com.cunyn.android.financesystem.util.StatusBarUtils
import com.umeng.analytics.MobclickAgent


open class BaseActivity<T> : AppCompatActivity()
, IView<T>
        , View.OnClickListener {

    var isWhite:Boolean=false
    //var quitReceiver:QuitReceiver?=null
    var isPORTRAIT=true
    //var resetReceiver:ResetReceiver?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setStatusBar(R.color.colorPrimary)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        //super.attachBaseContext( LocalManageUtil.setLocal(newBase!!) )
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            KeybordUtils.closeKeyboard(this)
//            this.finish()
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onBackPressed() {
        //super.onBackPressed()

        if(!BackHandlerHelper.handleBackPress(this)){

            this.finish()
        }

    }

    open fun setStatusBar(@ColorRes color :  Int ){
        //禁止横屏
        if(isPORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        if( color == R.color.white ){
            setStatusBarTextBlackColor()
        }else {
            StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary ) )
        }
    }


    private fun setStatusBarTextBlackColor():Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var newUiVisibility = this.window.decorView.systemUiVisibility
            newUiVisibility = newUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            newUiVisibility = newUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            this.window.decorView.systemUiVisibility = newUiVisibility
            window.statusBarColor = Color.WHITE
            return true
        }
        return false
    }


    override fun onClick(v: View?) {

    }

    override fun showProgress(msg: String) {

    }

    override fun showProgress() {
        showProgress(Constants.TIP_LOADING)
    }

    override fun hideProgress() {

    }

    override fun toast(msg: String) {
        showToast(msg)
    }

    override fun error(err: String) {
        toast(err)
    }

    override fun error() {
        toast(Constants.MESSAGE_ERROR)
    }

    /**
     * 处理服务端返回的公共错误代码
     * @param apiResult
     * @return
     */
    protected fun processCommonErrorCode( apiResult: ApiResult<*>): Boolean {
//        if (apiResult.code == ApiResultCodeEnum.USER_NO_LOGIN.code ||
//            apiResult.code == ApiResultCodeEnum.USER_FREEZE.code ||
//            apiResult.code == ApiResultCodeEnum.USER_NO_LOGIN.code  ) {
//
//            toast(Constants.MESSAGE_TOKEN_LOST)
//
//            //EventBus.getDefault().post(LogoutEvent())
//
//            newIntent<SplashActivity>()
//            return true
//        }
        return false
    }

//    protected fun isLogin(): Boolean {
//        return return !(BaseApplication.instance!!.variable.userBean == null
//                        || BaseApplication.instance!!.variable.userBean!!.token == ""
//                        || BaseApplication.instance!!.variable.userBean!!.userId == 0L)
//    }

}
