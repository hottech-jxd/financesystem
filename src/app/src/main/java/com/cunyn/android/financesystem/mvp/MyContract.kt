import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.IView


interface MyContract {
    interface Presenter:IPresenter{
        fun getApplyRecords(customerId: Long, userId:Long, page:Int , size:Int)


    }

    interface View:IView<Presenter>{
        fun getApplyRecordsCallback(apiResult: ApiResult< ArrayList<TradeRecordBean>?>)

    }
}