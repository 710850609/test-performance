package com.bob.test.util;

import com.bob.test.dao.UserMapper;
import com.bob.test.dao.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JunitDb {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }
}
