package com.li.missyou.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTest {

    @Test
    public void test1() {
        Optional<String> empty = Optional.empty();
        Optional<String> t1 = Optional.of("liyang");
        Optional<String> t2 = Optional.ofNullable(null);
    }
}
