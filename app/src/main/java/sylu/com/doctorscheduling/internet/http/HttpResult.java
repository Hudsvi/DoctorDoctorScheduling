package sylu.com.doctorscheduling.internet.http;

/**
 * Created by Hudsvi on 2017/3/8 9:26.
 */
/**
* 将网络服务端返回的JSON数据,解析成pojo对象（pojo是一种简单的类似JavaBean协助类）*/
public class HttpResult<T> {
    private T data;

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
