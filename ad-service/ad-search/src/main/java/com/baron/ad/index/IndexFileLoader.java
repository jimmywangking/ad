package com.baron.ad.index;

import com.alibaba.fastjson.JSON;
import com.baron.ad.constant.OpType;
import com.baron.ad.dump.DConstant;
import com.baron.ad.dump.table.*;
import com.baron.ad.exception.AdException;
import com.baron.ad.handler.AdLevelDataHandler;
import com.baron.ad.constant.OpType;
import com.sun.javafx.binding.StringFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/***
 @package com.baron.ad.index
 @author Baron
 @create 2020-09-05-2:47 PM
 */
@Component
@DependsOn("dataTable")
@Slf4j
public class IndexFileLoader {

    @PostConstruct
    public void init(){
        List<String> adPlans = LoadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        adPlans.forEach(p -> AdLevelDataHandler.handleLevel2(JSON.parseObject(p, AdPlanTable.class), OpType.ADD));

        List<String> adCreatives = LoadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        adCreatives.forEach(c -> AdLevelDataHandler.handleLevel2(JSON.parseObject(c, AdCreativeTable.class), OpType.ADD));

        List<String> adUnits = LoadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        adUnits.forEach(u -> AdLevelDataHandler.handleLevel3(JSON.parseObject(u, AdUnitTable.class), OpType.ADD));

        List<String> adCreativeUnits = LoadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        adCreativeUnits.forEach(cu -> AdLevelDataHandler.handleLevel3(JSON.parseObject(cu, AdCreativeUnitTable.class), OpType.ADD));

        List<String> unitDistricts = LoadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
        unitDistricts.forEach(d -> AdLevelDataHandler.handleLevel4(JSON.parseObject(d, AdUnitDistrictTable.class), OpType.ADD));

        List<String> unitIts = LoadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT));
        unitIts.forEach(i -> AdLevelDataHandler.handleLevel4(JSON.parseObject(i, AdUnitItTable.class), OpType.ADD));

        List<String> unitKeywords = LoadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD));
        unitKeywords.forEach(k -> AdLevelDataHandler.handleLevel4(JSON.parseObject(k, AdUnitKeywordTable.class), OpType.ADD));
    }


    private List<String> LoadDumpData(String fileName){
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            return reader.lines().collect(Collectors.toList());
        }catch (IOException ioException){
            log.error("IndexFileLoader, LoadDumpData ioException! {}", ioException.getMessage());
//            throw new AdException(ioException.getMessage());
            return null;
        }
    }
}
