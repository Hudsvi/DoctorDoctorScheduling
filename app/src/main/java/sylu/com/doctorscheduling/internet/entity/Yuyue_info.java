package sylu.com.doctorscheduling.internet.entity;

/**
 * Created by Hudsvi on 2017/3/31 10:25.
 */

public class Yuyue_info {
    private String deptno,queryDate;//----------科室，按天查询预约信息
    private String validateDate1;
    private String validateDate2;
    private String startTime1;
    private String startTime2;
    private String allowingdays;
    private String previousdays;
    private String defaulttimes;//最大违约次数

    public String getDefaulttimes() {
        return defaulttimes;
    }

    public void setDefaulttimes(String defaulttimes) {
        this.defaulttimes = defaulttimes;
    }

    public String getPreviousdays() {
        return previousdays;
    }

    public void setPreviousdays(String previousdays) {
        this.previousdays = previousdays;
    }

    public String getAllowingdays() {
        return allowingdays;
    }

    public void setAllowingdays(String allowingdays) {
        this.allowingdays = allowingdays;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getValidateDate2() {
        return validateDate2;
    }

    public void setValidateDate2(String validateDate2) {
        this.validateDate2 = validateDate2;
    }

    public String getValidateDate1() {
        return validateDate1;
    }

    public void setValidateDate1(String validateDate1) {
        this.validateDate1 = validateDate1;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

}
