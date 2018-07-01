package com.beauxie.cloud.study.controller;

import com.beauxie.cloud.study.entity.User;
import com.beauxie.cloud.study.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by 11323 on 2018/6/17.
 */
@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            for (GrantedAuthority c :
                    authorities) {
                //打印当前登录用户信息
                LOGGER.info("当前用户是{},角色是{}", user.getUsername(), c.getAuthority());
            }
        } else {
            //do other thing
        }
        User findOne = this.userRepository.findOne(id);
        return findOne;
    }
  /*  @GetMapping("/{id}")
    public String test(@PathVariable String id){
        return id;
    }*/
}
