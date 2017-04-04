package sylu.com.doctorscheduling.main.yuyue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import rx.Observable;

/**
 * Created by Hudsvi on 2017/3/29 13:05.
 */

public class YuyuePresenter implements YuyueContact.Presenter{
    private YuyueContact.View view;
    private Connection conn;
    private PreparedStatement pre_sta;
    private ResultSet rs;

    public YuyuePresenter(YuyueContact.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void setBookingSituation() {

    }
}
