package sylu.com.doctorscheduling.custom.muban;

import java.io.Serializable;

/**
 * Created by Hudsvi on 2017/3/16 12:20.
 */

public class Doctor_Muban_Details implements Serializable{
    private String diagnose_am;
    private String start_am;
    private String end_am;

    private String amount_am;

    private String diagnose_pm;

    public String getStart_pm() {
        return start_pm;
    }

    public void setStart_pm(String start_pm) {
        this.start_pm = start_pm;
    }

    public String getAmount_pm() {
        return amount_pm;
    }

    public void setAmount_pm(String amount_pm) {
        this.amount_pm = amount_pm;
    }

    public String getEnd_pm() {
        return end_pm;
    }

    public void setEnd_pm(String end_pm) {
        this.end_pm = end_pm;
    }

    public String getDiagnose_pm() {
        return diagnose_pm;
    }

    public void setDiagnose_pm(String diagnose_pm) {
        this.diagnose_pm = diagnose_pm;
    }

    public String getAmount_am() {
        return amount_am;
    }

    public void setAmount_am(String amount_am) {
        this.amount_am = amount_am;
    }

    public String getEnd_am() {
        return end_am;
    }

    public void setEnd_am(String end_am) {
        this.end_am = end_am;
    }

    public String getDiagnose_am() {
        return diagnose_am;
    }

    public void setDiagnose_am(String diagnose_am) {
        this.diagnose_am = diagnose_am;
    }

    public String getStart_am() {
        return start_am;
    }

    public void setStart_am(String start_am) {
        this.start_am = start_am;
    }

    private String start_pm;
    private String end_pm;
    private String amount_pm;
    public Doctor_Muban_Details(String end_am, String diagnose_am, String start_am, String amount_am,
                                String start_pm, String diagnose_pm, String end_pm, String amount_pm) {
        this.end_am = end_am;
        this.diagnose_am = diagnose_am;
        this.start_am = start_am;
        this.amount_am = amount_am;
        this.start_pm = start_pm;
        this.diagnose_pm = diagnose_pm;
        this.end_pm = end_pm;
        this.amount_pm = amount_pm;
    }

    public Doctor_Muban_Details() {
    }
}
