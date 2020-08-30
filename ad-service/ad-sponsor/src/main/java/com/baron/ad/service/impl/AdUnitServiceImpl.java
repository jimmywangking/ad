package com.baron.ad.service.impl;

import com.baron.ad.constants.Constants;
import com.baron.ad.dao.AdPlanRepository;
import com.baron.ad.dao.AdUnitRepository;
import com.baron.ad.dao.CreativeRepository;
import com.baron.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.baron.ad.dao.unit_condition.AdUnitItRepository;
import com.baron.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.baron.ad.dao.unit_condition.CreativeUnitRepository;
import com.baron.ad.entity.AdPlan;
import com.baron.ad.entity.AdUnit;
import com.baron.ad.entity.unit_condition.AdUnitDistrict;
import com.baron.ad.entity.unit_condition.AdUnitIt;
import com.baron.ad.entity.unit_condition.AdUnitKeyword;
import com.baron.ad.entity.unit_condition.CreativeUnit;
import com.baron.ad.exception.AdException;
import com.baron.ad.service.IAdUnitService;
import com.baron.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/***
 @package com.baron.ad.service.impl
 @author Baron
 @create 2020-08-30-1:49 PM
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanRepository adPlanRepository;
    private final AdUnitRepository adUnitRepository;

    private final AdUnitKeywordRepository adUnitKeywordRepository;
    private final AdUnitItRepository adUnitItRepository;
    private final AdUnitDistrictRepository adUnitDistrictRepository;

    private final CreativeUnitRepository creativeUnitRepository;
    private final CreativeRepository creativeRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository adPlanRepository, AdUnitRepository adUnitRepository, AdUnitDistrictRepository adUnitDistrictRepository, AdUnitItRepository adUnitItRepository, AdUnitKeywordRepository adUnitKeywordRepository, CreativeUnitRepository creativeUnitRepository, CreativeRepository creativeRepository) {
        this.adPlanRepository = adPlanRepository;
        this.adUnitRepository = adUnitRepository;
        this.adUnitDistrictRepository = adUnitDistrictRepository;
        this.adUnitItRepository = adUnitItRepository;
        this.adUnitKeywordRepository = adUnitKeywordRepository;
        this.creativeUnitRepository = creativeUnitRepository;
        this.creativeRepository = creativeRepository;
    }

    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {

        if (!request.createValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> adPlan = adPlanRepository.findById(request.getPlanId());
        if (!adPlan.isPresent()){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldAdUnit = adUnitRepository.findByPlanIdAndUnitName(
                request.getPlanId(), request.getUnitName()
        );
        if (oldAdUnit != null){
            throw new AdException(Constants.ErrorMsg.SAVE_NAME_UNIT_ERROR);
        }

        AdUnit newAdUnit = adUnitRepository.save(
                new AdUnit(request.getPlanId(), request.getUnitName(), request.getPositionType(), request.getBudget())
        );

        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }

    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {

        List<Long> ids = request.getUnitKeywordList().stream().map(AdUnitKeywordRequest.UnitKeyword::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(ids)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> unitIds = Collections.emptyList();
        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKeywordList())){
            request.getUnitKeywordList().forEach(i -> unitKeywords.add(new AdUnitKeyword(i.getUnitId(), i.getKeyword())));
            unitIds = adUnitKeywordRepository.saveAll(unitKeywords).stream().map(AdUnitKeyword::getId).collect(Collectors.toList());
        }
        return new AdUnitKeywordResponse(unitIds);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest adUnitItRequest) throws AdException {

        List<Long> unitIds = adUnitItRequest.getUnitItList().stream().map(AdUnitItRequest.UnitIt::getUnitId).collect(Collectors.toList());
        if(!isRelatedUnitExist(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();
        List<AdUnitIt> unitIts = new ArrayList<>();
        adUnitItRequest.getUnitItList().forEach(i -> unitIts.add(new AdUnitIt(i.getUnitId(), i.getItTag())));
        ids = adUnitItRepository.saveAll(unitIts).stream().map(AdUnitIt::getId).collect(Collectors.toList());

        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest adUnitDistrictRequest) throws AdException {
        List<Long> unitIds = adUnitDistrictRequest.getUnitDistrictList().stream().map(AdUnitDistrictRequest.UnitDistrict::getUnitId).collect(Collectors.toList());
        if(!isRelatedUnitExist(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        adUnitDistrictRequest.getUnitDistrictList().forEach(i -> unitDistricts.add(new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())));
        List<Long> ids = adUnitDistrictRepository.saveAll(unitDistricts).stream().map(AdUnitDistrict::getId).collect(Collectors.toList());
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest creativeUnitRequest) throws AdException {
        List<Long> unitIds = creativeUnitRequest.getUnitItems().stream().map(CreativeUnitRequest.CreativeUnitItem::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> creativeIds = creativeUnitRequest.getUnitItems().stream().map(CreativeUnitRequest.CreativeUnitItem::getCreativeId).collect(Collectors.toList());
        if(!isRelatedCreativeExist(creativeIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        creativeUnitRequest.getUnitItems().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(), i.getUnitId())
        ));

        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId).collect(Collectors.toList());

        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> ids){
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }

        return adUnitRepository.findAllById(ids).size() == new HashSet<>(ids).size();
    }

    private boolean isRelatedCreativeExist(List<Long> ids){
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return creativeRepository.findAllById(ids).size() == new HashSet<>(ids).size();
    }
}
