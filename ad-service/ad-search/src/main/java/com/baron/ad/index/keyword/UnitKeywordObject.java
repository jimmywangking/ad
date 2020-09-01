package com.baron.ad.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 @package com.baron.ad.index
 @author Baron
 @create 2020-08-31-9:46 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitKeywordObject {

    private Long unitId;
    private String keyword;

    void update(UnitKeywordObject object){
        if (null != object.getUnitId()){
            this.unitId = object.getUnitId();
        }
        if (null != object.getKeyword()){
            this.keyword = object.getKeyword();
        }

    }

}
