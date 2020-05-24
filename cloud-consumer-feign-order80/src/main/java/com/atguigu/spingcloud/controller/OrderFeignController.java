package com.atguigu.spingcloud.controller;

import com.atguigu.spingcloud.service.PaymentFeignService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author : YangChen
 * @Description :
 * @Date: Created in 21:18 2020/4/9
 */
@RestController
public class OrderFeignController {
    @Resource
    PaymentFeignService paymentFeignService;
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout(){
        return paymentFeignService.paymentFeignTimeout();
    }
}
