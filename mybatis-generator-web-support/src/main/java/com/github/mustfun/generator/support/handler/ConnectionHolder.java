package com.github.mustfun.generator.support.handler;

import org.springframework.util.DigestUtils;

import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/16
 * @since 1.0
 */
public class ConnectionHolder {

    private static ConcurrentHashMap<String,Connection> connectionMap=new ConcurrentHashMap<>(4);


    public static void addConnection(String key,Connection connection){
        String digest = DigestUtils.md5DigestAsHex(key.getBytes());
        connectionMap.put(digest, connection);
    }

    public  static Connection getConnection(String key){
        return connectionMap.get(DigestUtils.md5DigestAsHex(key.getBytes()));
    }

}
