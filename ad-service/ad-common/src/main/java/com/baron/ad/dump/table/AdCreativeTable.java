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
public class AdCreativeTable {

    private Long adId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer weight;
    private Long size;
    private Integer auditStatus;
    private String adUrl;
}
