package com.baron.ad.handler;

import com.alibaba.fastjson.JSON;
import com.baron.ad.dump.table.*;
import com.baron.ad.index.DataTable;
import com.baron.ad.index.IndexAware;
import com.baron.ad.index.adplan.AdPlanIndex;
import com.baron.ad.index.adplan.AdPlanObject;
import com.baron.ad.index.adunit.AdUnitIndex;
import com.baron.ad.index.adunit.AdUnitObject;
import com.baron.ad.index.creative.CreativeIndex;
import com.baron.ad.index.creative.CreativeObject;
import com.baron.ad.index.creativeUnit.CreativeUnitIndex;
import com.baron.ad.index.creativeUnit.CreativeUnitObject;
import com.baron.ad.index.district.UnitDistrictIndex;
import com.baron.ad.index.it.UnitItIndex;
import com.baron.ad.index.keyword.UnitKeywordIndex;
import com.baron.ad.mysql.constant.OpType;
import com.baron.ad.utils.CommonUtils;
import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-03-1:46 AM
 */
@Slf4j
public class AdLevelDataHandler {


    public static void handleLevel2(AdPlanTable planTable, OpType type){

        AdPlanObject planObject = new AdPlanObject(
            planTable.getId(), planTable.getUserId(), planTable.getPlanStatus(), planTable.getStartDate(), planTable.getEndDate()
        );
        handleBinLogEvent(DataTable.of(AdPlanIndex.class), planObject.getPlanId(), planObject, type);
    }

    public static void handleLevel2(AdCreativeTable creativeTable,  OpType type){
        CreativeObject creativeObject = new CreativeObject(creativeTable.getAdId(), creativeTable.getName(), creativeTable.getType(),
                creativeTable.getMaterialType(), creativeTable.getHeight(), creativeTable.getWeight(), creativeTable.getSize(),
                creativeTable.getAuditStatus(), creativeTable.getAdUrl());
        handleBinLogEvent(DataTable.of(CreativeIndex.class), creativeObject.getAdId(), creativeObject, type);
    }

    public static void handleLevel3(AdUnitTable unitTable, OpType type) {
        AdPlanObject planObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if (null == planObject) {
            log.error("handleLevel3 found AdPlanObject error: {}", unitTable.getPlanId());
            return;
        }
        AdUnitObject unitObject = new AdUnitObject(unitTable.getUnitID(), unitTable.getUnitStatus(), unitTable.getPositionType(), unitTable.getPlanId(),
                planObject);
        handleBinLogEvent(DataTable.of(AdUnitIndex.class), unitObject.getUnitId(), unitObject, type);
    }

    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType type){
        if (type == OpType.UPDATE){
            log.error("CreativeUnitIndex not support update!");
            return;
        }
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getCreativeId());
        if (null == unitObject || null == creativeObject){
            log.error("AdCreativeUnitTable index error: {}", JSON.toJSONString(creativeUnitTable));
            return;
        }
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(creativeUnitTable.getAdId(), creativeUnitTable.getCreativeId(), creativeUnitTable.getUnitId());
        handleBinLogEvent(DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(creativeUnitObject.getAdId().toString(), creativeUnitObject.getUnitId().toString()),
                creativeUnitObject, type
                );
    }


    public static void handleLevel4(AdUnitDistrictTable districtTable, OpType type){

        if (type == OpType.UPDATE) {
            log.error("District index cannot support update!");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(districtTable.getUnitId());
        if (unitObject == null) {
            log.error("District index error: {}", districtTable.getUnitId());
            return;
        }

        String key = CommonUtils.stringConcat(districtTable.getProvince(), districtTable.getCity());
        Set<Long> value = new HashSet<>(Collections.singleton(districtTable.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitDistrictIndex.class), key, value, type);
    }

    public static void handleLevel4(AdUnitItTable itTable, OpType type){
        if (type == OpType.UPDATE) {
            log.error("it index cannot support update!");
            return;
        }
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(itTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitItTable index error :{}", itTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(Collections.singleton(itTable.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitItIndex.class), itTable.getItTag(), value, type);
    }

    public static void  handleLevel4(AdUnitKeywordTable keywordTable, OpType type){

        if (type == OpType.UPDATE){
            log.error("Keyword index cannot support update!");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(keywordTable.getUnitId());
        if (unitObject == null){
            log.error("AdUnitKeyworkdTable index error :{}", keywordTable.getUnitId());
            return;
        }
        Set<Long> value = new HashSet<>(Collections.singleton(keywordTable.getUnitId()));
        handleBinLogEvent(DataTable.of(UnitKeywordIndex.class), keywordTable.getKeyword(), value, type);
    }
    private static <K, V> void handleBinLogEvent(IndexAware<K, V> index, K key, V value, OpType type){
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}
