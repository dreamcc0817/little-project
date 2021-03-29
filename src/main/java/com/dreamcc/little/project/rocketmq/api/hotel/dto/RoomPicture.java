package com.dreamcc.little.project.rocketmq.api.hotel.dto;

import lombok.Data;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:房间图片信息
 **/
@Data
public class RoomPicture {

    /**
     * 图片id
     */
    private Integer id;

    /**
     * 图片地址
     */
    private String url;

    private String src;
}