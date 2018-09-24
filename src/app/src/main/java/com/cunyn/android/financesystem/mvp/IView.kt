package com.cunyn.android.financesystem.mvp

interface IView<T> {

    fun showProgress()

    fun showProgress( msg:String)

    fun hideProgress()

    fun toast(msg : String)

    fun error(err:String)

    fun error()
}