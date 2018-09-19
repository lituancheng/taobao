/*
 * This file is generated by jOOQ.
*/
package com.leon.taobao.model.taobao.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User implements Serializable {

    private static final long serialVersionUID = -350098493;

    private Integer   id;
    private String    openId;
    private Integer   enable;
    private Timestamp createTime;
    private Timestamp updateTime;

    public User() {}

    public User(User value) {
        this.id = value.id;
        this.openId = value.openId;
        this.enable = value.enable;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public User(
        Integer   id,
        String    openId,
        Integer   enable,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.openId = openId;
        this.enable = enable;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User (");

        sb.append(id);
        sb.append(", ").append(openId);
        sb.append(", ").append(enable);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}