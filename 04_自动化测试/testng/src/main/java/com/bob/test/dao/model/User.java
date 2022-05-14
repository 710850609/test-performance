package com.bob.test.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User {

    private Long id;

    private String loginName;

    private String password;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;
}
