package com.zzh.sto.rocketmqtest.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 同步消息
 */
@Component
public class SyncProducer {
    @Value("${rocketmq.namesrvAddr}")
    private static String namesrvAddr="47.100.18.46:9876;47.106.10.130:9876";
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("name_group");
        producer.setNamesrvAddr(namesrvAddr);

        producer.start();

        for (int i = 0; i <10 ; i++) {
            Message message = new Message("topic1","tag1",("子恒你真帅"+i).getBytes());
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
            TimeUnit.SECONDS.sleep(2);
        }

        producer.shutdown();
    }
}
