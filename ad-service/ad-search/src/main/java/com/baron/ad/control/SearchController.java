package com.baron.ad.control;

import com.alibaba.fastjson.JSON;
import com.baron.ad.annotation.IgnoreResponseAdvice;
import com.baron.ad.client.SponsorClient;
import com.baron.ad.client.vo.AdPlan;
import com.baron.ad.client.vo.AdPlanGetRequest;
import com.baron.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-30-6:14 PM
 */
@RestController
@Slf4j
public class SearchController {
    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(RestTemplate restTemplate, @Qualifier("eureka-client-ad-sponsor") SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlansByFeign(@RequestBody AdPlanGetRequest request){
        log.info("ad-search: getAdPlansByFeign ->{}", JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRobbin")
    public CommonResponse<List<AdPlan>> getAdPlansByRobbin(@RequestBody AdPlanGetRequest request){
        log.info("ad-search: getAdPlansByRobbin ->{}", JSON.toJSONString(request));
        return restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan", request, CommonResponse.class
        ).getBody();
    }
}
