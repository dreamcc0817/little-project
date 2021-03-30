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

}
