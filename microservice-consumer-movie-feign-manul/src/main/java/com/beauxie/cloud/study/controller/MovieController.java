package com.beauxie.cloud.study.controller;

import com.beauxie.cloud.study.entity.User;
import com.beauxie.cloud.study.service.UserFeignClient;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Beauxie on 2018/6/17.
 */
@Import(FeignClientsConfiguration.class)
@RestController
public class MovieController {
    private static final String USER_SERVICE_NAME = "http://microservice-provider-user-with-auth/";

    private UserFeignClient userUserFeignClient;
    private UserFeignClient adminUserFeignClient;

    @Autowired
    public MovieController(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        this.userUserFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder)
                .contract(contract).requestInterceptor(new BasicAuthRequestInterceptor("user", "password1")).
                        target(UserFeignClient.class, USER_SERVICE_NAME);
        this.adminUserFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder)
                .contract(contract).requestInterceptor(new BasicAuthRequestInterceptor("admin", "password2")).
                        target(UserFeignClient.class, USER_SERVICE_NAME);
    }


    @GetMapping("/user-user/{id}")
    public User findByIdUser(@PathVariable Long id) {
        User findOne = this.userUserFeignClient.findById(id);
        return findOne;
    }

    @GetMapping("/user-admin/{id}")
    public User findByIdAdmin(@PathVariable Long id) {
        User findOne = this.adminUserFeignClient.findById(id);
        return findOne;
    }
}
