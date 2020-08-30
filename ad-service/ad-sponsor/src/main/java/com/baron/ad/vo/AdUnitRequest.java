package com.baron.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/***
 @package com.baron.ad.vo
 @author Baron
 @create 2020-08-30-1:43 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;
    private Integer positionType;
    private Long budget;

    public boolean createValidate(){

        return null!= planId && StringUtils.isNotEmpty(unitName)
                &&positionType != null && budget != null
                &&budget >0;
    }
}
