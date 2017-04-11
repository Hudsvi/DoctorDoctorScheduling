package sylu.com.doctorscheduling.constants;

/**
 * Created by Hudsvi on 2017/2/15 21:37.
 */

public class Constants {
    public static final String BASE_URL="jdbc:mysql://hudsvi.vicp.io:3306/" +
            "doctor?connectTimeout=10000&socketTimeout=12000";
    public static final String PATH_OF_PROGRAM="app/doctor";
    public static final String FIRST_TIME_LAUNCHED="ft";
    public static final String SHARED_PREFS_LOCATION="SPL_admin";
    public static final String PICASSO_CACHE = "picasso_cache";
    public static final String COOKIE = "cookie";
    public static final String LOGIN_STATUS="login_status";
    public static final String LOGIN_DEVICE_ID="device-id";
    public static final String LOGIN_USER_ID="login_user_id";
    public static final String USER_IDENTIFY="user_identify";
    public static final String MUBAN_DOCTOR_DETAILS="muban_details";
    public static final String MUBAN_DOCTOR_LIST_NAME="muban_doctor_name";
    public static final String MUBAN_DOCTOR_LIST_DATE ="muban_doctor_date";
    public static final String MUBAN_WEEK="muban_day_of_week";
    public static final String MUBAN_DEPT="muban_dept";
    public static final String MUBAN_WORKING_STATE="muban_working_state";
    public static final String MUBAN_NAME_ADD="add_doctor_name";//-------添加姓名的回调数据
    public static final String MUBAN_DEPT_ADD="add_doctor_dept";//----------添加科室的回调数据
    public static final String MUBAN_NAME_TYPE ="type_doctor_name";//-----修改医生姓名时的类型
    public static final String MUBAN_NAME_UPDATE="modyfy_doctor_name";//-----修改医生姓名的回调数据
    public static final int MUBAN_D_NAME_ADD_CODE=101;//---------添加医生姓名的结果码
    public static final int MUBAN_D_NAME_UPDATE_CODE=102;//---------修改医生姓名的结果码
    public static final String MUBAN_D_LAST_UPDATE_TIME="muban_last_time";//---------模板最后刷新时间
    public static final String DRIVER="com.mysql.jdbc.Driver";
    public static final String SQL_USER="doctor";
    public static final String SQL_PWD="123456h";
    public static final String PTR_UPDATE_TIME="ptr_updated_time";
    public static final int TIME_OUT=6;
    public static final int  TIME_OUT_READ=8;


}
