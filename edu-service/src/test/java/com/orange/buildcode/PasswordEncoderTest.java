package com.orange.buildcode;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // 密码
        String password = "123456";

        // 注意：每次加密时使用的盐值不同，生成的加密值也会不同。
        String hashPasswrod = delegatingPasswordEncoder.encode(password);
        System.out.println(hashPasswrod);

        // 注意：解密时使用当前比对的加密值中的盐值，可以成功的触发。
        // boolean f = delegatingPasswordEncoder.matches("123456", hashPasswrod);
        // System.out.println(f);
    }
}