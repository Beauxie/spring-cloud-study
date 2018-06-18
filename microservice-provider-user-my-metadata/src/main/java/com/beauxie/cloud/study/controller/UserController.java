package com.beauxie.cloud.study.controller;

import com.beauxie.cloud.study.entity.User;
import com.beauxie.cloud.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 11323 on 2018/6/17.
 */
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

  @GetMapping("/{id}")
    public User findById(@PathVariable Long id){
        User findOne = this.userRepository.findOne(id);
        return findOne;
    }
  /*  @GetMapping("/{id}")
    public String test(@PathVariable String id){
        return id;
    }*/
}
