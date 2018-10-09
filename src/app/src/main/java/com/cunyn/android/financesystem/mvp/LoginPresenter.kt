package com.cunyn.android.financesystem.mvp

import android.arch.lifecycle.LifecycleOwner
import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.bindLifeCycle
import com.cunyn.android.financesystem.util.ImageUtils
import com.cunyn.android.financesystem.wrapper
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class LoginPresenter(var view :LoginContract.View) : LoginContract.Presenter {

    private var model=ApiModel()


    override fun getLoginUIData(customerId: Long) {
        model.getLoginUIData(customerId)
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .doOnError { view.hideProgress()
                it.printStackTrace()}
                .doAfterNext {
                    view.getLoginUIDataCallback(it)
                }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({},{view.error()})
    }

    override fun sendCode(phone: String, safecode: String?, customerId: Long , safeKey:String?) {
        model.sendSmsCode(phone,safecode, customerId , safeKey)
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate{view.hideProgress()}
                .doOnError {
                    view.hideProgress()
                    it.printStackTrace()                }
                .doAfterNext {
                    view.sendCodeCallback(it)
                }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe( {}, {view.error() })
    }

    override fun login(phone: String, smsCode: String, validateCode: String?, customerId: Long) {
        model.login(phone, smsCode , validateCode , customerId)
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .doOnError { view.hideProgress()
                 it.printStackTrace()}
                .doAfterNext { view.loginCallback(it) }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({},{view.error()})
    }

//    private fun loginCallback(apiResult: ApiResult<UserBean>):ApiResult<UserBean>{
//        if(apiResult.code==ApiResultCodeEnum.SUCCESS.code){
//
//        }
//    }

    private fun base64ToBitmap(result:ApiResult<VerifyCodeBean?>){
        if(result.code == ApiResultCodeEnum.SUCCESS.code&& result.data!=null ){
            result.data!!.imageBitmap = ImageUtils.stringtoBitmap(result.data!!.image)
        }
    }

    override fun getPictureCode() {
        model.getPictureCode()
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .doOnError { view.hideProgress()
                    //view.error()
                    it.printStackTrace()
                }
                .doAfterNext {


                    base64ToBitmap(it)

                    view.getPictureCodecallback(it)
                }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({},{view.error()})


    }

    override fun getRegisterContent() {
        model.getRegisterContent(Constants.CUSTOMERID)
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .doOnError { view.hideProgress()
                    it.printStackTrace()
                }
                .doAfterNext {  view.getRegisterContentCallback(it) }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({},{view.error()})
    }

    override fun onDestory() {

    }
}