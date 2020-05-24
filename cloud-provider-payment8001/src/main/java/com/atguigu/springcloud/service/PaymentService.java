package com.atguigu.springcloud.service;

/**
 * @Author : YangChen
 * @Description :
 * @Date: Created in 16:23 2020/4/1
 */

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;
public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}

