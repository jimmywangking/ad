package com.baron.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 @package com.baron.ad.vo
 @author Baron
 @create 2020-08-29-5:08 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
    private Long userId;
    private String userName;
    private String token;
    private Date createTime;
    private Date updateTime;

}
