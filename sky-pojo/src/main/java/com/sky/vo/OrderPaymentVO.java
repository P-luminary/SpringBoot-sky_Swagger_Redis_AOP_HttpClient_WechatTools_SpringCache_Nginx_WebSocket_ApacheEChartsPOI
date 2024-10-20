package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentVO implements Serializable {

    private String orderNumber; // 订单号
    private String paymentStatus; // 支付状态，如 "SUCCESS"
    private Date paymentTime; // 支付时间
}
