package com.zzh.sto.rocketmqtest.rocketmq.constant;

import com.zzh.sto.rocketmqtest.rocketmq.domain.OrderStep;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static String namesrvAddr="47.100.18.46:9876;47.106.10.130:9876";

    /**
     * 模拟一个订单的数据流程
     * @return
     */
    public static List<OrderStep> getOrderStepList(){
        List<OrderStep> list = new ArrayList<>();
        String oderIdList[] ={"2001L","2002L","2003L"};
        for (String oderId:oderIdList) {
            String oderTypeList[] ={"创建","付款","完成"};
            for (String oderType:oderTypeList) {
                list.add(new OrderStep(oderId,oderType));
            }
        }
        return list;
    }
}
