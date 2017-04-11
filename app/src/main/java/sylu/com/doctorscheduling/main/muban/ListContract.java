package sylu.com.doctorscheduling.main.muban;

import java.util.List;

import sylu.com.doctorscheduling.custom.muban.Doctor_Muban_List_Item;
import sylu.com.doctorscheduling.custom.muban.Muban_MyArrayAdapter;

/**
 * Created by Hudsvi on 2017/3/15 19:30.
 */

public interface ListContract {
    void delete(Muban_MyArrayAdapter.ViewHolder holder);
    void cancel(Muban_MyArrayAdapter.ViewHolder holder);
    void update(Muban_MyArrayAdapter.ViewHolder holder);
}
