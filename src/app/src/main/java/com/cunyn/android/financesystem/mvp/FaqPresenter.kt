package com.cunyn.android.financesystem.mvp

import android.arch.lifecycle.LifecycleOwner
import com.cunyn.android.financesystem.bean.ApiResult
import com.cunyn.android.financesystem.bean.Constants
import com.cunyn.android.financesystem.bindLifeCycle
import com.cunyn.android.financesystem.wrapper
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class FaqPresenter(var view :FaqContract.View) : FaqContract.Presenter {

    private var model=ApiModel()


    override fun getFaqData(customerId: Long) {
        model.getFaqData(customerId)
                .wrapper()
                .doOnSubscribe { view.showProgress() }
                .doAfterTerminate { view.hideProgress() }
                .doOnError { view.hideProgress()
                it.printStackTrace()}
                .doAfterNext {
                    view.getFagDataCallback(it)
                }
                .bindLifeCycle(view as LifecycleOwner)
                .subscribe({},{view.error()})
    }



    override fun onDestory() {

    }
}