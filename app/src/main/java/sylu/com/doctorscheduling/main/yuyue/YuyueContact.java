package sylu.com.doctorscheduling.main.yuyue;

import java.sql.Connection;
import java.util.List;

import sylu.com.doctorscheduling.BasePresenter;
import sylu.com.doctorscheduling.BaseView;

/**
 * Created by Hudsvi on 2017/3/29 12:42.
 */

public interface YuyueContact {
    interface View extends BaseView<Presenter> {
        void getDepart();//----科室
        void setDepart(List<String> list) ;
        String getValidity();//--------有效期
        void selectInfo(String date,String s);//-----------------展示预约信息
        void addInfo();//-----------增加预约信息
        void deleteInfo();//-----------删除预约信息
        void modifyInfo();//--------------修改预约信息
        void setInfoEnabled(boolean enabled);

    }
    interface Presenter extends BasePresenter {
        void setBookingSituation();//设置预约信息
    }
}
