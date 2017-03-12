package sylu.com.doctorscheduling.internet.http;

import rx.Observable;
import rx.Subscriber;
import sylu.com.doctorscheduling.internet.LoginRequest;
import sylu.com.doctorscheduling.internet.entity.Dept_Admin_Info;

/**
 * Created by Hudsvi on 2017/3/9 17:43.
 */
/**
 * app的数据处理中心，与服务端进行交互的功能接口*/
public class HttpMethods {
    private HttpManager manager;

    private static HttpMethods httpMethods_instance=new HttpMethods();

    private HttpMethods(){
        manager=HttpManager.getInstance();
    }
    public static HttpMethods getHttpMethods_instance() {
        return httpMethods_instance;
    }
    /**
    *登录接口
     */
    public void Dept_Admin_Login(LoginRequest request, Subscriber<Dept_Admin_Info> subscriber){
        Observable<Dept_Admin_Info> login=manager.getHttpService().courierLogin(request.getParamMap());
        manager.toSubscribe(login,subscriber);
    }
}

