package com.baron.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 @package com.baron.ad.search.vo.media
 @author Baron
 @create 2020-09-10-7:00 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

    private String appCode;

    private String appName;

    private String packageName;

    private String activityName;

}
