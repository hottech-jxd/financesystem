package com.cunyn.android.financesystem.mvp

import android.arch.lifecycle.LifecycleOwner
import android.text.TextUtils
import com.cunyn.android.financesystem.base.BaseApplication
import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.bindLifeCycle
import com.cunyn.android.financesystem.util.ContractUtils
import com.cunyn.android.financesystem.util.GsonUtils
import com.cunyn.android.financesystem.util.SPUtils
import com.cunyn.android.financesystem.wrapper
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.util.*

class AuditionPresenter(var view:AuditionContract.View)
    :AuditionContract.Presenter{
    private var model=ApiModel()



    override fun uploadContracts(customerId: Long, userMobile: String ) {

        Observable.create( object : ObservableOnSubscribe<ApiResult<String?>> {

            override fun subscribe(emitter: ObservableEmitter<ApiResult<String?>>) {

                var utils =  ContractUtils(BaseApplication.instance!!)
                var list = utils.getPhone()
                var json = GsonUtils.toJsonString(list)


                var apiResult = ApiResult<String?>()
                apiResult.code = ApiResultCodeEnum.SUCCESS.code
                apiResult.message=""
                apiResult.data =json

                emitter.onNext( apiResult )
                emitter.onComplete()
            }}).wrapper()
                 .doAfterTerminate { view.hideProgress() }
                .doOnSubscribe { view.showProgress() }
                .doAfterNext {
                    this.upload( userMobile , customerId , it.data )
                }
                .doOnError { view.hideProgress()
                    it.printStackTrace() }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({},{view.error()})


    }

    private fun upload(userMobile : String,customerId: Long, json:String?){
        model.uploadContracts(userMobile,customerId,json)
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .doOnError { view.hideProgress() }
                .doAfterNext { view.uploadContractsCallback(it) }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({}, {view.error()})
    }

    override fun submit(RealName: String, IdCardNo: String, UserMobile: String, BankNo: String) {
        model.submitInfo(RealName , IdCardNo , UserMobile , BankNo)
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .doOnError { view.hideProgress() }
                .doAfterNext { view.submitCallback(it) }
                .bindLifeCycle( view as LifecycleOwner)
                .subscribe({}, {view.error()})
    }

    override fun createPreOrder(txnType: String , customerId: Long) {
        model.createPreOrder(txnType , customerId)
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .doOnError { view.hideProgress()
                    it.printStackTrace()
                }.doAfterNext { view.createPreOrderCallback(it , txnType) }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({},{view.error()})
    }

    override fun onDestory() {

    }
}