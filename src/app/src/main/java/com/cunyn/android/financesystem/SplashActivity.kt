package com.cunyn.android.financesystem

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import com.cunyn.android.financesysetm.BaseActivity
import com.cunyn.android.financesystem.bean.ApiResult
import com.cunyn.android.financesystem.bean.Constants
import com.cunyn.android.financesystem.mvp.InitPresenter

class SplashActivity : BaseActivity<InitContract.Presenter>()
,InitContract.View{
    private var iPresenter = InitPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {

        isPORTRAIT=false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler(this.mainLooper)
                .postDelayed(
                        {iPresenter.init(Constants.CUSTOMERID)}
                        , 2000)

        //iPresenter.init(Constants.CUSTOMERID,1500)
    }

    override fun initCallback(apiResult: ApiResult<Any>) {

        skipIntent<MainActivity>()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
