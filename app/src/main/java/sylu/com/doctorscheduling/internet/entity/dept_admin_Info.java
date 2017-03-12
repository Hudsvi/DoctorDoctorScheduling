package sylu.com.doctorscheduling.internet.entity;

import java.io.Serializable;

/**
 * Created by Hudsvi on 2017/3/8 10:05.
 */

public class Dept_Admin_Info implements Serializable {
    private String password;
    private String message;
    private AdminBean admin;

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public String getPassword() {
        return password;
    }
    public  void setAdmin(AdminBean bean){
        this.admin=bean;
    }
    public AdminBean getAdmin(){
        return admin;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class AdminBean implements Serializable{
        private String admin_alias;//登录者的昵称
        private String admin_name; //登录者姓名
        private String token;//设备ID
        private int phone;//登录账号
        private boolean isWorking;//当前是否在工作状态，用于提示当前登录者是否有需要处理的事务（门诊办|客户人员）
        private int unAuditedSubmitting;//需要审核的提交数量（门诊办）
        private int unReleasedSubmitting;//需要发布的提交数量（客服人员）
        private int[] managed_departments;//所有已经绑定的科室。（门诊办|科室管理员）
        private int login_identifies;//判断登录者身份

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getPhone() {
            return phone;
        }

        public void setPhone(int phone) {
            this.phone = phone;
        }

        public boolean isWorking() {
            return isWorking;
        }

        public void setWorking(boolean working) {
            isWorking = working;
        }

        public int getUnAuditedSubmitting() {
            return unAuditedSubmitting;
        }

        public void setUnAuditedSubmitting(int unAuditedSubmitting) {
            this.unAuditedSubmitting = unAuditedSubmitting;
        }

        public int getUnReleasedSubmitting() {
            return unReleasedSubmitting;
        }

        public void setUnReleasedSubmitting(int unReleasedSubmitting) {
            this.unReleasedSubmitting = unReleasedSubmitting;
        }

        public boolean isDepartments_bound() {
            return departments_bound;
        }

        public void setDepartments_bound(boolean departments_bound) {
            this.departments_bound = departments_bound;
        }

        public int getLogin_identifies() {
            return login_identifies;
        }

        public void setLogin_identifies(int login_identifies) {
            this.login_identifies = login_identifies;
        }

        public int[] getManaged_departments() {
            return managed_departments;
        }

        public void setManaged_departments(int[] managed_departments) {
            this.managed_departments = managed_departments;
        }

        public String getAdmin_alias() {
            return admin_alias;
        }

        public String getAdmin_name() {
            return admin_name;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
        }

        public void setAdmin_alias(String admin_alias) {
            this.admin_alias = admin_alias;
        }

        private boolean departments_bound;//判断是否绑定相应科室

    }
}
