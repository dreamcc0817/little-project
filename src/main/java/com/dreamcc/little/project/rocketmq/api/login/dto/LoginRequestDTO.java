package com.dreamcc.little.project.rocketmq.api.login.dto;

import lombok.Data;

/**
 * @author cloud-cc
 * @ClassName LoginRequestDTO
 * @Description 登录请求的dto
 * @date 2021/3/17 10:44
 * @Version 1.0
 */
@Data
public class LoginRequestDTO {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * token
     */
    private String token;

    /**
     * 小程序id
     */
    private Integer beid;
}
