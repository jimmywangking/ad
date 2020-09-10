package com.baron.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.baron.ad.index.CommonStatus;
import com.baron.ad.index.DataTable;
import com.baron.ad.index.adunit.AdUnitIndex;
import com.baron.ad.index.adunit.AdUnitObject;
import com.baron.ad.index.creative.CreativeIndex;
import com.baron.ad.index.creative.CreativeObject;
import com.baron.ad.index.creativeUnit.CreativeUnitIndex;
import com.baron.ad.index.district.UnitDistrictIndex;
import com.baron.ad.index.it.UnitItIndex;
import com.baron.ad.index.keyword.UnitKeywordIndex;
import com.baron.ad.search.ISearch;
import com.baron.ad.search.vo.SearchRequest;
import com.baron.ad.search.vo.SearchResponse;
import com.baron.ad.search.vo.feature.DistrictFeature;
import com.baron.ad.search.vo.feature.FeatureRelation;
import com.baron.ad.search.vo.feature.ItFeature;
import com.baron.ad.search.vo.feature.KeywordFeature;
import com.baron.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/***
 @package com.baron.ad.search
 @author Baron
 @create 2020-09-10-8:01 PM
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {
    @Override
    public SearchResponse fetchAds(SearchRequest request) {

        //广告位信息
        List<AdSlot> adSlots= request.getRequestInfo().getAdSlots();

        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();

        FeatureRelation featureRelation = request.getFeatureInfo().getRelation();

        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {

            Set<Long> targetUnitIdSet;
            //根据流量类型获取初始 AdUnit
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());
            if (featureRelation == FeatureRelation.AND){

                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterItTagFeature(adUnitIdSet, itFeature);
                fileterDistrictFeature(adUnitIdSet, districtFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {
                targetUnitIdSet = getOrRelationAdUnits(adUnitIdSet, keywordFeature, districtFeature, itFeature);

            }
            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);

            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> objects = DataTable.of(CreativeIndex.class).fetch(adIds);

            //通过adslot对CreativeObject过滤
            filterCreativeByAdSlot(objects, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());

            adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(objects));
        }

        log.info("fetchAds: {}-{}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    private Set<Long> getOrRelationAdUnits(Set<Long> adUnitAdset, KeywordFeature keywordFeature, DistrictFeature districtFeature, ItFeature itFeature){
        if (CollectionUtils.isEmpty(adUnitAdset)) {
            return Collections.emptySet();
        } else {
            Set<Long> keywordUnitIdSet = new HashSet<>(adUnitAdset);
            Set<Long> districtUnitIdSet = new HashSet<>(adUnitAdset);
            Set<Long> itUnitIdSet =  new HashSet<>(adUnitAdset);

            filterKeywordFeature(keywordUnitIdSet, keywordFeature);
            filterItTagFeature(itUnitIdSet, itFeature);
            fileterDistrictFeature(districtUnitIdSet, districtFeature);

            return  new HashSet<>(CollectionUtils.union(
                    CollectionUtils.union(keywordUnitIdSet, itUnitIdSet), districtUnitIdSet
            ));
        }
    }
    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)){
            return;
        }

        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            //filter 可以过滤不满足条件的
            CollectionUtils.filter(adUnitIds, adUnitId -> DataTable.of(UnitKeywordIndex.class).match(adUnitId, keywordFeature.getKeywords()));
        }
    }

    private void fileterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)){
            return;
        }

        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            //filter 可以过滤不满足条件的
            CollectionUtils.filter(adUnitIds, adUnitId -> DataTable.of(UnitDistrictIndex.class).match(adUnitId, districtFeature.getDistricts()));
        }
    }

    private void filterItTagFeature(Collection<Long> adUnitIds, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)){

            return;
        }

        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            //filter 可以过滤不满足条件的
            CollectionUtils.filter(adUnitIds, adUnitId -> DataTable.of(UnitItIndex.class).match(adUnitId, itFeature.getIts()));
        }
    }

    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status){

        if (CollectionUtils.isEmpty(unitObjects)){
            return;
        }
        CollectionUtils.filter(unitObjects, object -> object.getUnitStatus().equals(status.getStatus()) &&
                object.getAdPlanObject().getPlanStatus().equals(status.getStatus()));

    }

    private void filterCreativeByAdSlot(List<CreativeObject> creatives, Integer width, Integer height, List<Integer> type) {
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        CollectionUtils.filter(creatives, creative -> creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
        && creative.getWeight().equals(width) && creative.getHeight().equals(height) && type.contains(creative.getType()));
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creatives) {
        if (CollectionUtils.isEmpty(creatives)){
            return Collections.emptyList();
        }
        CreativeObject creative  =  creatives.get(Math.abs(new Random().nextInt() % creatives.size()));

        return Collections.singletonList(SearchResponse.convert(creative));
    }
}
