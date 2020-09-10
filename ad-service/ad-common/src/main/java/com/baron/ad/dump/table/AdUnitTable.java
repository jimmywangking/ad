package com.baron.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 @package com.baron.ad.dump.table
 @author Baron
 @create 2020-09-02-10:46 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitTable {

    private Long unitid;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;
}
