package com.beauxie.cloud.study.service;

import com.beauxie.cloud.study.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiepuyao
 * @date Created on 2018/7/3
 */
@Component
public class FeignClientFallback implements UserFeignClient {

    @Override
    public User findById(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(-1L);
        user.setName("默认用户");
        return user;
    }
}
