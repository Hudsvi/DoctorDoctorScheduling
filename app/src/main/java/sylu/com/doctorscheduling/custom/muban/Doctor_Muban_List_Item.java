package sylu.com.doctorscheduling.custom.muban;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Hudsvi on 2017/3/15 10:40.
 */

public class Doctor_Muban_List_Item implements Serializable{
    public Doctor_Muban_List_Item(String dept, String date, String doctor_name) {
        this.dept = dept;
        this.date = date;
        this.doctor_name = doctor_name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    private String dept;
    private String date;
    private String doctor_name;



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }
}
