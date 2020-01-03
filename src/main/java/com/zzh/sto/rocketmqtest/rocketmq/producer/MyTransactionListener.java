package com.zzh.sto.rocketmqtest.rocketmq.producer;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

public class MyTransactionListener implements TransactionListener {
    //执行本地事物 要等到server那边传回ok才会执行
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        switch (msg.getTopic()){
            case "topicTran1":
                System.out.println("这是topicTran1");
                return LocalTransactionState.COMMIT_MESSAGE;
            case "topicTran2":
                System.out.println("这是topicTran2");
                return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("将处于UNKNOW的消息进行回传检查"+msg);
        //变成提交状态
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
