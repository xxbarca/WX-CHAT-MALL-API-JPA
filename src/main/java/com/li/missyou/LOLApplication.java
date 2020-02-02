package com.li.missyou;

import com.li.missyou.sample.EnableLOLConfiguration;
import com.li.missyou.sample.HeroConfigration;
import com.li.missyou.sample.ISkill;
import com.li.missyou.sample.LOLConfigurationSelector;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//@ComponentScan
//@Import(LOLConfigurationSelector.class)
@EnableLOLConfiguration
public class LOLApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(LOLApplication.class).web(WebApplicationType.NONE).run(args);
        ISkill iSkill = (ISkill) context.getBean("irelia");
        iSkill.r();
    }
}
