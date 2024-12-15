package com.delivery_service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void test() {
        int i = 3;
        int j = 4;

        Assertions.assertThat(i).isNotEqualTo(j);
    }

}