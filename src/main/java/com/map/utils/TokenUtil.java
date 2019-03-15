package com.map.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.map.pojo.User;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mengchen
 * @time 19-3-15 下午4:07
 */
public class TokenUtil {

    /**
     * 公钥
     */
    private static final String TOKEN_SECRET;

    static {
        TOKEN_SECRET = PropertiesUtil.getProperty("token.secret");
    }

    public static String createToken(User user) throws Exception {
        // 签发时间
        Date iatDate = new Date();

        // 过期时间，7天时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.HOUR, 24 * 7);
        Date experiesDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("type", user.getType())
                .withExpiresAt(experiesDate) // 设置过期的日期
                .withIssuedAt(iatDate) // 签发时间
                .sign(Algorithm.HMAC256(TOKEN_SECRET)); // 加密

        // 存储在redis中
        String userId = String.valueOf(user.getId());
        Jedis jedis = JedisUtil.createJedis();
        jedis.set(userId, token);
        jedis.expire(userId, 7 * 24 * 3600);
        return token;
    }

    /**
     * 解密
     */

    public static Map<String, Claim> verifyToken(String token) throws Exception {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (Exception e) {
            throw new Exception("登录过期");
        }
        return jwt.getClaims();
    }

    public static String getTokenByUserId(int userId) {
        return JedisUtil.createJedis().get(String.valueOf(userId));
    }


}
