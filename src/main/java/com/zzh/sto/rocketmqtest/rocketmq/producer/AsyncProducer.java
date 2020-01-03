package com.zzh.sto.rocketmqtest.rocketmq.producer;

import com.zzh.sto.rocketmqtest.rocketmq.constant.Config;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 异步消息 demo
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group");
        producer.setNamesrvAddr(Config.namesrvAddr);
        producer.setRetryTimesWhenSendAsyncFailed(0);
        producer.start();

        for (int i = 0; i <10 ; i++) {
            Message message = new Message("topic1","tag1",("子恒有点帅啊"+i).getBytes());
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("成为一个异常");
                }
            });
            System.out.printf("这是第%d次循环",i);

            TimeUnit.SECONDS.sleep(2);
        }

        producer.shutdown();
    }
}
