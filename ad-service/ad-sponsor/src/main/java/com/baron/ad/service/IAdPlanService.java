package com.baron.ad.service;

import com.baron.ad.entity.AdPlan;
import com.baron.ad.exception.AdException;
import com.baron.ad.vo.AdPlanGetRequest;
import com.baron.ad.vo.AdPlanRequest;
import com.baron.ad.vo.AdPlanResponse;

import java.util.List;

/***
 @package com.baron.ad.service
 @author Baron
 @create 2020-08-30-11:57 AM
 */
public interface IAdPlanService {

    /**
     * <h2>创建推广计划</h2>
     * @param adPlanRequest
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest adPlanRequest) throws AdException;

    /**
     * <h2>获取推广计划</h2>
     * @param adPlanGetRequest
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest adPlanGetRequest) throws AdException;

    /**
     * <h2>更新推广计划</h2>
     * @param adPlanRequest
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest adPlanRequest) throws  AdException;

    /**
     * <h2>删除推广计划</h2>
     * @param adPlanRequest
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest adPlanRequest) throws AdException;
}
