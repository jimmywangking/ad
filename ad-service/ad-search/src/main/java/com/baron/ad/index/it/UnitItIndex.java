package com.baron.ad.index.it;

import com.baron.ad.index.IndexAware;
import com.baron.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/***
 @package com.baron.ad.index.it
 @author Baron
 @create 2020-08-31-1:44 PM
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    //itTag adUnitIt set
    private static Map<String, Set<Long>> itUnitMap;
    //adUnitIt itTag set
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitItIndex, before add:{}", unitItMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        for(Long unitId : value){
            Set<String> its = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            its.add(key);
        }
        log.info("UnitItIndex, after add:{}", unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index cannot support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {

        log.info("UnitItIndex, before delete: {}",unitItMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.remove(value);

        for (Long unitId : value){
            Set<String> itTageSet = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            itTageSet.remove(key);
        }
        log.info("UnitItIndex, after delete: {}", unitItMap);

    }

    public boolean match(Long unitId, List<String> itTags){
        if (unitItMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitItMap.get(unitId))){
            Set<String> unitIts = unitItMap.get(unitId);
            return CollectionUtils.isSubCollection(itTags, unitIts);
        }
        return false;
    }
}


