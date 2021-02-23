package com.doman;

import java.io.Serializable;
import java.util.Date;

public class Parent implements Serializable {
    private static final long serialVersionID = 1L;


    private Integer id;

    private String bindcode;

    private String educode;

    private Integer parentid;

    private String tel;

    private String name;

    private String openid;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBindcode() {
        return bindcode;
    }

    public void setBindcode(String bindcode) {
        this.bindcode = bindcode == null ? null : bindcode.trim();
    }

    public String getEducode() {
        return educode;
    }

    public void setEducode(String educode) {
        this.educode = educode == null ? null : educode.trim();
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}