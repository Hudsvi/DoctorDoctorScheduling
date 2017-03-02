package sylu.com.doctorscheduling;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Hudsvi on 2017/2/16 11:27.
 */
public abstract class BaseAdapter1 extends BaseAdapter {
    protected final ArrayList lists;
    protected Context context;
    public BaseAdapter1(ArrayList lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (lists == null) {
            return 0;
        }
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
