package com.cunyn.android.financesystem.mvp

import android.arch.lifecycle.LifecycleOwner
import com.cunyn.android.financesystem.bean.ApiResult
import com.cunyn.android.financesystem.bean.ApiResultCodeEnum
import com.cunyn.android.financesystem.bean.Constants
import com.cunyn.android.financesystem.bean.UserBean
import com.cunyn.android.financesystem.bindLifeCycle
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

    override fun sendCode(phone: String, safecode: String?, customerId: Long) {
        model.sendSmsCode(phone,safecode, customerId)
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

    override fun getPictureCode() {
        model.getPictureCode()
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .doOnError { view.hideProgress()
                    //view.error()
                    it.printStackTrace()
                }
                .doAfterNext { view.getPictureCodecallback(it) }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({},{view.error()})


//        model.getPictureCode()
//                .wrapper()
//                .bindLifeCycle(view as LifecycleOwner)
//                .subscribe( object : Observer<ApiResult<String>> {
//                    override fun onComplete() {
//                        view.hideProgress()
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                        view.showProgress()
//                    }
//
//                    override fun onNext(t: ApiResult<String>) {
//                        view.getPictureCodecallback(t)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        view.hideProgress()
//                        view.error()
//                    }
//                })
    }

    override fun onDestory() {

    }
}