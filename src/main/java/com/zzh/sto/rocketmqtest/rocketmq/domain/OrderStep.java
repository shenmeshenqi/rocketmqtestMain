package com.zzh.sto.rocketmqtest.rocketmq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderStep {
    private String orderId;
    private String orderType;
}
