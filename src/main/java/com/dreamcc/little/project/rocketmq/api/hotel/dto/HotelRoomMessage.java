package com.dreamcc.little.project.rocketmq.api.hotel.dto;

import lombok.Data;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:酒店房间更新消息
 **/
@Data
public class HotelRoomMessage {

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 手机号
     */
    private String phoneNumber;
}