package com.baron.ad.advice;

import com.baron.ad.exception.AdException;
import com.baron.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/***
 @package com.baron.ad.advice
 @author Baron
 @create 2020-08-26-8:21 PM
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest rep, AdException ex){

        CommonResponse<String> commonResponse = new CommonResponse<>(-1,"business error");
        commonResponse.setData(ex.getMessage());
        return commonResponse;
    }
}
