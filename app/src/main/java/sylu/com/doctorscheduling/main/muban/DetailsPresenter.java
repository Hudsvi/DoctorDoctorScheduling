package sylu.com.doctorscheduling.main.muban;

import sylu.com.doctorscheduling.custom.muban.Doctor_Muban_Details;

/**
 * Created by Hudsvi on 2017/4/10 11:34.
 */

public class DetailsPresenter implements DetailsContract.Presenter {
    private DetailsContract.View view;
    private DetailsContract.Presenter presenter;

    public DetailsPresenter(DetailsContract.View view, DetailsContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
        view.setPresenter(this);
    }

    @Override
    public void getDetails(Doctor_Muban_Details doctor_Muban_details) {

    }
}
