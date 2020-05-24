package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author : YangChen
 * @Description :
 * @Date: Created in 22:34 2020/4/10
 */
@RestController
@Slf4j
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallBackMethod", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")})
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        //int age = 10/0;
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    @GetMapping(value = "/consumer/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentCircuitBreaker(id);
    }
    public String paymentTimeOutFallBackMethod(@PathVariable("id") Integer id) {
        return "我是消费者80，对方支付系统繁忙，请稍后再试，o(╥﹏╥)o";
    }

}
