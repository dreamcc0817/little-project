package com.dreamcc.little.project.rocketmq.api.login.service;

import com.dreamcc.little.project.rocketmq.api.login.dto.LoginRequestDTO;

/**
 * @author cloud-cc
 * @ClassName LoginService
 * @Description 登录接口Service组件
 * @date 2021/3/17 11:31
 * @Version 1.0
 */
public interface LoginService {
    /**
     * 第一次登陆分发优惠券
     *
     * @param loginRequestDTO 登陆信息
     */
    void firstLoginDistributeCoupon(LoginRequestDTO loginRequestDTO);

    /**
     * 重置用户的登录状态
     *
     * @param phoneNumber 手机号
     */
    void resetFirstLoginStatus(String phoneNumber);
}
