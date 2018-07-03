package com.beauxie.cloud.study.service;

import com.beauxie.cloud.study.entity.User;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * UserFeignClient的fallbackFactory类，该类需事先FallbackFactory接口，并覆写create方法
 *
 * @author Beauxie
 * @date Created on 2018/7/3
 */
@Component
public class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public User findById(@PathVariable("id") Long id) {
                //日志最好放在各个fallback方法中，而不要直接放在create方法中
                // 否则在应用时，就会打印该日志
                //可以根据不同的异常返回不同的结果
                LOGGER.info("fallback;reason was:", throwable);
                User user = new User();
                user.setId(-1L);
                user.setName("默认用户");
                return user;
            }
        };
    }
}
