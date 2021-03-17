package com.dreamcc.little.project.rocketmq.api.login.enums;

/**
 * @author cloud-cc
 * @ClassName FirstLoginStatusEnum
 * @Description 第一次登陆状态枚举
 * @date 2021/3/17 13:46
 * @Version 1.0
 */
public enum FirstLoginStatusEnum {
    YES(1,"未登录过"),
    NO(2,"已登录过");

    private Integer status;
    private String desc;

    FirstLoginStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
