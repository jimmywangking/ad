package com.baron.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 @package com.baron.ad.search.vo
 @author Baron
 @create 2020-09-10-6:54 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    //广告位编码
    private String adSlotCode;

    //流量类型
    private Integer positionType;

    private Integer width;
    private Integer height;

    //广告物料类型: 图片 视频
    private List<Integer> type;
    //最低出价
    private Integer minCpm;
}
