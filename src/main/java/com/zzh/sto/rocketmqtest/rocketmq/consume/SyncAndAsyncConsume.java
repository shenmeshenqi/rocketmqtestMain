package com.zzh.sto.rocketmqtest.rocketmq.consume;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SyncAndAsyncConsume {
    @Value("rocketmq.namesrvAddr")
    private static String nameSrvAddr="47.100.18.46:9876;47.106.10.130:9876";
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("new_group");
        consumer.setNamesrvAddr(nameSrvAddr);

        //下面如果不设置将默认采用
        consumer.setMessageModel(MessageModel.CLUSTERING);//设置负载均衡模式 消费者采用负载均衡方式消费消息，多个消费者共同消费队列消息，每个消费者处理的消息不同
        //consumer.setMessageModel(MessageModel.BROADCASTING); //设置广播模式  消费者采用广播的方式消费消息，每个消费者消费的消息都是相同的


        consumer.subscribe("topicTran1||topicTran2||topicTran3","*");


        //这个设置 能设置一次消费消息的数量
        // msgs msgs.size() >= 1<br> DefaultMQPushConsumer.consumeMessageBatchMaxSize=1,you can modify here
        //consumer.setConsumeMessageBatchMaxSize(3);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.printf("%s Receive New Messages: %s %n",
                        Thread.currentThread().getName(), list);
                System.out.printf("消息个数%d\n",list.size());
                for (MessageExt ext:list) {
                    System.out.printf("消息id：%s\t消息内容：%s\n",ext.getMsgId(),new String(ext.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("消费消息开始-----");
    }

}
