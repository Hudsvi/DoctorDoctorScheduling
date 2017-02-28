package sylu.com.doctorscheduling.utils.manager;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/14 17:35.
 */

public class ActivityManager extends Activity {
    private  ArrayList<Activity> activities =new ArrayList<Activity>();
    private static ActivityManager manger;
    public static ActivityManager getInstance(){
        if (manger == null) {
            manger=new ActivityManager();
        }
        return manger;
    }
    public void AddIntoManager(Activity activity){
        if(!activities.contains(activity)){
            activities.add(activity);
        }
    }
    public void DropFromManager(Activity activity){
        if(activities.contains(activity)) {
            activities.remove(activity);
        }
    }
    public void Exit(){
        for (Activity acts :activities) {
            acts.finish();
        }
        Process.killProcess(Process.myPid());//退出时彻底清理残留进程
    }
}
