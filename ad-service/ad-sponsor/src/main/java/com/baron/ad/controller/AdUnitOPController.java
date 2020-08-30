package com.baron.ad.controller;

import com.alibaba.fastjson.JSON;
import com.baron.ad.entity.unit_condition.AdUnitKeyword;
import com.baron.ad.exception.AdException;
import com.baron.ad.service.impl.AdUnitServiceImpl;
import com.baron.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/***
 @package com.baron.ad.controller
 @author Baron
 @create 2020-08-30-4:14 PM
 */
@RestController
@Slf4j
public class AdUnitOPController {

    private final AdUnitServiceImpl adUnitService;

    public AdUnitOPController(AdUnitServiceImpl adUnitService) {
        this.adUnitService = adUnitService;
    }

    @PostMapping("/create/adUnit")
    public AdUnitResponse createUnit(@RequestBody AdUnitRequest request) throws AdException{
        log.info("ad-sponsor: createUnit ->{}", JSON.toJSONString(request));
        return  adUnitService.createUnit(request);
    }

    @PostMapping("/create/unitKeyword")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest adUnitKeywordRequest) throws AdException{
        log.info("ad-sponsor: createUnitKeyword ->{}", JSON.toJSONString(adUnitKeywordRequest));
        return adUnitService.createUnitKeyword(adUnitKeywordRequest);
    }

    @PostMapping("/create/unitIt")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest adUnitItRequest) throws AdException{
        log.info("ad-sponsor: createUnitIt ->{}", JSON.toJSONString(adUnitItRequest));
        return adUnitService.createUnitIt(adUnitItRequest);
    }

    @PostMapping("/create/unitDistrict")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException{
        log.info("ad-sponsor: createUnitDistrict -.{}", JSON.toJSONString(request));
        return adUnitService.createUnitDistrict(request);
    }

    @PostMapping("/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException{
        log.info("ad-sponsor: createCreativeUnit ->{}", JSON.toJSONString(request));
        return adUnitService.createCreativeUnit(request);
    }






}
