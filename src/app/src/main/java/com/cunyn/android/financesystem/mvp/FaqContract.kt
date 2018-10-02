import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.IView


interface FaqContract {
    interface Presenter:IPresenter{
        fun getFaqData(customerId: Long)


    }

    interface View:IView<Presenter>{
        fun getFagDataCallback(apiResult: ApiResult<FeedbackBean?>)

    }
}