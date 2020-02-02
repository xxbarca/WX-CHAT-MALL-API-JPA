package com.li.missyou.sample.hero;

import com.li.missyou.sample.ISkill;
import org.springframework.stereotype.Component;

public class Irelia implements ISkill {

    public Irelia() {
        System.out.println("Hello Irealia");
    }

    public void q() {
        System.out.println("Irealia Q");
    }

    public void w() {
        System.out.println("Irealia w");
    }

    public void e() {
        System.out.println("Irealia e");
    }

    public void r() {
        System.out.println("Irealia r");
    }
}
