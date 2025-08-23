package com.chengfu.springbootinit.controller;

import com.chengfu.springbootinit.common.BaseResponse;
import com.chengfu.springbootinit.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查
 *
 * @author: Chengfu Shi
 **/
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/")
    public BaseResponse<String> checkHealth(){
        return ResultUtils.success("ok");
    }
}

