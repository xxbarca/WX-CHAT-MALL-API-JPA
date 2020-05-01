package com.li.missyou.repository;

import com.li.missyou.model.User;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

//    Optional<User> findByOpenid(String openId);
    User findByOpenid(String openId);

    User findFirstById(Long id);

    User findByUnifyUid(Long uuid);

}
