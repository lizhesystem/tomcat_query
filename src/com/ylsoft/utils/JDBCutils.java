package com.ylsoft.utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCutils {

    private static DataSource ds;

    static {
        Properties pro = new Properties();
        try {
            pro.load(JDBCutils.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 获取连接池对象
    public static DataSource getDataSource(){
        return ds;
    }

    // 获取数据库连接对象
    public static Connection getconnect() throws SQLException {
        return ds.getConnection();
    }
}
