package com.dreamcc.little.project.rocketmq.api.hotel.dto;

import lombok.Data;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:房间描述信息
 **/
@Data
public class RoomDescription {

    /**
     * 面积
     */
    private String area;

    /**
     * 宽高
     */
    private String bed;

    /**
     * 早餐的份数
     */
    private Integer breakfast;
}