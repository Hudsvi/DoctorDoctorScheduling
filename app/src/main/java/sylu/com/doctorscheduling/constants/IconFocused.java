package sylu.com.doctorscheduling.constants;

import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/3/4 15:00.
 */

public enum IconFocused {
    RESERVATION(R.drawable.yuyue1,0),
    TEMPLATE(R.drawable.muban1,1),
    AUTO_SCHEDULING(R.drawable.paiban1,2),
    REEXAMINE(R.drawable.fuhe1,3),
    PERSONAL(R.drawable.person1,4);
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
                return icon.icon_id;
            }
        }
        return 0;
    }
}
