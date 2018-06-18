package com.beauxie.cloud.study.controller;

import com.beauxie.cloud.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by 11323 on 2018/6/17.
 */
@RestController
public class UserController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.userServiceUrl}")
    private String userServiceUrl;

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        User findOne = this.restTemplate.getForObject(userServiceUrl + id, User.class);
        return findOne;
    }

    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo() {
        return this.discoveryClient.getInstances("microservice-provider-user");
//        return this.discoveryClient.getInstances("MICROSERVICE-PROVIDER-USER");
    }
}
