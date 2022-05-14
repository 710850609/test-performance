package com.bob.test.service;

import com.bob.test.dao.UserMapper;
import com.bob.test.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbService {

    @Autowired
    private UserMapper userMapper;

    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

    public void updateById(User user) {
        userMapper.updateById(user);
    }

    public void insert(User user) {
        userMapper.insert(user);
    }
}
