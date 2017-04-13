package sylu.com.doctorscheduling.custom.paiban;

/**
 * Created by Hudsvi on 2017/3/19 16:09.
 */

public class Doctor_Paiban_List_Item {
    private String name;
    private String time01,time02,time21,time22;//分别为am和pm的时间段
    private String amount1,amount2;//am和pm的放号量

    public Doctor_Paiban_List_Item() {
    }

    public Doctor_Paiban_List_Item(String name, String time01, String time02, String time21,
                                   String time22, String amount1, String amount2) {
        this.name = name;
        this.time01 = time01;
        this.time02 = time02;
        this.time21 = time21;
        this.time22 = time22;
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime01() {
        return time01;
    }

    public void setTime01(String time01) {
        this.time01 = time01;
    }

    public String getTime02() {
        return time02;
    }

    public void setTime02(String time02) {
        this.time02 = time02;
    }

    public String getTime21() {
        return time21;
    }

    public void setTime21(String time21) {
        this.time21 = time21;
    }

    public String getTime22() {
        return time22;
    }

    public void setTime22(String time22) {
        this.time22 = time22;
    }

    public String getAmount1() {
        return amount1;
    }

    public void setAmount1(String amount1) {
        this.amount1 = amount1;
    }

    public String getAmount2() {
        return amount2;
    }

    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }
}
