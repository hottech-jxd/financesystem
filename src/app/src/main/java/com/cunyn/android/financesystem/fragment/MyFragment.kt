package com.cunyn.android.financesystem.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

import com.cunyn.android.financesystem.R
import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.MyPresenter
import com.cunyn.android.financesystem.widget.RecyclerViewDividerOneLine
import com.guoxintaiyi.android.missionwallet.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_myfragment.*
import kotlinx.android.synthetic.main.layout_header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Myragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MyFragment : BaseFragment<MyContract.Presenter>(),
        MyContract.View,
BaseQuickAdapter.RequestLoadMoreListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var datas = ArrayList<TradeRecordBean>()
    private var myAdapter:MyAdapter?=null
    var iPresenter=MyPresenter(this)
    var pageIndex=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        my_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        my_progress.visibility=View.GONE
    }

    override fun initView() {
        header_title.text="我的借款"
        header_left_image.setImageResource(R.mipmap.arrow_left)
        header_left_image.setOnClickListener(this)

        my_username.text = Variable.UserBean!!.UserName
        my_userid.text = "ID:${Variable.UserBean!!.UserId}"

//        datas.add("a")
//        datas.add("b")
//        datas.add("c")
//        datas.add("c")
//        datas.add("c")
        myAdapter= MyAdapter(datas)
        my_recyclerview.adapter = myAdapter
        my_recyclerview.layoutManager=LinearLayoutManager(context!!)
        my_recyclerview.addItemDecoration(RecyclerViewDividerOneLine(context!!
                ,ContextCompat.getColor(context!!,R.color.line_color)
                ,1f,0f,0f))

        myAdapter!!.setOnLoadMoreListener(this, my_recyclerview)

        iPresenter.getApplyRecords(Constants.CUSTOMERID , Variable.UserBean!!.UserId , pageIndex+1 , Constants.PAGE_SIZE)
    }

    override fun fetchData() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id){
            R.id.header_left_image->{
                closeFragment()
            }
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_myfragment
    }

    override fun interceptBackPressed(): Boolean {
        return false
    }

    override fun onLoadMoreRequested() {
        iPresenter.getApplyRecords(Constants.CUSTOMERID , Variable.UserBean!!.UserId
                , pageIndex+1 ,Constants.PAGE_SIZE)
    }

    override fun getApplyRecordsCallback(apiResult: ApiResult<ArrayList<TradeRecordBean>?>) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        if(apiResult.data==null){
            return
        }



        if (  apiResult.data!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == -1) {
                myAdapter!!.loadMoreEnd(true)
            } else {
                myAdapter!!.loadMoreEnd()
            }
            pageIndex++

        } else {
            myAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 0) {
            myAdapter!!.setNewData(apiResult.data)
            myAdapter!!.disableLoadMoreIfNotFullPage(my_recyclerview)
        } else {
            myAdapter!!.addData(apiResult.data!!)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Myragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}


class MyAdapter(data : ArrayList<TradeRecordBean>)
    : BaseQuickAdapter<TradeRecordBean, BaseViewHolder>(R.layout.layout_my_item, data){

    override fun convert(helper: BaseViewHolder?, item: TradeRecordBean?) {

            helper!!.setText(R.id.my_item_time , item!!.ApplyTime)
        helper.setText(R.id.my_item_status , item.ApplyStatusStr)
    }
}
