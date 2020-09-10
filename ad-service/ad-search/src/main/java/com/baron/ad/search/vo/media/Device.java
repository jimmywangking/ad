package com.baron.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 @package com.baron.ad.search.vo.media
 @author Baron
 @create 2020-09-10-7:04 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    private String deviceCode;

    private String mac;

    private String ip;

    private String model;

    private String displaySize;

    private String screenSize;

    private String serialName;
}
