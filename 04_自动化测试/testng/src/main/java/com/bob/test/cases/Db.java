package com.bob.test.cases;

import com.alibaba.fastjson.JSON;
import com.apifan.common.random.source.NumberSource;
import com.apifan.common.random.source.PersonInfoSource;
import com.bob.test.core.TestNg;
import com.bob.test.dao.model.User;
import com.bob.test.service.DbService;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@CustomLog
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
        log.info("保存用户：{}", JSON.toJSONString(user));
        TestNg.attributes().set("id", user.getId());
    }

    @Test(description = "根据id更新", priority = 1)
    public void updateById() {
        Long id = TestNg.attributes().get("id", Long.class);
        Assert.assertTrue(id != null, "当前运行变量中用户id为空");
        User user = new User();
        user.setId(id);
        user.setLoginName(PersonInfoSource.getInstance().randomEnglishName());
        dbService.updateById(user);
        log.info("更新【{}】用户登录名为：{}", id, user.getLoginName());
    }

    @Test(description = "根据id查询", priority = 2)
    public void selectById() {
        Long id = TestNg.attributes().get("id", Long.class);
        Assert.assertTrue(id != null, "当前运行变量中用户id为空");
        User user = dbService.selectById(id);
        log.info("查询【{}】用户信息:{}", id, JSON.toJSONString(user));
    }




}
