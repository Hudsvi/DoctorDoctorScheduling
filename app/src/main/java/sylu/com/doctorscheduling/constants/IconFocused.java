package sylu.com.doctorscheduling.constants;

import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/3/4 15:00.
 */

public enum IconFocused {
    RESERVATION(R.drawable.yuyue1,0),
    TEMPLATE(R.drawable.muban1,1),
    REEXAMINE(R.drawable.fuhe1,2),
    AUTO_SCHEDULING(R.drawable.paiban1,3),
    RELEASE(R.drawable.fabu1,4);
    private int icon_id;
    private int icon_index;
    IconFocused(int icon, int index) {
        this.icon_id=icon;
        this.icon_index=index;
    }
    public static int getIcon(int index){
        for (IconFocused icon :
                IconFocused.values()) {
            if(icon.icon_index==index){
                return icon.icon_index;
            }
        }
        return 0;
    }
}
