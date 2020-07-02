package com.li.missyou.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.missyou.exception.http.ParameterException;
import com.li.missyou.model.User;
import com.li.missyou.repository.UserRepository;
import com.li.missyou.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WxAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Value("${wx.code2session}")
    private String code2session;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.appsecret}")
    private String appsecret;

    /**
     * 1. code换取用户openid
     * 2. user id -> uid
     * 3. 注册 -> openid 写入 user / 查询user uid
     * 4. -> uid
     * 5. uid写入jwt令牌
     * 6. jwt -> 小程序
     * */
    public String code2Session(String code) {
        // 调用微信服务器的url
        String url = MessageFormat.format(this.code2session, this.appid, this.appsecret, code);
        RestTemplate restTemplate = new RestTemplate();
        String sessionText = restTemplate.getForObject(url, String.class);
        Map<String, Object> session = new HashMap<>();
        try {
            session = mapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return this.registerUser(session);
    }

    private String registerUser(Map<String, Object> session) {
        String openid = (String) session.get("openid");
        if (openid == null) {
            throw new ParameterException(20004);
        }
//        Optional<User> userOptional = userRepository.findByOpenid(openid);
//        log.info("data = {}", userOptional.get());
//
//        if (userOptional.isPresent()) {
//            // 如果存在
//            // TODO: 返回JWT令牌
//            return JwtToken.makeToken(userOptional.get().getId());
//        }
//        // 如果不存在 -> 注册
//        // 新增一条user记录
//        User user = User.builder().openid(openid).build();
//        userRepository.save(user);
//        // TODO: 返回JWT
//        Long uid = user.getId();
//        return JwtToken.makeToken(uid);

        User user = userRepository.findByOpenid(openid);
        if (user != null) {
            return JwtToken.makeToken(user.getId());
        } else {
            User user1 = User.builder().openid(openid).build();
            userRepository.save(user1);
            Long uid = user1.getId();
            return JwtToken.makeToken(uid);
        }


    }
}
