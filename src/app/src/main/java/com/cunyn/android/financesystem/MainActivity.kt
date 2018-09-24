package com.cunyn.android.financesystem

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.cunyn.android.financesysetm.BaseActivity
import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.fragment.IndexFragment
import com.cunyn.android.financesystem.fragment.LoginRegisterFragment
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.InitPresenter
import com.guoxintaiyi.android.missionwallet.base.OnFragmentEventListener

class MainActivity : BaseActivity<InitContract.Presenter>(),
        InitContract.View,OnFragmentEventListener{
    private var REQUEST_CODE= 1200
    private var iPresenter = InitPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        //initView()
    }


    private fun initView(){

        iPresenter.init(Constants.CUSTOMERID)


//        var loginRegisterFragment = LoginRegisterFragment.newInstance("","")
//        loginRegisterFragment.closeListener=this
//
//        supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.main_container , loginRegisterFragment)
//                .commit()

    }

    override fun onOpen( type :Int ) {
        when(type){
            FragmentType.Index.code->{
                var indexFragment = IndexFragment.newInstance("","")
                indexFragment.closeListener=this

                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container , indexFragment)
                        .commit()
            }
        }
    }

    override fun onClose() {

    }

    override fun onClsoeActivity() {
        finish()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)

                return
            }
        }

        initView()
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if( requestCode == REQUEST_CODE ){
            if( grantResults[0] != PackageManager.PERMISSION_GRANTED){
                toast("由于您拒绝App使用读写存储设备权限，App将无法使用")
                finish()
            }else{
                initView()
            }
        }
    }

    override fun initCallback(apiResult: ApiResult<Any>) {

        if(apiResult.code !=ApiResultCodeEnum.SUCCESS.code){
            toast( apiResult.message)
            return
        }

        if(Variable.UserBean==null) {

            var loginRegisterFragment = LoginRegisterFragment.newInstance("", "")
            loginRegisterFragment.closeListener = this

            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, loginRegisterFragment)
                    .commit()
        }else{

            var indexFragment = IndexFragment.newInstance("","")
            indexFragment.closeListener = this
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, indexFragment)
                    .commit()
        }
    }
}
