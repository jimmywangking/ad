package com.baron.ad.service.impl;

import com.baron.ad.constants.Constants;
import com.baron.ad.dao.AdUserRepository;
import com.baron.ad.entity.AdUser;
import com.baron.ad.exception.AdException;
import com.baron.ad.service.IUserService;
import com.baron.ad.utils.CommonUtils;
import com.baron.ad.vo.CreateUserRequest;
import com.baron.ad.vo.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;

/***
 @package com.baron.ad.service.impl
 @author Baron
 @create 2020-08-30-11:35 AM
 */
public class UserServiceImpl implements IUserService {

    private final AdUserRepository adUserRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository adUserRepository) {
        this.adUserRepository = adUserRepository;
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if(!request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = adUserRepository.findByUsername(request.getUserName());
        if(oldUser != null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = adUserRepository.save(new AdUser(request.getUserName(), CommonUtils.md5(request.getUserName())));
        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getUsername(), newUser.getCreateTime(),newUser.getUpdateTime());
    }
}
