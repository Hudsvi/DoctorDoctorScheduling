package sylu.com.doctorscheduling.internet.http;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import sylu.com.doctorscheduling.internet.entity.Dept_Admin_Info;

import static sylu.com.doctorscheduling.constants.Constants.BASE_URL;

/**
 * Created by Hudsvi on 2017/3/9 17:45.
 */

public interface HttpService {
    @FormUrlEncoded
    @POST(BASE_URL + "/login")
    Observable<Dept_Admin_Info> courierLogin(@FieldMap Map<String, String> info);
}
