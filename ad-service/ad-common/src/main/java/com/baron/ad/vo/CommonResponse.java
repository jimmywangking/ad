package com.baron.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 @package com.baron.ad.vo
 @author Baron
 @create 2020-08-25-11:20 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
