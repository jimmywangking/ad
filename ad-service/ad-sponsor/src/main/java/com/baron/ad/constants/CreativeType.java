package com.baron.ad.constants;

import lombok.Getter;

/***
 @package com.baron.ad.constants
 @author Baron
 @create 2020-08-29-3:50 PM
 */
@Getter
public enum CreativeType {
    IMAGE(1,"图片"),
    VIDEO(2,"视频"),
    TEXT(3,"文本");
    private int type;
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
