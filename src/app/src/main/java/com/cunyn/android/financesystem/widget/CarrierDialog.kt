package com.cunyn.android.financesystem.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import com.cunyn.android.financesystem.R
import com.cunyn.android.financesystem.bean.XinYanData


/**
 * Created by XY400047 on 2018/8/10.
 * Email 0426k@sina.com
 * Update:
 * Description:
 */
class CarrierDialog : AppCompatDialogFragment(), View.OnClickListener {
    private var mContentView: View? = null
    private var edxinyanphonenum: EditText? = null
    private var edfwpwd: EditText? = null
    private var edUsername: EditText? = null
    private var edIdnum: EditText? = null
    private var ivNameclear: ImageView? = null
    private var ivIdClear: ImageView? = null
    private var ivPhoneNumClear: ImageView? = null
    private var ivPwdClear: ImageView? = null
    private var ibnext: Button? = null
    private var mOnNextListener: OnNextListener? = null
    private var inputSwitch: Switch? = null
    private var idnameinputSwitch: Switch? = null//idnameinputSwitch 是否显示用户名  默认不显示
    private var ivclose: ImageView? = null


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun afterTextChanged(editable: Editable) {
            val username = edUsername!!.text.toString()
            val id = edIdnum!!.text.toString()
            val phonenum = edxinyanphonenum!!.text.toString()
            val pwd = edfwpwd!!.text.toString()

            if (!TextUtils.isEmpty(username) && edUsername!!.hasFocus()) {
                ivNameclear!!.visibility = View.VISIBLE
            } else if (!TextUtils.isEmpty(id) && edIdnum!!.hasFocus()) {
                ivIdClear!!.visibility = View.VISIBLE
            } else if (!TextUtils.isEmpty(phonenum) && edxinyanphonenum!!.hasFocus()) {
                ivPhoneNumClear!!.visibility = View.VISIBLE
            } else if (!TextUtils.isEmpty(pwd) && edfwpwd!!.hasFocus()) {
                ivPwdClear!!.visibility = View.VISIBLE
            } else if (!TextUtils.isEmpty(phonenum) && edxinyanphonenum!!.hasFocus()) {
                ivPhoneNumClear!!.visibility = View.VISIBLE
            } else {
                ivNameclear!!.visibility = View.INVISIBLE
                ivIdClear!!.visibility = View.INVISIBLE
                ivPwdClear!!.visibility = View.INVISIBLE
                ivPhoneNumClear!!.visibility = View.INVISIBLE
            }

        }
    }

    internal var onFocusChangeListener: View.OnFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if (v === edUsername) {
            if (!hasFocus) {
                ivNameclear!!.visibility = View.INVISIBLE
            } else {
                if (!TextUtils.isEmpty(edUsername!!.text.toString())) {
                    ivNameclear!!.visibility = View.VISIBLE
                }
            }
        } else if (v === edIdnum) {
            if (!hasFocus) {
                ivIdClear!!.visibility = View.INVISIBLE
            } else {
                if (!TextUtils.isEmpty(edIdnum!!.text.toString())) {
                    ivIdClear!!.visibility = View.VISIBLE
                }
            }
        } else if (v === edfwpwd) {
            if (!hasFocus) {
                ivPwdClear!!.visibility = View.INVISIBLE
            } else {
                if (!TextUtils.isEmpty(edfwpwd!!.text.toString())) {
                    ivPwdClear!!.visibility = View.VISIBLE
                }
            }
        } else if (v === edxinyanphonenum) {
            if (!hasFocus) {
                ivPhoneNumClear!!.visibility = View.INVISIBLE
            } else {
                if (!TextUtils.isEmpty(edxinyanphonenum!!.text.toString())) {
                    ivPhoneNumClear!!.visibility = View.VISIBLE
                }
            }
        }
    }

    private val mAnimStyle = com.xinyan.bigdata.R.style.DefaultCityPickerAnimation


    interface OnNextListener {
        fun onClick()
    }

    fun setmOnNextListener(l: OnNextListener) {
        mOnNextListener = l
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContentView = inflater.inflate(R.layout.dialog_carrier, container, false)
        edxinyanphonenum = mContentView!!.findViewById(R.id.xinyanphonenum) as EditText
        edfwpwd = mContentView!!.findViewById(R.id.fwpwd) as EditText
        edUsername = mContentView!!.findViewById(R.id.xinyan_username) as EditText
        edIdnum = mContentView!!.findViewById(R.id.xinyan_idnum_input) as EditText
        ivNameclear = mContentView!!.findViewById(R.id.user_name_clear) as ImageView
        ivIdClear = mContentView!!.findViewById(R.id.user_id_clear) as ImageView
        ivPhoneNumClear = mContentView!!.findViewById(R.id.user_phonenum_clear) as ImageView
        ivPwdClear = mContentView!!.findViewById(R.id.user_fupwd_clear) as ImageView
        inputSwitch = mContentView!!.findViewById(R.id.inputSwitch) as Switch
        idnameinputSwitch = mContentView!!.findViewById(R.id.idnameinputSwitch) as Switch
        ivclose = mContentView!!.findViewById(R.id.ivclose) as ImageView
        ibnext = mContentView!!.findViewById(R.id.next) as Button

        edUsername!!.onFocusChangeListener = onFocusChangeListener
        edIdnum!!.onFocusChangeListener = onFocusChangeListener
        edxinyanphonenum!!.onFocusChangeListener = onFocusChangeListener
        edfwpwd!!.onFocusChangeListener = onFocusChangeListener

        edUsername!!.addTextChangedListener(textWatcher)
        edIdnum!!.addTextChangedListener(textWatcher)
        edfwpwd!!.addTextChangedListener(textWatcher)
        if (!TextUtils.isEmpty(XinYanData.realname))
            edUsername!!.setText(XinYanData.realname)
        if (!TextUtils.isEmpty(XinYanData.idcard))
            edIdnum!!.setText(XinYanData.idcard)
        if (!TextUtils.isEmpty(XinYanData.phoneNum))
            edxinyanphonenum!!.setText(XinYanData.phoneNum)
        if (!TextUtils.isEmpty(XinYanData.phoneServerCode))
            edfwpwd!!.setText(XinYanData.phoneServerCode)
        ivclose!!.setOnClickListener { dismiss() }

        inputSwitch!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                XinYanData.carrierCanInput = XinYanData.YES
            } else {
                XinYanData.carrierCanInput = XinYanData.NO
            }
        }


        idnameinputSwitch!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                XinYanData.carrierIDandNameShow = XinYanData.YES
            } else {
                XinYanData.carrierIDandNameShow = XinYanData.NO
            }
        }

        if (XinYanData.YES.equals(XinYanData.carrierCanInput)) {
            inputSwitch!!.isChecked = true
        } else {
            inputSwitch!!.isChecked = false
        }
        if (XinYanData.YES.equals(XinYanData.carrierIDandNameShow)) {
            idnameinputSwitch!!.isChecked = true
        } else {
            idnameinputSwitch!!.isChecked = false
        }

        ivNameclear!!.setOnClickListener(this)
        ivIdClear!!.setOnClickListener(this)
        ivPhoneNumClear!!.setOnClickListener(this)
        ivPwdClear!!.setOnClickListener(this)
        ibnext!!.setOnClickListener(this)


        return mContentView
    }

    override fun onClick(view: View) {
        if (view === ibnext) {
            XinYanData.idcard = edIdnum!!.text.toString().replace(" ".toRegex(), "")
            XinYanData.realname = edUsername!!.text.toString().replace(" ".toRegex(), "")
            XinYanData.phoneNum = edxinyanphonenum!!.text.toString().replace(" ".toRegex(), "")
            XinYanData.phoneServerCode = edfwpwd!!.text.toString().replace(" ".toRegex(), "")

            //            /*
            //             * 四要素逻辑
            //             * 1.显示/可编辑：四要素可以不传
            //             * 2.不显示/可编辑：姓名、身份证必填
            //             * 3.显示/不可编辑：姓名、身份证必填、手机号必填
            //             * 4.不显示/不可编辑：姓名、身份证必填、手机号必填
            //             * *不可编辑模式：密码框 为空的时候可编辑 ，不为空不可编辑密码框
            //             */
            //
            if (XinYanData.YES.equals(XinYanData.carrierCanInput) && XinYanData.NO.equals(XinYanData.carrierIDandNameShow)) {
                if (TextUtils.isEmpty(XinYanData.realname)) {
                    Toast.makeText(context, getString(R.string.inputname), Toast.LENGTH_LONG).show()
                    return
                }
                if (TextUtils.isEmpty(XinYanData.idcard)) {
                    Toast.makeText(context, getString(R.string.inputid), Toast.LENGTH_LONG).show()
                    return
                }
            } else if (XinYanData.NO.equals(XinYanData.carrierCanInput) && XinYanData.YES.equals(XinYanData.carrierIDandNameShow)) {
                if (TextUtils.isEmpty(XinYanData.realname)) {
                    Toast.makeText(context, getString(R.string.inputname), Toast.LENGTH_LONG).show()
                    return
                }
                if (TextUtils.isEmpty(XinYanData.idcard)) {
                    Toast.makeText(context, getString(R.string.inputid), Toast.LENGTH_LONG).show()
                    return
                }
                if (TextUtils.isEmpty(XinYanData.phoneNum)) {
                    Toast.makeText(context, getString(R.string.inputPhonum), Toast.LENGTH_LONG).show()
                    return
                }
            } else if (XinYanData.NO.equals(XinYanData.carrierCanInput) && XinYanData.NO.equals(XinYanData.carrierIDandNameShow)) {
                if (TextUtils.isEmpty(XinYanData.realname)) {
                    Toast.makeText(context, getString(R.string.inputname), Toast.LENGTH_LONG).show()
                    return
                }
                if (TextUtils.isEmpty(XinYanData.idcard)) {
                    Toast.makeText(context, getString(R.string.inputid), Toast.LENGTH_LONG).show()
                    return
                }
                if (TextUtils.isEmpty(XinYanData.phoneNum)) {
                    Toast.makeText(context, getString(R.string.inputPhonum), Toast.LENGTH_LONG).show()
                    return
                }
            }
            if (mOnNextListener != null) {
                mOnNextListener!!.onClick()
            }
            dismiss()
        } else if (view === ivNameclear) {
            edUsername!!.setText("")
        } else if (view === ivIdClear) {
            edIdnum!!.setText("")
        } else if (view === ivPhoneNumClear) {
            edxinyanphonenum!!.setText("")
        } else if (view === ivPwdClear) {
            edfwpwd!!.setText("")
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val window = dialog.window
        if (window != null) {
            window.decorView.setPadding(0, 0, 0, 0)
            window.setBackgroundDrawableResource(android.R.color.transparent)
            val wm = activity!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            val screenWith = dm.widthPixels
            if (screenWith != 0) {
                window.setLayout((screenWith * 0.86).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            } else {
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            }

            window.setWindowAnimations(mAnimStyle)
        }
        return dialog
    }

}
