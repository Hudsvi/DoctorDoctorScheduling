package sylu.com.doctorscheduling.main.muban;

import android.content.Context;

import sylu.com.doctorscheduling.BasePresenter;
import sylu.com.doctorscheduling.BaseView;
import sylu.com.doctorscheduling.custom.muban.Doctor_Details;
import sylu.com.doctorscheduling.custom.muban.MyArrayAdapter;

/**
 * Created by Hudsvi on 2017/3/17 10:55.
 */

public interface DetailsContract {
    interface View extends BaseView<DetailsContract.Presenter>{
        void setDetails(MyArrayAdapter.ViewHolder holder);
        Context getContext();
    }
    interface Presenter extends BasePresenter {
        void getDetails(Doctor_Details doctor_details);
    }
}
