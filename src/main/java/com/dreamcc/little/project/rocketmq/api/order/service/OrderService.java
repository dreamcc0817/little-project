package com.dreamcc.little.project.rocketmq.api.order.service;

import com.dreamcc.little.project.rocketmq.api.order.dto.CreateOrderResponseDTO;
import com.dreamcc.little.project.rocketmq.api.order.dto.OrderInfoDTO;
import com.ruyuan.little.project.common.dto.CommonResponse;


/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单service组件
 **/
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderInfoDTO 订单信息
     * @return 结果
     */
    CommonResponse<CreateOrderResponseDTO> createOrder(OrderInfoDTO orderInfoDTO);

    /**
     * 取消订单
     *
     * @param orderNo 订单ID
     * @param phoneNumber 订单手机号
     * @return
     */
    CommonResponse cancelOrder(String orderNo,String phoneNumber);

    /**
     * 支付订单
     *
     * @param orderNo     订单号
     * @param phoneNumber 用户手机号
     * @return 结果 订单id
     */
    Integer informPayOrderSuccessed(String orderNo, String phoneNumber);


    /**
     * 入住
     *
     * @param orderNo     订单号
     * @param phoneNumber 手机号
     */
    void informConfirmOrder(String orderNo, String phoneNumber);
}
