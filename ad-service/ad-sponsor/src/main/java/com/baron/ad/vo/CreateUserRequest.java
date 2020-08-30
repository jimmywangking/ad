package com.baron.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-29-4:59 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    String userName;
    public Boolean validate(){
        return StringUtils.isNotEmpty(userName);
    }
}
