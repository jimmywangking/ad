package com.baron.ad.controller;

import com.alibaba.fastjson.JSON;
import com.baron.ad.exception.AdException;
import com.baron.ad.service.IUserService;
import com.baron.ad.vo.CreateUserRequest;
import com.baron.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-30-3:46 PM
 */
@RestController
@Slf4j
public class UserOPController {

    private final IUserService iUserService;

    @Autowired
    public UserOPController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException{
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(request));
        return iUserService.createUser(request);
    }
}
