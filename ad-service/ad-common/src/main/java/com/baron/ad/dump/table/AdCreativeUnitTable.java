package com.baron.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 @package com.baron.ad.dump.table
 @author Baron
 @create 2020-09-02-10:53 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeUnitTable {
    private Long adId;
    private Long creativeId;
    private Long unitId;
}
