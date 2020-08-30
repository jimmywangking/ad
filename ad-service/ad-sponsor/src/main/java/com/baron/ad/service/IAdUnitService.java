package com.baron.ad.service;

import com.baron.ad.exception.AdException;
import com.baron.ad.vo.*;

/***
 @package com.baron.ad.service
 @author Baron
 @create 2020-08-30-1:43 PM
 */
public interface IAdUnitService {

    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    AdUnitItResponse createUnitIt(AdUnitItRequest adUnitItRequest) throws AdException;

    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest adUnitDistrictRequest) throws AdException;


    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest creativeUnitRequest) throws AdException;
}
