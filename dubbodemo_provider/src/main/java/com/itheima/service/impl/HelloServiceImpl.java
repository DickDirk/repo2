package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.service.HelloService;

/**
 * @program: xc-edu01
 * @description
 * @author: liumengke
 * @create: 2019-08-30 15:06
 **/
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
