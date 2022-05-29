package com.bob.test.cases.junit;

import com.alibaba.fastjson.JSON;
import com.apifan.common.random.source.NumberSource;
import com.apifan.common.random.source.PersonInfoSource;
import com.bob.test.core.Tests;
import com.bob.test.dao.model.User;
import com.bob.test.service.DbService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("数据库操作")
public class Db {

    @Autowired
    private DbService dbService;

    @Test
    @DisplayName("保存")
    public void insert() {
        User user = new User();
        user.setPassword(NumberSource.getInstance().randomInt(100000, 999999)+"");
        user.setLoginName(PersonInfoSource.getInstance().randomChineseName());
        dbService.insert(user);
        Tests.logger(this).info("保存用户：{}", JSON.toJSONString(user));
        Tests.attributes().set("id", user.getId());
    }

    @Test
    @DisplayName("根据id更新")
    public void updateById() {
        Long id = Tests.attributes().get("id", Long.class);
        Assertions.assertTrue(id != null, "当前运行变量中用户id为空");
        User user = new User();
        user.setId(id);
        user.setLoginName(PersonInfoSource.getInstance().randomEnglishName());
        dbService.updateById(user);
        Tests.logger(this).info("更新【{}】用户登录名为：{}", id, user.getLoginName());
    }

    @Test
    @DisplayName("根据id查询")
    public void selectById() {
        Long id = Tests.attributes().get("id", Long.class);
        Assertions.assertTrue(id != null, "当前运行变量中用户id为空");
        User user = dbService.selectById(id);
        Tests.logger(this).info("查询【{}】用户信息:{}", id, JSON.toJSONString(user));
    }

}
