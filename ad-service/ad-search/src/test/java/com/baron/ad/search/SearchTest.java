package com.baron.ad.search;

import com.alibaba.fastjson.JSON;
import com.baron.ad.Application;
import com.baron.ad.search.vo.SearchRequest;
import com.baron.ad.search.vo.feature.DistrictFeature;
import com.baron.ad.search.vo.feature.FeatureRelation;
import com.baron.ad.search.vo.feature.ItFeature;
import com.baron.ad.search.vo.feature.KeywordFeature;
import com.baron.ad.search.vo.media.AdSlot;
import com.baron.ad.search.vo.media.App;
import com.baron.ad.search.vo.media.Device;
import com.baron.ad.search.vo.media.Geo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-12-9:24 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds() {
        SearchRequest request = new SearchRequest();
        request.setMediaId("baron-ad");

        //第一个测试条件
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "abd", Collections.singletonList(new AdSlot("ad-x",1,1080,720, Arrays.asList(1,2),1000)),
                buildExampleApp(),buildExampleGeo(),buildExampleDevice()
        ));
        request.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("宝马", "大众"), Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省","合肥市")),
                Arrays.asList("台球","游泳"), FeatureRelation.OR
        ));

        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(search.fetchAds(request)));


        //第一个测试条件
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "abd", Collections.singletonList(new AdSlot("ad-y",1,1080,720, Arrays.asList(1,2),1000)),
                buildExampleApp(),buildExampleGeo(),buildExampleDevice()
        ));
        request.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("宝马", "大众", "标志"), Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省","合肥市")),
                Arrays.asList("台球","游泳"), FeatureRelation.OR
        ));

        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(search.fetchAds(request)));
    }

    private App buildExampleApp() {
        return new App("baron", "baron", "com.baron", "picture");
    }

    private Geo buildExampleGeo() {
        return new Geo((float) 100.28, (float) 88.61, "临江市","临江市");
    }

    private Device buildExampleDevice() {
        return new Device("iphone", "Oxxxxx", "127.0.0.1", "x", "1280 720", "1280 720", "1234567890");
    }

    private SearchRequest.FeatureInfo buildExampleFeatureInfo(List<String> keywords, List<DistrictFeature.ProvinceAndCity> provinceAndCityList,
                                                              List<String> its, FeatureRelation relation) {
        return new SearchRequest.FeatureInfo(new KeywordFeature(keywords), new DistrictFeature(provinceAndCityList),
                new ItFeature(its), relation);
    }
}
