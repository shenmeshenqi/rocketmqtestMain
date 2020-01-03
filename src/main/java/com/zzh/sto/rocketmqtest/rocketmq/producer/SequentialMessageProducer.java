package com.zzh.sto.rocketmqtest.rocketmq.producer;

import com.zzh.sto.rocketmqtest.rocketmq.constant.Config;
import com.zzh.sto.rocketmqtest.rocketmq.domain.OrderStep;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 顺序消息生产者
 */
public class SequentialMessageProducer {
    /**
     * 顺序消息: 1. 全局有序  2.局部有序
     * 全局有序:消费者只对一个queue进行消费 保证 全局的有序性
     * 局部有序: 消费者为每一个队列开一个线程，每个线程有序的对消息进行消费
     */

    /**
     * 下面使用局部有序的形式
     * @param args
     * @throws MQClientException
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQBrokerException
     */
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        List<OrderStep> orderStepList = Config.getOrderStepList();
        DefaultMQProducer producer = new DefaultMQProducer("group");
        producer.setNamesrvAddr(Config.namesrvAddr);
        producer.start();
        
        for (OrderStep orderStep:orderStepList) {
            Message message = new Message("topic1","tag1",orderStep.toString().getBytes());
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    String orderId = (String) arg;
                    int index = orderId.hashCode() % mqs.size();
                    return mqs.get(index);
                }
            }, orderStep.getOrderId());
            System.out.println(sendResult);
            TimeUnit.SECONDS.sleep(1);
        }

        producer.shutdown();
    }
}
