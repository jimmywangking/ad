package com.baron.ad.index.enums;

import lombok.Getter;

/***
 @package com.baron.ad.index
 @author Baron
 @create 2020-09-05-10:51 PM
 */
@Getter
public enum DataLevel {
    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 3"),
    LEVEL4("4", "level 4");

    private String level;
    private String desc;


    DataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}
