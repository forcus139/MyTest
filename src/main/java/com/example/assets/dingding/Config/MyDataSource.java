package com.example.assets.dingding.Config;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/28 14:32
 **/
/*
 * 自定义数据库连接池
 * */
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class MyDataSource implements DataSource {
    //    1、准备容器  注意必须是线程安全的
    private static List<Connection> pool= Collections.synchronizedList(new ArrayList<>());
    //    2、通过工具类获取10个连接对象
    static {
        for (int i = 0; i < 10; i++) {
            Connection con= JDBCUtils.getConnect();
            pool.add(con);
        }
    }
//    3、重写getConnection方法

    @Override
    public Connection getConnection() throws SQLException {
        if (pool.size()>0){
            return pool.remove(0);
        }else {
            throw new RuntimeException("连接池已空");
        }
    }
    //    4、定义getSize方法获取连接池大小
    public int getSize(){
        return pool.size();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}

