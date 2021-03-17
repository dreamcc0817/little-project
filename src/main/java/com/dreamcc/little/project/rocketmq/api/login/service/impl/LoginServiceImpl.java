package com.dreamcc.little.project.rocketmq.api.login.service.impl;

import com.alibaba.fastjson.JSON;
import com.dreamcc.little.project.rocketmq.api.login.dto.LoginRequestDTO;
import com.dreamcc.little.project.rocketmq.api.login.enums.FirstLoginStatusEnum;
import com.dreamcc.little.project.rocketmq.api.login.service.LoginService;
import com.ruyuan.little.project.common.dto.CommonResponse;
import com.ruyuan.little.project.common.enums.ErrorCodeEnum;
import com.ruyuan.little.project.common.enums.LittleProjectTypeEnum;
import com.ruyuan.little.project.mysql.api.MysqlApi;
import com.ruyuan.little.project.mysql.dto.MysqlRequestDTO;
import com.ruyuan.little.project.redis.api.RedisApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author cloud-cc
 * @ClassName LoginServiceImpl
 * @Description 登陆接口service组件实现类
 * @date 2021/3/17 11:34
 * @Version 1.0
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private final DefaultMQProducer loginMqProducer;

    @Value("${rocketmq.login.topic}")
    private String loginTopic;

    public LoginServiceImpl(@Qualifier(value = "loginMqProducer") DefaultMQProducer loginMqProducer) {
        this.loginMqProducer = loginMqProducer;
    }

    /**
     * mysql dubbo api接口
     */
    @Reference(version = "1.0.0",
            interfaceClass = MysqlApi.class,
            cluster = "failfast")
    private MysqlApi mysqlApi;

    /**
     * redis dubbo服务
     */
    @Reference(version = "1.0.0",
            interfaceClass = RedisApi.class,
            cluster = "failfast")
    private RedisApi redisApi;

    /**
     * 第一次登陆分发优惠券
     *
     * @param loginRequestDTO 登陆信息
     */
    @Override
    public void firstLoginDistributeCoupon(LoginRequestDTO loginRequestDTO) {
        //不是第一次登录
        if(!isFirstLogin(loginRequestDTO)){
            log.info("userId:{} not first login", loginRequestDTO.getUserId());
            return;
        }
        //更新第一次登录的标识位
        this.updateFirstLoginStatus(loginRequestDTO.getPhoneNumber(), FirstLoginStatusEnum.NO);
        //发送第一次登录成功的消息
        this.sendFirstLoginMessage(loginRequestDTO);
    }


    /**
     * 发送首次登录消息
     *
     * @param loginRequestDTO 登录请求信息
     */
    private void sendFirstLoginMessage(LoginRequestDTO loginRequestDTO) {
        Message message = new Message();
        message.setTopic(loginTopic);
        message.setBody(JSON.toJSONString(loginRequestDTO).getBytes(StandardCharsets.UTF_8));
        try {
            log.info("start send login success notify message");
            SendResult sendResult = loginMqProducer.send(message);
            log.info("end send login success notify message, sendResult:{}", JSON.toJSONString(sendResult));
        } catch (Exception e) {
            log.error("send login success notify message fail, error message:{}", e);
        }
    }

    private void updateFirstLoginStatus(String phoneNumber, FirstLoginStatusEnum firstLoginStatusEnum) {
        MysqlRequestDTO mysqlRequestDTO = new MysqlRequestDTO();
        mysqlRequestDTO.setSql("update t_member set first_login_status = ? WHERE beid = 1563 and mobile = ?");
        ArrayList<Object> params = new ArrayList<>();
        params.add(firstLoginStatusEnum.getStatus());
        params.add(phoneNumber);
        mysqlRequestDTO.setParams(params);
        mysqlRequestDTO.setPhoneNumber(phoneNumber);
        mysqlRequestDTO.setProjectTypeEnum(LittleProjectTypeEnum.ROCKETMQ);

        log.info("start query first login status param:{}", JSON.toJSONString(mysqlRequestDTO));
        CommonResponse<Integer> response = mysqlApi.update(mysqlRequestDTO);
        log.info("end query first login status param:{}, response:{}", JSON.toJSONString(mysqlRequestDTO), JSON.toJSONString(response));
    }

    /**
     * 检验是否第一次登录
     *
     * @param loginRequestDTO 登录信息
     */
    private boolean isFirstLogin(LoginRequestDTO loginRequestDTO) {
        MysqlRequestDTO mysqlRequestDTO = new MysqlRequestDTO();
        mysqlRequestDTO.setSql("select first_login_status from t_member where id = ? ");
        ArrayList<Object> params = new ArrayList<>();
        params.add(loginRequestDTO.getUserId());
        mysqlRequestDTO.setParams(params);
        mysqlRequestDTO.setPhoneNumber(loginRequestDTO.getPhoneNumber());
        mysqlRequestDTO.setProjectTypeEnum(LittleProjectTypeEnum.ROCKETMQ);

        log.info("start query first login status param:{}", JSON.toJSONString(mysqlRequestDTO));

        CommonResponse<List<Map<String, Object>>> response = mysqlApi.query(mysqlRequestDTO);

        log.info("end query first login status param:{}, response:{}", JSON.toJSONString(mysqlRequestDTO), JSON.toJSONString(response));
        if (Objects.equals(response.getCode(), ErrorCodeEnum.SUCCESS.getCode())
                && !CollectionUtils.isEmpty(response.getData())) {
            Map<String, Object> map = response.getData().get(0);
            return Objects.equals(Integer.valueOf(String.valueOf(map.get("first_login_status"))),
                    FirstLoginStatusEnum.YES.getStatus());
        }
        return false;
    }

    /**
     * 重置用户的登录状态
     *
     * @param phoneNumber 手机号
     */
    @Override
    public void resetFirstLoginStatus(String phoneNumber) {
        this.updateFirstLoginStatus(phoneNumber, FirstLoginStatusEnum.YES);
    }
}
