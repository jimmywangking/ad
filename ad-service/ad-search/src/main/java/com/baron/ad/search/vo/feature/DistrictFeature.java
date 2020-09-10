package com.baron.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 @package com.baron.ad.search.vo.feature
 @author Baron
 @create 2020-09-10-7:09 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictFeature{

    private List<ProvinceAndCity> districts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProvinceAndCity{
        private String province;
        private String city;

    }

}
