package com.baron.ad.service;

import com.baron.ad.exception.AdException;
import com.baron.ad.vo.CreateUserRequest;
import com.baron.ad.vo.CreateUserResponse;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-29-4:58 PM
 */
public interface IUserService {
    /**
     * <h2>创建用户</h2>
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
