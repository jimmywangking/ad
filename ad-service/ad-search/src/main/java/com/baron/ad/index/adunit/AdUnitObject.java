package com.baron.ad.index.adunit;

import com.baron.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 @package com.baron.ad.index
 @author Baron
 @create 2020-08-30-11:31 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {
    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;

    private AdPlanObject adPlanObject;

    void update(AdUnitObject newObject){
        if (null != newObject.getUnitId()){
            this.unitId = newObject.getUnitId();
        }
        if (null != newObject.getUnitStatus()){
            this.unitStatus = newObject.getUnitStatus();
        }
        if (null != newObject.getPositionType()){
            this.positionType = newObject.getPositionType();
        }
        if (null != newObject.getPlanId()){
            this.planId = newObject.getPlanId();
        }
    }
}
