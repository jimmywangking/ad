package com.baron.ad.index.keyword;

import com.baron.ad.index.IndexAware;
import com.baron.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/***
 @package com.baron.ad.index.keyword
 @author Baron
 @create 2020-08-31-9:54 AM
 */
@Slf4j
@Component
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> keywordUnitMap;
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }
    @Override
    public Set<Long> get(String key) {
        log.info("UnitKeywordIndex: before get -> {}", keywordUnitMap);
        if (StringUtils.isEmpty(key)){
            return Collections.emptySet();
        }
        Set<Long> set = keywordUnitMap.get(key);
        if (null == set){
            return Collections.emptySet();
        }
        log.info("UnitKeywordIndex: after get -> {}", keywordUnitMap);

        return set;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitKeywordIndex: before add -> {}", keywordUnitMap);
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);

        for(Long unitId : value){
            Set<String> keywordSet = CommonUtils.getOrCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.add(key);
        }

        log.info("UnitKeywordIndex: after add -> {}", unitKeywordMap);
    }

    @Override
    public void update(String key, Set<Long> value) {

        log.error("unitKeywordIndex cannot support update!");
    }

    @Override
    public void delete(String key, Set<Long> value) {

        log.info("UnitKeywordIndex: before delete -> {}", unitKeywordMap);

        Set<Long> unitIds = CommonUtils.getOrCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for(Long unitId : value){
            Set<String> keywordSet = CommonUtils.getOrCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.remove(key);
        }

        log.info("UnitKeywordIndex: after delete -> {}", unitKeywordMap);
    }

    public boolean match(Long unitId, List<String> keywords){
        if (unitKeywordMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))){
            Set<String> unitKeywords = unitKeywordMap.get(unitId);
            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }
        return false;
    }
}
