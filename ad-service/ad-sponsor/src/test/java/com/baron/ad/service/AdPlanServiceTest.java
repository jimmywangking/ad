package com.baron.ad.service;

import com.alibaba.fastjson.JSON;
import com.baron.ad.Application;
import com.baron.ad.exception.AdException;
import com.baron.ad.vo.AdPlanGetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/***
 @package com.baron.ad.service
 @author Baron
 @create 2020-09-12-9:10 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {

    @Autowired
    private IAdPlanService planService;

    @Test
    public void testGetAdPlan() throws AdException{
        System.out.println(
                JSON.toJSONString(
                planService.getAdPlanByIds(
                        new AdPlanGetRequest(15L, Collections.singletonList(10L))
                )
                )
        );
    }
}
