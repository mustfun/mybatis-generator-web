package com.github.mustfun.generator.support.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author dengzhiyuan
 * @version 1.0
 * @date 2018/4/13
 * @since 1.0
 */
public class DbUtil {
    private  String url;
    private  String username;
    private  String password;

    public DbUtil(String url,String dbName,String userName,String password){
        this.url = "jdbc:mysql://"+url+":3306/"+dbName+"?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
        this.username = userName;
        this.password = password;
    }


    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
