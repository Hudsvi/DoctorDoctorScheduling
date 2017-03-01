package sylu.com.doctorscheduling.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * Created by Hudsvi on 2017/2/19 16:01.
 */
public class WeakMapUtils<T> implements Iterable<T> {

    private final WeakHashMap<T, Object> weakMap = new WeakHashMap<T, Object>();//定义一个弱类型的HashMap对象
    private final Object mValue = new Object();//将弱HashMap对象隐式转换成HashSet类型的对象
    public void add(T obj) {
        if (obj != null) {
            weakMap.put(obj, mValue);
        }
    }

    public void clearMap() {
        if(weakMap!=null&&!isEmpty())
        weakMap.clear();
    }

    public void rmObject(T obj) {
        if(obj!=null&&!isEmpty())
        weakMap.remove(obj);
    }

    public T seeker() {
        if (size() == 0) {
            return null;
        }
        T result = null;
        for (T key : weakMap.keySet()) {
            if (key != null) {
                result = key;
                break;
            }
        }
        weakMap.remove(result);
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        ArrayList<T> list = new ArrayList<T>(size());
        for (T key : weakMap.keySet()) {
            if (key != null)
                list.add(key);
        }
        return list.iterator();
    }

    public boolean contains(T obj) {
        return weakMap.containsKey(obj);
    }

    public boolean isEmpty() {
        return weakMap.isEmpty();
    }

    public int size() {
        return weakMap.size();
    }

}
