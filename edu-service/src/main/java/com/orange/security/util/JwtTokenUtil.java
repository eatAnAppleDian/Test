package com.orange.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.orange.security.entity.TokenPayLoad;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

/**
 * @author orange
 */
public class JwtTokenUtil {
    /**
     * 使用RSA算法生成token
     */
    public static String generateTokenByRSA(String userName) throws JOSEException {
        //创建JWS头，设置签名算法和类型
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();

        //将负载信息封装到Payload中
        TokenPayLoad token = getDefaultPayload(userName);
        Payload payload = new Payload(JSONUtil.toJsonStr(token));

        //创建JWS对象
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        //创建RSA签名器，密钥至少是2048位
        RSAKey rsaKey = getDefaultRSAKey();
        JWSSigner jwsSigner = new RSASSASigner(rsaKey);

        //签名
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    /**
     * 使用RSA算法校验token
     */
    public static TokenPayLoad verifyTokenByRSA(String token) throws ParseException, JOSEException {
        //从token中解析JWS对象
        JWSObject jwsObject = JWSObject.parse(token);

        //使用RSA公钥创建RSA验证器
        RSAKey rsaKey = getDefaultRSAKey();
        RSAKey publicRsaKey = rsaKey.toPublicJWK();
        JWSVerifier jwsVerifier = new RSASSAVerifier(publicRsaKey);
        if (!jwsObject.verify(jwsVerifier)) {
            throw new BadCredentialsException("TOKEN签名不合法，请重新登录！");
        }

        String payload = jwsObject.getPayload().toString();
        TokenPayLoad tokenPayLoad = JSONUtil.toBean(payload, TokenPayLoad.class);
        // 判断超期
        if (tokenPayLoad.getExp() < System.currentTimeMillis()) {
            throw new BadCredentialsException("TOKEN已过期，请重新登录！");
        }

        return tokenPayLoad;
    }

    /**
     * 获取payload
     */
    private static TokenPayLoad getDefaultPayload(String userName) {
        // 当前时间
        Date now = new Date();

        // 超期时间 1个小时
        Date exp = DateUtil.offsetSecond(now, 60 * 60);

        return TokenPayLoad.builder()
                // 用户名
                .sub(userName)
                // 签发时间
                .iat(now.getTime())
                // 超期时间
                .exp(exp.getTime())
                //jwt的唯一身份标识
                .jti(UUID.randomUUID().toString())
                .build();
    }

    /**
     * 获取默认的RSAKey
     */
    private static RSAKey getDefaultRSAKey() {
        // 从classpath下读取证书文件edu.jdk，需要给出加密密钥
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("edu.jks"), "edu123456".toCharArray());
        //使用别名edu来获取RSA秘钥对
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("edu", "edu123456".toCharArray());

        //获取RSA公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //获取RSA私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        return new RSAKey.Builder(publicKey).privateKey(privateKey).build();
    }
}