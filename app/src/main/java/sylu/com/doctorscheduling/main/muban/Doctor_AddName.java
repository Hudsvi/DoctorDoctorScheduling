package sylu.com.doctorscheduling.main.muban;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.custom.MySharedPreferences;
import sylu.com.doctorscheduling.internet.jdbc.SQLConnector;
import sylu.com.doctorscheduling.utils.NetUtils;
import sylu.com.doctorscheduling.utils.manager.PhoneManager;
import sylu.com.doctorscheduling.view.UIForeRunner;

/**
 * Created by Hudsvi on 2017/4/6 9:07.
 */

public class Doctor_AddName extends Activity implements DialogInterface.OnCancelListener {
    @BindView(R.id.muban_doctor_name_title)
    TextView mubanDoctorNameTitle;//-------------标题
    @BindView(R.id.muban_doctor_name_add_cons)
    ConstraintLayout mubanDoctorNameAddCons;//----------------添加的布局
    @BindView(R.id.muban_add_name_update_tv)
    TextView mubanAddNameUpdateTv;
    @BindView(R.id.muban_add_name_update_edittext)
    EditText mubanAddNameUpdateEdittext;
    @BindView(R.id.muban_add_name_update_cons)
    ConstraintLayout mubanAddNameUpdateCons;//-------------修改的布局
    private Connection conn;
    private PreparedStatement pre_sta;
    private ResultSet rs;
    @BindView(R.id.muban_doctor_name_back)
    ImageView mubanDoctorNameBack;
    @BindView(R.id.muban_doctor_name_ok)
    ImageView mubanDoctorNameOk;
    @BindView(R.id.muban_edit_name_title_ll)
    ConstraintLayout mubanEditNameLl;
    @BindView(R.id.muban_add_name_tv)
    TextView mubanAddNameTv;
    @BindView(R.id.muban_add_name_edittext)
    EditText mubanAddNameEdittext;
    @BindView(R.id.muban_add_dept_tv)
    TextView mubanAddDeptTv;
    @BindView(R.id.muban_add_dept_spinner)
    Spinner mubanAddDeptSpinner;
    private UIForeRunner foreRunner;
    private String name, dept;//增加医生的姓名，以及科室
    private String newname;//-------------新名称
    private int type=0;//--------------------医生姓名操作的类型，1为增加医生，2为修改姓名
    private static final int ERRO = 0;
    private static final int SUCESS_DEPT = 1;
    private static final int ERRO_DEPT = 2;
    private ArrayList<String> dept_ls;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERRO:
                    if (foreRunner != null) {
                        foreRunner.dissmissNetLoadingDialog();
                    }
                    break;
                case SUCESS_DEPT:
                    setAdapter();
                    break;
                case ERRO_DEPT:
                    break;
            }
        }
    };

    private void setAdapter() {
        ArrayAdapter adapter = new ArrayAdapter(Doctor_AddName.this,
                android.R.layout.simple_spinner_dropdown_item, dept_ls);
        mubanAddDeptSpinner.setAdapter(adapter);
        foreRunner.dissmissNetLoadingDialog();
    }

    @OnItemSelected(R.id.muban_add_dept_spinner)
    public void onItemSelected(int position) {
        dept = mubanAddDeptSpinner.getSelectedItem().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muban_add_doctor_name);
        ButterKnife.bind(this);
        type=getIntent().getIntExtra(Constants.MUBAN_NAME_TYPE,0);
        if(type==1) {
            mubanAddNameUpdateCons.setVisibility(View.GONE);
            mubanDoctorNameAddCons.setVisibility(View.VISIBLE);
            if (NetUtils.isNetworkAvailable(Doctor_AddName.this)) {
                initAdd();
            } else {
                Toast.makeText(this, "无法连接到服务器", Toast.LENGTH_SHORT).show();
            }
        }else if(type==2){
            mubanDoctorNameAddCons.setVisibility(View.GONE);
            mubanAddNameUpdateCons.setVisibility(View.VISIBLE);
            mubanDoctorNameTitle.setText("修改名称");

        }
        else{
            Toast.makeText(this, "类型错误！", Toast.LENGTH_SHORT).show();
        }
    }

    private void initModidy() {

    }

    private void initAdd() {
        foreRunner = new UIForeRunner(Doctor_AddName.this);
        foreRunner.showNetLoadingDialog("加载科室中", this);
        dept_ls = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                String phone = MySharedPreferences.getInstance(Doctor_AddName.this).getStringValue(Constants.LOGIN_USER_ID);
                conn = SQLConnector.getInstance(Doctor_AddName.this).initSQL();
                try {
                    if (!phone.equals("")) {
                        pre_sta = conn.prepareStatement("select dept_no from" +
                                " admin_dept where dept_user=?");
                        pre_sta.setString(1, phone);
                        rs = pre_sta.executeQuery();
                        while (rs.next()) {
                            dept_ls.add(rs.getString("dept_no"));
                        }
                        sendmessage(SUCESS_DEPT, null);//--------查询成功回调
                    }
                } catch (Exception e) {
                    sendmessage(ERRO, null);
                } finally {
                    closeSQL();
                }
            }
        }.start();
    }

    private void closeSQL() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.muban_doctor_name_back, R.id.muban_doctor_name_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.muban_doctor_name_back:
                finish();
                break;
            case R.id.muban_doctor_name_ok:
                if(type==1) {
                    String name = mubanAddNameEdittext.getText().toString();
                    if (PhoneManager.containEmpty(name) || name.length() < 2 || name.length() > 4) {
                        Toast.makeText(this, "姓名格式不正确", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.MUBAN_NAME_ADD, name);
                        intent.putExtra(Constants.MUBAN_DEPT_ADD, dept);
                        setResult(Constants.MUBAN_D_NAME_ADD_CODE, intent);
                        finish();
                    }
                }
                else if(type==2){
                    String newname=mubanAddNameUpdateEdittext.getText().toString();
                    if (PhoneManager.containEmpty(newname) || newname.length() < 2 || newname.length() > 4) {
                        Toast.makeText(this, "姓l名格式不正确", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.MUBAN_NAME_UPDATE, newname);
                        setResult(Constants.MUBAN_D_NAME_UPDATE_CODE,intent);
                        finish();
                    }
                }
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.layout_stay, R.anim.layout_translate_out);
    }

    private void sendmessage(int what, Object ob) {
        Message m = new Message();
        m.what = what;
        m.obj = ob;
        handler.sendMessage(m);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }
}
