package com.baron.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-31-10:05 AM
 */
public class CommonUtils {

    //范型 当key在map里不存在的时候，通过一个Supplier的对象，返回一个新的V通过factory.get()获得
    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory){
        return map.computeIfAbsent(key, k -> factory.get());
    }

}
