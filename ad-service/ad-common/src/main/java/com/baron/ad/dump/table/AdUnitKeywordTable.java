package com.baron.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 @package com.baron.ad.dump.table
 @author Baron
 @create 2020-09-02-10:50 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordTable {
    private Long unitId;
    private String keyword;
}
