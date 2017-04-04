package sylu.com.doctorscheduling.utils;

/**
 * Created by Hudsvi on 2017/3/5 17:14.
 */

public enum PhoneNumberUtils {
   NO_1("133",1),
   NO_2("153",2),
   NO_3("180",3),
   NO_4("181",4),
   NO_5("189",5),
   NO_6("177",6),
   NO_7("173",7),
   NO_8("149",8),
   NO_9("130",9),
   NO_10("131",10),
   NO_11("132",11),
   NO_12("155",12),
   NO_13("156",13),
   NO_14("145",14),
   NO_15("185",15),
   NO_16("186",16),
   NO_17("176",17),
   NO_18("175",18),
   NO_19("134",19),
   NO_20("135",20),
   NO_21("136",21),
   NO_22("137",22),
   NO_23("138",23),
   NO_24("139",24),
   NO_25("150",25),
   NO_26("151",26),
   NO_27("152",27),
   NO_28("157",28),
   NO_29("158",29),
   NO_30("159",30),
   NO_31("182",31),
   NO_32("183",32),
   NO_33("184",33),
   NO_34("187",34),
   NO_35("188",35),
   NO_36("147",36),
   NO_37("178",37);
    String phone_num;
    int index;


    PhoneNumberUtils(String s, int i) {
        this.phone_num=s;
        this.index=i;
    }

    public static boolean validatedNumber(String num){
        for (PhoneNumberUtils p:PhoneNumberUtils.values()) {
            if(p.phone_num.equals(num))
                return true;
        }
        return false;
    }
}
