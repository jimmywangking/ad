package com.baron.ad.service;

import com.alibaba.fastjson.JSON;
import com.baron.ad.Application;
import com.baron.ad.constants.CommonStatus;
import com.baron.ad.dao.AdPlanRepository;
import com.baron.ad.dao.AdUnitRepository;
import com.baron.ad.dao.CreativeRepository;
import com.baron.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.baron.ad.dao.unit_condition.AdUnitItRepository;
import com.baron.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.baron.ad.dao.unit_condition.CreativeUnitRepository;
import com.baron.ad.dump.DConstant;
import com.baron.ad.dump.table.*;
import com.baron.ad.entity.AdPlan;
import com.baron.ad.entity.AdUnit;
import com.baron.ad.entity.Creative;
import com.baron.ad.entity.unit_condition.AdUnitDistrict;
import com.baron.ad.entity.unit_condition.AdUnitIt;
import com.baron.ad.entity.unit_condition.AdUnitKeyword;
import com.baron.ad.entity.unit_condition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-02-11:40 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {
    @Autowired
    private AdPlanRepository adPlanRepository;
    @Autowired
    private AdUnitRepository adUnitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitKeywordRepository keywordRepository;
    @Autowired
    private AdUnitItRepository itRepository;
    @Autowired
    private AdUnitDistrictRepository districtRepository;

    @Test
    public void DumpAdTableData(){
        DumpAdPlanTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        DumpAdUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        DumpAdUnitKeywordTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD));
        DumpAdUnitItTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT));
        DumpAdUnitDistrictTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
        DumpCreativeTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        DumpCreativeUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
    }
//    @Test
    private void DumpAdPlanTable(String fileName){
        List<AdPlan> adPlanList= adPlanRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adPlanList)){
            return ;
        }
        List<AdPlanTable> planTables = new ArrayList<>();
        adPlanList.forEach(p -> planTables.add(new AdPlanTable(p.getId(), p.getUserId(), p.getPlanStatus(), p.getStartDate(), p.getEndDate())));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdPlanTable planTable : planTables){
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex){
            log.error("DumpDataService: DumpAdPlanTable error!");
        }
    }

//    @Test
    private void DumpAdUnitTable(String fileName){
        List<AdUnit> adUnits = adUnitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }
        List<AdUnitTable> unitTables = new ArrayList<>();
        adUnits.forEach(u -> unitTables.add(new AdUnitTable(u.getId(), u.getUnitStatus(), u.getPositionType(), u.getPlanId())));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex){
            log.error("DumpDataService: DumpUnitTable error!");
        }
    }

//    @Test
    private void DumpCreativeTable(String fileName) {
        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)){return;}
        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(c -> creativeTables.add(new AdCreativeTable(c.getId(), c.getName(), c.getType(), c.getMaterialType(),
                c.getHeight(), c.getWeight(), c.getSize(), c.getAuditStatus(), c.getUrl())));
        Path path = Paths.get(fileName);
        try {
            BufferedWriter writer = Files.newBufferedWriter(path);
                for (AdCreativeTable creativeTable : creativeTables){
                    writer.write(JSON.toJSONString(creativeTable));
                    writer.newLine();
                }
                writer.close();
        } catch (IOException ex){
            log.error("DumpCreativeTable error!");
        }
    }

//    @Test
    private void DumpAdUnitKeywordTable(String fineName) {
        List<AdUnitKeyword> keywords = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(keywords)){return;}
        List<AdUnitKeywordTable> keywordTables = new ArrayList<>();
        keywords.forEach(k -> keywordTables.add(new AdUnitKeywordTable(k.getUnitId(), k.getKeyword())));

        Path path = Paths.get(fineName);
        try {
            BufferedWriter writer = Files.newBufferedWriter(path);
            for (AdUnitKeywordTable keywordTable : keywordTables){
                writer.write(JSON.toJSONString(keywordTable));
                writer.newLine();
            }
            writer.close();
        }catch (IOException ex){
            log.error("DumpKeywordTable error!");
        }
    }

//    @Test
    private void DumpCreativeUnitTable(String fileName) {
        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)){return;}
        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c -> creativeUnitTables.add(new AdCreativeUnitTable(c.getId(), c.getCreativeId(), c.getUnitId())));
        Path path = Paths.get(fileName);
        try {
            BufferedWriter writer = Files.newBufferedWriter(path);
            for (AdCreativeUnitTable creativeUnitTable : creativeUnitTables){
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
            writer.close();
        }catch (IOException ex){
            log.error("DumpCreativeUnitTables error!");
        }
    }

//    @Test
    private void DumpAdUnitItTable(String fileName) {
        List<AdUnitIt> unitIts = itRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)){return;}
        List<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(i -> unitItTables.add(new AdUnitItTable(i.getUnitId(), i.getItTag())));

        Path path = Paths.get(fileName);
        try {
            BufferedWriter writer = Files.newBufferedWriter(path);
            for (AdUnitItTable itTable : unitItTables){
                writer.write(JSON.toJSONString(itTable));
                writer.newLine();
            }
            writer.close();
        }catch (IOException ex){
            log.error("DumpAdUnitItTables error!");
        }
    }

//    @Test
    private void DumpAdUnitDistrictTable(String fileName) {
        List<AdUnitDistrict> districts = districtRepository.findAll();
        if (CollectionUtils.isEmpty(districts)){return;}
        List<AdUnitDistrictTable> districtTables = new ArrayList<>();
        districts.forEach(d -> districtTables.add(new AdUnitDistrictTable(d.getUnitId(), d.getProvince(), d.getCity())));
        Path path = Paths.get(fileName);
        try {
            BufferedWriter writer = Files.newBufferedWriter(path);
            for (AdUnitDistrictTable districtTable : districtTables){
                writer.write(JSON.toJSONString(districtTable));
                writer.newLine();
            }
            writer.close();
        }catch (IOException ex){
            log.error("DumpAdUnitDistrictTables error!");
        }
    }
}
