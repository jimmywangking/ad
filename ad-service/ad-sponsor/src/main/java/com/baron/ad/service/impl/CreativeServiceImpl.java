package com.baron.ad.service.impl;

import com.baron.ad.dao.CreativeRepository;
import com.baron.ad.entity.Creative;
import com.baron.ad.service.ICreativeService;
import com.baron.ad.vo.CreativeRequest;
import com.baron.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 @package com.baron.ad.service.impl
 @author Baron
 @create 2020-08-30-3:11 PM
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest creativeRequest) {

        Creative creative = creativeRepository.save(creativeRequest.convertToEntity());
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
