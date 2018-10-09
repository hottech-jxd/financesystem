package com.cunyn.android.financesystem.mvp

import android.arch.lifecycle.LifecycleOwner
import android.text.TextUtils
import com.cunyn.android.financesystem.base.BaseApplication
import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.bindLifeCycle
import com.cunyn.android.financesystem.util.GsonUtils
import com.cunyn.android.financesystem.util.SPUtils
import com.cunyn.android.financesystem.wrapper
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe


class InitPresenter(var view :InitContract.View) : InitContract.Presenter {




    override fun init(customerId: Long, delay:Long  ) {


        Observable.create( object : ObservableOnSubscribe<ApiResult<Any>> {

            override fun subscribe(emitter: ObservableEmitter<ApiResult<Any>>) {

                var json = SPUtils.getInstance(BaseApplication.instance!!,Constants.PREF_FILENAME)
                        .readString(Constants.PREF_USER)
                if( !TextUtils.isEmpty(json)){
                    Variable.UserBean = GsonUtils.toObject( json , UserBean::class.java)
                }
                var apiResult = ApiResult<Any>()
                apiResult.code = ApiResultCodeEnum.SUCCESS.code
                apiResult.message=""

            emitter.onNext( apiResult )
            emitter.onComplete()
            }}).wrapper(delay)
                .doAfterTerminate { view.hideProgress() }
                .doOnSubscribe { view.showProgress() }
                .doAfterNext { view.initCallback(it) }
                .doOnError { view.hideProgress()
                    it.printStackTrace() }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({},{view.error()})

    }


    override fun onDestory() {

    }
}