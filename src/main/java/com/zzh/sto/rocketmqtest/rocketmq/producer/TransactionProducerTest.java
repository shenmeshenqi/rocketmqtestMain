package com.zzh.sto.rocketmqtest.rocketmq.producer;

import com.zzh.sto.rocketmqtest.rocketmq.constant.Config;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 发送事物消息
 */
public class TransactionProducerTest {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        TransactionMQProducer producer = new TransactionMQProducer("new_group");
        MyTransactionListener myTransactionListener = new MyTransactionListener();
        producer.setTransactionListener(myTransactionListener);
        producer.setNamesrvAddr(Config.namesrvAddr);
        String [] topicList = {"topicTran1","topicTran2","topicTran3"};
        producer.start();
        for (String topic :topicList) {
            Message message = new Message(topic,"tag1",("子恒你真帅"+topic).getBytes());
            SendResult sendResult = producer.sendMessageInTransaction(message,null);
            System.out.println(sendResult);
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
