package com.baron.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/***
 @package com.baron.ad.client
 @author Baron
 @create 2020-08-30-6:09 PM
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
