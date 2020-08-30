package com.baron.ad.service.impl;

import com.baron.ad.constants.CommonStatus;
import com.baron.ad.constants.Constants;
import com.baron.ad.dao.AdPlanRepository;
import com.baron.ad.dao.AdUserRepository;
import com.baron.ad.entity.AdPlan;
import com.baron.ad.entity.AdUser;
import com.baron.ad.exception.AdException;
import com.baron.ad.service.IAdPlanService;
import com.baron.ad.utils.CommonUtils;
import com.baron.ad.vo.AdPlanGetRequest;
import com.baron.ad.vo.AdPlanRequest;
import com.baron.ad.vo.AdPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/***
 @package com.baron.ad.service.impl
 @author Baron
 @create 2020-08-30-12:13 PM
 */
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    @Autowired
    private AdUserRepository adUserRepository;
    @Autowired
    private AdPlanRepository adPlanRepository;

    /**
     * <h2>创建推广计划</h2>
     *
     * @param adPlanRequest
     * @return
     * @throws AdException
     */
    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest adPlanRequest) throws AdException {

        if (!adPlanRequest.createValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdUser> adUser = adUserRepository.findById(adPlanRequest.getUserId());
        if (!adUser.isPresent()){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan oldAdPlan = adPlanRepository.findByUserIdAndPlanName(
                adPlanRequest.getUserId(), adPlanRequest.getPlanName()
        );

        if (oldAdPlan != null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        AdPlan newAdPlan = adPlanRepository.save(
                new AdPlan(adPlanRequest.getUserId(), adPlanRequest.getPlanName(),
                        CommonUtils.parseStringDate(adPlanRequest.getStartDate()),
                        CommonUtils.parseStringDate(adPlanRequest.getEndDate()))
        );
        return new AdPlanResponse(newAdPlan.getId(),newAdPlan.getPlanName());
    }

    /**
     * <h2>获取推广计划</h2>
     *
     * @param adPlanGetRequest
     * @return
     * @throws AdException
     */
    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest adPlanGetRequest) throws AdException {

        if(!adPlanGetRequest.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return adPlanRepository.findAllByIdInAndUserId(adPlanGetRequest.getIds(),adPlanGetRequest.getUserId());
    }

    /**
     * <h2>更新推广计划</h2>
     *
     * @param adPlanRequest
     * @return
     * @throws AdException
     */
    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest adPlanRequest) throws AdException {

        if(!adPlanRequest.updateValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan adPlan = adPlanRepository.findByIdAndUserId(adPlanRequest.getId(), adPlanRequest.getUserId());
        if (adPlan == null){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        if(adPlanRequest.getPlanName() != null){
            adPlan.setPlanName(adPlanRequest.getPlanName());
        }
        if(adPlanRequest.getStartDate() != null){
            adPlan.setStartDate(CommonUtils.parseStringDate(adPlanRequest.getStartDate()));
        }
        if(adPlanRequest.getEndDate() != null){
            adPlan.setEndDate(CommonUtils.parseStringDate(adPlanRequest.getEndDate()));
        }

        adPlan.setUpdateTime(new Date());
        adPlan = adPlanRepository.save(adPlan);

        return new AdPlanResponse(adPlan.getId(),adPlan.getPlanName());
    }

    /**
     * <h2>删除推广计划</h2>
     *
     * @param adPlanRequest
     * @throws AdException
     */
    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest adPlanRequest) throws AdException {

        if(!adPlanRequest.deleteValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan adPlan = adPlanRepository.findByIdAndUserId(adPlanRequest.getId(), adPlanRequest.getUserId());
        if(adPlan == null){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        adPlan.setPlanStatus(CommonStatus.INVALID.getStatus());
        adPlan.setUpdateTime(new Date());
        adPlanRepository.save(adPlan);
    }
}
