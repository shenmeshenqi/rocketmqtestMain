package com.zzh.sto.rocketmqtest.rocketmq.consume;

import com.zzh.sto.rocketmqtest.rocketmq.constant.Config;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class SequentialMessageConsume {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("name_group");
        consumer.setNamesrvAddr(Config.namesrvAddr);

        //下面如果不设置将默认采用
        consumer.setMessageModel(MessageModel.CLUSTERING);//设置负载均衡模式 消费者采用负载均衡方式消费消息，多个消费者共同消费队列消息，每个消费者处理的消息不同
        //consumer.setMessageModel(MessageModel.BROADCASTING); //设置广播模式  消费者采用广播的方式消费消息，每个消费者消费的消息都是相同的
        consumer.subscribe("topic1","tag1");


        //这个设置 能设置一次消费消息的数量
        // msgs msgs.size() >= 1<br> DefaultMQPushConsumer.consumeMessageBatchMaxSize=1,you can modify here
        //consumer.setConsumeMessageBatchMaxSize(3);
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
               /* System.out.printf("%s Receive New Messages: %s %n",
                        Thread.currentThread().getName(), msgs);*/
                //System.out.printf("消息个数%d\n",msgs.size());
                for (MessageExt ext:msgs) {
                    System.out.printf("当前线程:%s,消息id：%s\t消息内容：%s\n",Thread.currentThread().getName(),ext.getMsgId(),new String(ext.getBody()));
                }
                return  ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
        System.out.println("消费消息开始-----");
    }
}
