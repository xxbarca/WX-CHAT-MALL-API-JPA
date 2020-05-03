package com.li.missyou.service;

import com.li.missyou.model.User;
import com.li.missyou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long uid) {
        return userRepository.findFirstById(uid);
    }
}
