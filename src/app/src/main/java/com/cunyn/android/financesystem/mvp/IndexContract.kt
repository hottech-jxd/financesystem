import com.cunyn.android.financesystem.bean.ApiResult
import com.cunyn.android.financesystem.bean.IndexUIBean
import com.cunyn.android.financesystem.bean.LoginUIBean
import com.cunyn.android.financesystem.bean.UserBean
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.IView


interface IndexContract {
    interface Presenter:IPresenter{
        fun getIndexUIData(customerId: Long)

        fun apply(userId:Long, customerId: Long)

    }

    interface View:IView<Presenter>{
        fun getIndexUIDataCallback(apiResult: ApiResult<ArrayList<IndexUIBean>>)
        fun applyCallback(apiResult: ApiResult<Any?>)
    }
}