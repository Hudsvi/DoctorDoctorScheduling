package sylu.com.doctorscheduling.custom.muban;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Hudsvi on 2017/3/15 10:40.
 */

public class Doctor_List_Item implements Serializable{

    private String doctor_name;

    public Doctor_List_Item(String doctor_name) {
        this.doctor_name = doctor_name;
    }


    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }
}
