package com.baron.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/***
 @package com.baron.ad.vo
 @author Baron
 @create 2020-08-30-12:04 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanGetRequest {

    private Long userId;
    private List<Long> ids;

    public boolean validate(){

        return userId != null && !CollectionUtils.isEmpty(ids);
    }
}
