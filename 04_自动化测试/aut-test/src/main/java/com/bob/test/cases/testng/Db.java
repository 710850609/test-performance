package com.bob.test.cases.testng;

import com.alibaba.fastjson.JSON;
import com.apifan.common.random.source.NumberSource;
import com.apifan.common.random.source.PersonInfoSource;
import com.bob.test.core.Tests;
import com.bob.test.dao.model.User;
import com.bob.test.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest
public class Db extends AbstractTestNGSpringContextTests {

    @Autowired
    private DbService dbService;

    @Test(description = "保存", priority = 0)
    public void insert() {
        User user = new User();
        user.setPassword(NumberSource.getInstance().randomInt(100000, 999999)+"");
        user.setLoginName(PersonInfoSource.getInstance().randomChineseName());
        dbService.insert(user);
        Tests.logger(this).info("保存用户：{}", JSON.toJSONString(user));
        Tests.attributes().set("id", user.getId());
    }

    @Test(description = "根据id更新", priority = 1)
    public void updateById() {
        Long id = Tests.attributes().get("id", Long.class);
        Assert.assertTrue(id != null, "当前运行变量中用户id为空");
        User user = new User();
        user.setId(id);
        user.setLoginName(PersonInfoSource.getInstance().randomEnglishName());
        dbService.updateById(user);
        Tests.logger(this).info("更新【{}】用户登录名为：{}", id, user.getLoginName());
    }

    @Test(description = "根据id查询", priority = 2)
    public void selectById() {
        Long id = Tests.attributes().get("id", Long.class);
        Assert.assertTrue(id != null, "当前运行变量中用户id为空");
        User user = dbService.selectById(id);
        Tests.logger(this).info("查询【{}】用户信息:{}", id, JSON.toJSONString(user));
    }

}
