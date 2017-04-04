package sylu.com.doctorscheduling.internet.jdbc;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sylu.com.doctorscheduling.constants.Constants;

/**
 * Created by Hudsvi on 2017/3/27 21:44.
 */

public class SQLConnector {
    private String driver;
    private String url;
    private String user;
    private String password;
    private static SQLConnector sqlConnector = null;
    private static Connection connection;
    /*private PreparedStatement pre_sta;
    private ResultSet rset;*/
    private Context context;

    public SQLConnector(Context context) {
        this.context = context;
//        initSQL(context);
    }

    public Connection  initSQL() {
        try {
            driver = Constants.DRIVER;
            url = Constants.BASE_URL;
            user = Constants.SQL_USER;
            password = Constants.SQL_PWD;
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
        }
        return connection;
    }

    public static SQLConnector getInstance(Context context) {
        if (sqlConnector == null) {
            sqlConnector = new SQLConnector(context);
        }
        return sqlConnector;
    }

    private void toast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
/*
    public void CloseSQL() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/
}
