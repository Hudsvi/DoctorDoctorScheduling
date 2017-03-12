package sylu.com.doctorscheduling.constants;

import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/3/4 15:30.
 */

public enum IconNotfocused {
    RESERVATION(R.drawable.yuyue,0),
    TEMPLATE(R.drawable.muban,1),
    REEXAMINE(R.drawable.fuhe,2),
    AUTO_SCHEDULING(R.drawable.paiban,3),
    RELEASE(R.drawable.fabu,4);
    private int icon_id;
    private int icon_index;
    IconNotfocused(int icon, int index) {
        this.icon_id=icon;
        this.icon_index=index;
    }
    public static int getIcon(int index){
        for (IconNotfocused icon :
                IconNotfocused.values()) {
            if(icon.icon_index==index){
                return icon.icon_index;
            }
        }
        return 0;
    }
}
