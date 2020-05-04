package com.li.missyou.service;

import com.li.missyou.model.User;
import com.li.missyou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {

    User getUserById(Long uid);
}
