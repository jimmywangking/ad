package com.baron.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 @package com.baron.ad.dump
 @author Baron
 @create 2020-09-02-10:44 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanTable {
    private Long id;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
}
