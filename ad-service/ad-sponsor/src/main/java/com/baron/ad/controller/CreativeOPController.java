package com.baron.ad.controller;

import com.alibaba.fastjson.JSON;
import com.baron.ad.exception.AdException;
import com.baron.ad.service.ICreativeService;
import com.baron.ad.vo.CreativeRequest;
import com.baron.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/***
 @package com.baron.ad.controller
 @author Baron
 @create 2020-08-30-4:30 PM
 */
@RestController
@Slf4j
public class CreativeOPController {
    private final ICreativeService iCreativeService;

    public CreativeOPController(ICreativeService iCreativeService) {
        this.iCreativeService = iCreativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException{
        log.info("ad-sponse: createCreative ->{}", JSON.toJSONString(request));
        return iCreativeService.createCreative(request);
    }
}
