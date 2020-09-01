package com.baron.ad.index.creativeUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 @package com.baron.ad.index
 @author Baron
 @create 2020-08-31-5:49 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitObject {
    private Long adId;
    private Long creativeId;
    private Long unitId;

    //adId-unitId
}
