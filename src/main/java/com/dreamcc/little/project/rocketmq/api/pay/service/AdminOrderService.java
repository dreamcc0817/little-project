package com.dreamcc.little.project.rocketmq.api.pay.service;

import com.ruyuan.little.project.common.dto.CommonResponse;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:后台管理系统订单管理组件
 **/
public interface AdminOrderService {

    /**
     * 确认订单入住
     *
     * @param orderNo     订单号
     * @param phoneNumber 手机号
     * @return 结果
     */
    CommonResponse confirmOrder(String orderNo, String phoneNumber);

}
