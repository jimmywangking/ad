package com.baron.ad.service;

import com.baron.ad.vo.CreativeRequest;
import com.baron.ad.vo.CreativeResponse;

/***
 @package com.baron.ad.service
 @author Baron
 @create 2020-08-30-3:00 PM
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest creativeRequest);

}
