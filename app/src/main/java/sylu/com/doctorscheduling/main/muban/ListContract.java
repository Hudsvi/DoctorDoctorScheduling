package sylu.com.doctorscheduling.main.muban;

import sylu.com.doctorscheduling.custom.muban.MyArrayAdapter;

/**
 * Created by Hudsvi on 2017/3/15 19:30.
 */

public interface ListContract {
    void delete(long user_id, int positon,MyArrayAdapter.ViewHolder holder);
    void cancel(MyArrayAdapter.ViewHolder holder);
}
