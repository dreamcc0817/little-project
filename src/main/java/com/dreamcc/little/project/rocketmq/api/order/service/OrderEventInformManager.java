package com.dreamcc.little.project.rocketmq.api.order.service;


import com.dreamcc.little.project.rocketmq.api.order.dto.OrderInfoDTO;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单事件通知组件
 **/
public interface OrderEventInformManager {

    /**
     * 通知创建订单事件
     *
     * @param orderInfoDTO 订单信息
     */
    void informCreateOrderEvent(OrderInfoDTO orderInfoDTO);

}
