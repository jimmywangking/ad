package com.baron.ad.constants;

import lombok.Getter;

/***
 @package com.baron.ad.constants
 @author Baron
 @create 2020-08-29-4:02 PM
 */
@Getter
public enum CreativeMaterialType {
    JPG(1,"jpg"),
    BMP(2,"bmp"),

    MP4(3,"mp4"),
    AVI(4,"avi"),

    TXT(5,"txt");

    private int code;
    private String desc;

    CreativeMaterialType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
