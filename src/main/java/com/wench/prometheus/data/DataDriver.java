package com.wench.prometheus.data;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class DataDriver implements Driver {

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
            String connection = "jdbc:mysql://localhost:3306/prometheus";
            Connection conn = DriverManager.getConnection(connection, "root", "wenchy");
            return conn;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
