package com.baron.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-31-10:05 AM
 */
@Slf4j
public class CommonUtils {

    //范型 当key在map里不存在的时候，通过一个Supplier的对象，返回一个新的V通过factory.get()获得
    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory){
        return map.computeIfAbsent(key, k -> factory.get());
    }

    //拼接 多个字符串
    public static String stringConcat(String... args){
        StringBuilder result =  new StringBuilder();
        for (String arg : args){
            result.append(arg).append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    // Tue Jan 05 22:50:02 CST 2020
    public static Date parseStringDate(String dateString){

        try {
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            return DateUtils.addHours(dateFormat.parse(dateString),-8);
        } catch (ParseException ex){
            log.error("parseStringDate error :{}", dateString);
            return null;
        }
    }

}
