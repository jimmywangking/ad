package com.baron.ad.index.creativeUnit;

import com.baron.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/***
 @package com.baron.ad.index.creativeUnit
 @author Baron
 @create 2020-08-31-5:50 PM
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    //adId-unitId, object
    private static Map<String, CreativeUnitObject> creativeUnitObjectMap;
    //adId, unitId set
    private static Map<Long, Set<Long>> creativeUnitMap;
    //unitId, adId set
    private static Map<Long, Set<Long>> unitCreativeMap;
    static{
        creativeUnitMap = new ConcurrentHashMap<>();
        creativeUnitObjectMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }
    @Override
    public CreativeUnitObject get(String key) {
        return creativeUnitObjectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex, start add:{}", creativeUnitObjectMap);
        creativeUnitObjectMap.put(key, value);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isEmpty(unitSet)){
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(), unitSet);
        }
        unitSet.add(value.getUnitId());

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeSet)){
            creativeSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), creativeSet);
        }
        creativeSet.add(value.getAdId());
        log.info("CreativeUnitIndex, end add:{}", creativeUnitObjectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {

        log.error("CreativeUnitIndex cannot support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex, start delete:{}", creativeUnitObjectMap);
        creativeUnitObjectMap.remove(key);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isNotEmpty(unitSet)){
            unitSet.remove(value.getUnitId());
        }
        unitSet.remove(value.getUnitId());

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeSet)){
            creativeSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.remove(value.getUnitId());
        }
        creativeSet.remove(value.getAdId());
        log.info("CreativeUnitIndex, end delete:{}", creativeUnitObjectMap);

    }
}
