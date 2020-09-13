package com.baron.ad.controller;

import com.alibaba.fastjson.JSON;
import com.baron.ad.entity.AdPlan;
import com.baron.ad.exception.AdException;
import com.baron.ad.service.IAdPlanService;
import com.baron.ad.vo.AdPlanGetRequest;
import com.baron.ad.vo.AdPlanRequest;
import com.baron.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 @package com.baron.ad.controller
 @author Baron
 @create 2020-08-30-3:54 PM
 */
@RestController
@Slf4j
public class AdPlanOPController {

    private final IAdPlanService iAdPlanService;

    @Autowired
    public AdPlanOPController(IAdPlanService iAdPlanService) {
        this.iAdPlanService = iAdPlanService;
    }

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest adPlanRequest) throws AdException{
        log.info("ad-sponsor: createAdPlan -> {}", JSON.toJSONString(adPlanRequest));
        return iAdPlanService.createAdPlan(adPlanRequest);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException{
        log.info("ad-sponsor: getAdPlanByIds ->{}", JSON.toJSONString(request));
        return iAdPlanService.getAdPlanByIds(request);
    }

        @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest adPlanRequest) throws AdException{
        log.info("ad-sponsor: updateAdPlan ->{}", JSON.toJSONString(adPlanRequest));
        return iAdPlanService.updateAdPlan(adPlanRequest);
    }

    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest adPlanRequest) throws  AdException{
        log.info("ad-sponsor: deleteAdPlan ->{}", JSON.toJSONString(adPlanRequest));
        iAdPlanService.deleteAdPlan(adPlanRequest);
    }
}
