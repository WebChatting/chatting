package com.sxrekord.chatting.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Rekord
 * @date 2022/9/13 12:20
 */
@SpringBootTest
public class DruidTest {

    @Autowired
    DruidDataSource dataSource;

    @Test
    public void druidTest() throws SQLException, SQLException {
        //看一下默认数据源
        System.out.println(dataSource.getClass());
        //获得连接
        Connection connection =   dataSource.getConnection();
        System.out.println(connection);

        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
        System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());
        System.out.println("druidDataSource 数据源最大超时时间：" + druidDataSource.getMaxWait());

        //关闭连接
        connection.close();
    }
}
