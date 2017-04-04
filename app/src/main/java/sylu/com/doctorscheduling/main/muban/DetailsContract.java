package sylu.com.doctorscheduling.main.muban;

import android.content.Context;

import sylu.com.doctorscheduling.BasePresenter;
import sylu.com.doctorscheduling.BaseView;
import sylu.com.doctorscheduling.custom.muban.Doctor_Muban_Details;
import sylu.com.doctorscheduling.custom.muban.Muban_MyArrayAdapter;

/**
 * Created by Hudsvi on 2017/3/17 10:55.
 */

public interface DetailsContract {
    interface View extends BaseView<DetailsContract.Presenter>{
        void setDetails(Muban_MyArrayAdapter.ViewHolder holder);
        Context getContext();
    }
    interface Presenter extends BasePresenter {
        void getDetails(Doctor_Muban_Details doctor_Muban_details);
    }
}
