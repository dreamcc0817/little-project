package com.dreamcc.little.project.rocketmq.api.coupon.consumer;


import com.dreamcc.little.project.rocketmq.api.coupon.listener.FirstLoginMessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:优惠券模块消费者配置类组件
 **/
@Configuration
public class CouponConsumerConfiguration {

    /**
     * namesrv address
     */
    @Value("${rocketmq.namesrv.address}")
    private String namesrvAddress;

    /**
     * 登录topic
     */
    @Value("${rocketmq.login.topic}")
    private String loginTopic;

    /**
     * 登录消息consumerGroup
     */
    @Value("${rocketmq.login.consumer.group}")
    private String loginConsumerGroup;

    /**
     * 登录消息的consumer bean
     *
     * @return 登录消息的consumer bean
     */
    @Bean(value = "loginConsumer")
    public DefaultMQPushConsumer loginConsumer(@Qualifier(value = "firstLoginMessageListener") FirstLoginMessageListener firstLoginMessageListener) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(loginConsumerGroup);
        consumer.setNamesrvAddr(namesrvAddress);
        consumer.subscribe(loginTopic, "*");
        consumer.setMessageListener(firstLoginMessageListener);
        consumer.start();
        return consumer;
    }

}