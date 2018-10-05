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
import android.widget.Toast
import com.cunyn.android.financesysetm.BaseActivity
import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.fragment.IndexFragment
import com.cunyn.android.financesystem.fragment.LoginRegisterFragment
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.InitPresenter
import com.guoxintaiyi.android.missionwallet.base.OnFragmentEventListener

class MainActivity : BaseActivity<InitContract.Presenter>(),
        InitContract.View,OnFragmentEventListener{

    private val permissions = arrayOf<String>(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.READ_CONTACTS)



    private var REQUEST_CODE= 1200
    private var iPresenter = InitPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

    }


    private fun initView(){
        checkLogin()
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

    //true 有权限  未获取
    fun lacksPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (lacksPermission(permission)) {
                return true
            }
        }
        return false
    }

    // true  未获取权限
    private fun lacksPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
    }

    fun requestAllPermissions(vararg permissions: String) {
        if (lacksPermissions(*permissions)) {
            ActivityCompat.requestPermissions(this,
                    permissions, REQUEST_CODE)
        }else{
            initView()
        }
    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestAllPermissions(*permissions)
        }else {
            initView()
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if( requestCode == REQUEST_CODE ){


            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show()
                } else {
                    toast("由于您拒绝App使用权限，App将无法使用")
                    finish()
                    return
                    //Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show()
                }
            }
            initView()

        }
    }

    override fun initCallback(apiResult: ApiResult<Any>) {

//        if(apiResult.code !=ApiResultCodeEnum.SUCCESS.code){
//            toast( apiResult.message)
//            return
//        }


    }


    private fun checkLogin(){
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
