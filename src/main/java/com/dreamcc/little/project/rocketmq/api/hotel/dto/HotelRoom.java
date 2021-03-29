package com.dreamcc.little.project.rocketmq.api.hotel.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:酒店房间详情dto
 **/
@Data
public class HotelRoom {

    /**
     * 房间id
     */
    private Long id;

    /**
     * "id":"4009",
     * "title":"豪华客房",
     * "pcate":"1981",
     * "thumb_url":"https://weapp-1303909892.file.myqcloud.com//image/20201221/6e222a7cc34f48db.jpg",
     * <p>
     * 房间名称
     */
    private String title;

    /**
     * 店铺id
     */
    private Long pcate;

    /**
     * 商品图片
     */
    private String thumbUrl;

    /**
     * 房间详细信息
     */
    private RoomDescription roomDescription;

    /**
     * 房间图片信息
     */
    private List<RoomPicture> goods_banner;

    /**
     * 参考价格
     */
    private BigDecimal marketprice;

    /**
     * 实际价格
     */
    private BigDecimal productprice;

    /**
     * 商品的数量
     */
    private Integer total;

    private Integer totalcnf;

    /**
     * 创建时间 unix时间
     */
    private Long createtime;

}