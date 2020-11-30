package com.gszcn.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/11/27 下午2:56
 */
@SpringBootTest
public class Test1 {
    @Test
    public void test11(){
//        String name = "abner chai";
        String name = null;
        assert (name!=null):"变量name为空null";
        System.out.println(name);
    }
}
