package com.li.missyou.sample;

import com.li.missyou.sample.condition.DianaCondition;
import com.li.missyou.sample.condition.IreliaCondition;
import com.li.missyou.sample.hero.Diana;
import com.li.missyou.sample.hero.Irelia;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeroConfigration {

//    @Bean
//    @ConditionalOnProperty(value = "hero.condition", havingValue = "diana", matchIfMissing = true)
//    @Conditional(DianaCondition.class)
//    public ISkill diana() {
//        return new Diana("diana", 30);
//    }

    @Bean
    public ISkill irelia() {
        return new Irelia();
    }
}
