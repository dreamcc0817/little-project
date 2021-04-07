package com.dreamcc.little.project.rocketmq.api.order.listener;

import com.alibaba.fastjson.JSON;
import com.dreamcc.little.project.rocketmq.api.order.dto.OrderInfoDTO;
import com.dreamcc.little.project.rocketmq.api.order.service.OrderService;
import com.ruyuan.little.project.common.dto.CommonResponse;
import com.ruyuan.little.project.common.enums.ErrorCodeEnum;
import com.ruyuan.little.project.common.enums.LittleProjectTypeEnum;
import com.ruyuan.little.project.redis.api.RedisApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.dreamcc.little.project.rocketmq.common.constants.RedisKeyConstant.ORDER_LOCK_KEY_PREFIX;

/**
 * @author cloud-cc
 * @ClassName OrderDelayMessageListener
 * @Description TODO
 * @date 2021/3/31 10:01
 * @Version 1.0
 */
@Slf4j
@Component
public class OrderDelayMessageListener implements MessageListenerConcurrently {

    @Autowired
    private OrderService orderService;

    /**
     * redis dubbo服务
     */
    @Reference(version = "1.0.0",
            interfaceClass = RedisApi.class,
            cluster = "failfast")
    private RedisApi redisApi;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

        for (MessageExt msg : msgs) {
            String body = new String(msg.getBody(), StandardCharsets.UTF_8);
            OrderInfoDTO orderInfoDTO = JSON.parseObject(body, OrderInfoDTO.class);
            String orderNo = orderInfoDTO.getOrderNo();
            String phoneNumber = orderInfoDTO.getPhoneNumber();
            log.info("received order delay message orderNo:{}", orderNo);
            try {
                // 获取订单分布式锁防止订单正在支付
                CommonResponse<Boolean> commonResponse = redisApi.lock(ORDER_LOCK_KEY_PREFIX + orderNo,
                        orderNo,
                        10L,
                        TimeUnit.SECONDS,
                        phoneNumber,
                        LittleProjectTypeEnum.ROCKETMQ);
                if (Objects.equals(commonResponse.getCode(), ErrorCodeEnum.SUCCESS.getCode())
                        && Objects.equals(commonResponse.getData(), Boolean.TRUE)) {
                    log.info("acquire order lock success ");
                    // 修改订单状态为取消预约
                    try {
                        orderService.cancelOrder(orderNo, phoneNumber);
                    } catch (Exception e) {
                        log.info("cancel order fail error message:{}", e);
                    }
                }
            } finally {
                // 释放锁
                redisApi.unlock(ORDER_LOCK_KEY_PREFIX + orderNo,
                        orderNo,
                        phoneNumber,
                        LittleProjectTypeEnum.ROCKETMQ);
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
