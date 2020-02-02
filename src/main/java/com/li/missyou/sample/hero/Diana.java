package com.li.missyou.sample.hero;

import com.li.missyou.sample.ISkill;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

public class Diana implements ISkill {
    private String skillName = "Diana R";
    private String name;
    private Integer age;

    public Diana(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Diana() {
        System.out.println("Hello Diana");
    }

    public void q() {
        System.out.println("Diana Q");
    }

    public void w() {
        System.out.println("Diana w");
    }

    public void e() {
        System.out.println("Diana e");
    }

    public void r() {
        System.out.println(this.skillName);
    }

}
