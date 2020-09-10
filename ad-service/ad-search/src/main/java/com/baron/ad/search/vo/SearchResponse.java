package com.baron.ad.search.vo;

import com.baron.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 @package com.baron.ad.search.vo
 @author Baron
 @create 2020-09-10-7:14 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {
        private Long adId;
        private String adUrl;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        private List<String> showMonitorUrl  = Arrays.asList("www.baron.com","www.ibm.com");
        private List<String> clickMonitorUrl = Arrays.asList("www.baron.com","www.ibm.com");
    }


    public static Creative convert(CreativeObject object) {
        Creative creative =  new Creative();
        creative.setAdId(object.getAdId());
        creative.setAdUrl(object.getAdUrl());
        creative.setWidth(object.getWeight());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());

        return creative;
    }
}
