package com.baron.ad.constants;

import lombok.Getter;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-27-4:40 PM
 */
@Getter
public enum CommonStatus {
    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
