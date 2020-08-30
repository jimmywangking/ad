package com.baron.ad.utils;


import org.apache.commons.codec.digest.DigestUtils;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-30-11:44 AM
 */
public class CommonUtils {

    public static  String md5(String value){
        return DigestUtils.md5Hex(value).toUpperCase();
    }
}
