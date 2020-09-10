package com.baron.ad.index.creative;

import com.baron.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/***
 @package com.baron.ad.index.creative
 @author Baron
 @create 2020-08-31-5:37 PM
 */
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    private static Map<Long, CreativeObject> creativeMap;
    static {
        creativeMap = new ConcurrentHashMap<>();
    }

    public List<CreativeObject> fetch(Collection<Long> adIds){
        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }

        List<CreativeObject> result = new ArrayList<>();
        adIds.forEach(u -> {CreativeObject Object = get(u);
        if (null == Object) {log.error("CreativeObject not found {}", u); return ;}
        result.add(Object);});

        return result;
    }

    @Override
    public CreativeObject get(Long key) {
        log.info("CreativeIndex, start get: {}", creativeMap);
        return creativeMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("CreativeIndex, start add: {}", creativeMap);
        creativeMap.put(key, value);
        log.info("CreativeIndex, end add: {}", creativeMap);

    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("CreativeIndex, start update: {}", creativeMap);
        CreativeObject oldObject = creativeMap.get(key);
        if (null == oldObject){
            creativeMap.put(key, value);
        }else {
            oldObject.update(value);
        }
        log.info("CreativeIndex, end update: {}", creativeMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {

        log.info("CreativeIndex, start delete: {}", creativeMap);
        creativeMap.remove(key);
        log.info("CreativeIndex, end delete: {}", creativeMap);
    }
}
