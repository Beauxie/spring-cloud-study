package com.beauxie.cloud.study.controller;

import com.beauxie.cloud.study.entity.User;
import com.beauxie.cloud.study.service.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 11323 on 2018/6/17.
 */
@RestController
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
        User findOne = this.userFeignClient.findById(id);
        return findOne;
    }
}
