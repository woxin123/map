package com.map.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author mengchen
 * @time 19-3-15 下午4:43
 */
public class JedisUtil {
    private static JedisPool jedisPool;

    static {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        // 控制一个pool最多可以有多少idle的jedis实例 默认值为8
        config.setMaxIdle(200);
        // 最大连接数
        config.setMaxTotal(30);

        // 每次释放连接的最大数目
        config.setNumTestsPerEvictionRun(1024);
        jedisPool = new JedisPool(config, "localhost");
    }

    public static Jedis createJedis() {
        return jedisPool.getResource();
    }
}
