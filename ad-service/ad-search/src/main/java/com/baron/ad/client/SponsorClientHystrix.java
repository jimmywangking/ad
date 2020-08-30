package com.baron.ad.client;

import com.baron.ad.client.vo.AdPlan;
import com.baron.ad.client.vo.AdPlanGetRequest;
import com.baron.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/***
 @package com.baron.ad.client
 @author Baron
 @create 2020-08-30-6:35 PM
 */
@Component
public class SponsorClientHystrix implements SponsorClient{

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
