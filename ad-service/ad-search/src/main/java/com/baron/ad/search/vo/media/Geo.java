package com.baron.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 @package com.baron.ad.search.vo.media
 @author Baron
 @create 2020-09-10-7:02 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    //纬度
    private Float latitude;
    //经度
    private Float longitude;

    private String city;
    private String province;
}
