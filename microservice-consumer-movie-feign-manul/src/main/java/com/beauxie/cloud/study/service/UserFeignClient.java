package com.beauxie.cloud.study.service;

import com.beauxie.cloud.study.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户接口服务
 *
 * @author Beauxie
 * @date 2018/6/19.
 */
//@FeignClient(name = "microservice-provider-user")
public interface UserFeignClient {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    User findById(@PathVariable("id") Long id);
}
