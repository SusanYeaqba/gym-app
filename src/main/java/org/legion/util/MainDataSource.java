package org.legion.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MainDataSource {

    private static HikariConfig config;
    private static HikariDataSource ds;

    static {
        try {
            Properties props = new Properties();
            props.load(new Utilities().loadResource("datasource.properties"));
            config = new HikariConfig(props);
            ds = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private MainDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static synchronized int executeQuery(String query) throws SQLException {
        int res = 0;
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        System.out.println(query);
        res = st.executeUpdate(query);
        st.close();
        conn.close();
        return res;
    }

    public static synchronized BigDecimal executeDecimalResultQuery(String query) throws SQLException {
        BigDecimal res = BigDecimal.ZERO;
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        System.out.println(query);
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            res = rs.getBigDecimal(1);
        }
        rs.close();
        st.close();
        conn.close();
        return res;
    }

    public static HikariDataSource getDs() {
        return ds;
    }

    public static void setDs(HikariDataSource ds) {
        MainDataSource.ds = ds;
    }

}
