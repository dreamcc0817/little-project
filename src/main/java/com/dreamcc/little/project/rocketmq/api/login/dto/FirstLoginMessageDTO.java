package com.dreamcc.little.project.rocketmq.api.login.dto;

import lombok.Data;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:第一次登陆的消息dto
 **/
@Data
public class FirstLoginMessageDTO {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String nickName;

    /**
     * 小程序id
     */
    private Integer beid;

    /**
     * 用户手机号
     */
    private String phoneNumber;

}