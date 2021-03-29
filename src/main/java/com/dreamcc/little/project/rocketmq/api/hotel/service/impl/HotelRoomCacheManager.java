package com.dreamcc.little.project.rocketmq.api.hotel.service.impl;

import com.alibaba.fastjson.JSON;
import com.dreamcc.little.project.rocketmq.api.hotel.dto.HotelRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cloud-cc
 * @ClassName HotelRoomCacheManager
 * @Description TODO
 * @date 2021/3/23 10:07
 * @Version 1.0
 */
@Slf4j
@Component
public class HotelRoomCacheManager {

    /**
     * 酒店房间jvm缓存 TODO 防止oom可以通过google Guava Cache改造
     */
    private ConcurrentHashMap<Long, HotelRoom> hotelRoomCacheMap = new ConcurrentHashMap<>();


    /**
     * 根据房间id获取房间jvm内存信息
     *
     * @param roomId 房间id
     * @return 结果
     */
    public HotelRoom getHotelRoomFromLocalCache(Long roomId) {
        return hotelRoomCacheMap.get(roomId);
    }

    /**
     * 更新本地缓存
     *
     * @param hotelRoom 酒店房间数据
     */
    public void updateLocalCache(HotelRoom hotelRoom) {
        hotelRoomCacheMap.put(hotelRoom.getId(), hotelRoom);
        log.info("hotel room local cache data:{}", JSON.toJSONString(hotelRoomCacheMap));
    }

}
