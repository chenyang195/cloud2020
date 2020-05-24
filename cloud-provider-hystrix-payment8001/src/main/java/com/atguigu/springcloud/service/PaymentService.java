package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author : YangChen
 * @Description :
 * @Date: Created in 23:09 2020/4/9
 */
@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "   paymentInfo_OK,id：" + id + "\t" + "O(∩_∩)O哈哈~";
    }
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")

    })
    public String paymentInfo_Timeout(Integer id) {
        int timeNumber = 3;
        //int age = 10/0;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "   paymentInfo_Timeout,id：" + id + "\t" + "O(∩_∩)O哈哈~" + "   耗时(秒)：" ;
    }
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED,value = "true"),
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,value = "10"),
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS,value = "10000"),
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE,value = "60")
    })
    public String paymentCircuitBreaker(Integer id){
        if (id<0){
            throw new RuntimeException("++++++++++++++id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+serialNumber;
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "   系统繁忙，请稍后再试,id：" + id + "\t" + "o(╥﹏╥)o";
    }


}
