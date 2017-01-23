package com.mss.tpo.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class ConnectionProvider {

    private static ConnectionProvider _instance;
    private DataSource dataSource;
    private Connection connection;

    private ConnectionProvider() {
    }

    public static ConnectionProvider getInstance() {
        if (_instance == null) {
            _instance = new ConnectionProvider();
        }
        return _instance;
    }

    public Connection getConnection() throws ServiceLocatorException {
        try {
            String dsnName = ConfigProperties.getProperty("DB.DSNNAME");
            dataSource = DataServiceLocator.getInstance().getDataSource(dsnName);
            connection = dataSource.getConnection();
        } catch (ServiceLocatorException se) {
            throw new ServiceLocatorException("Exception in Connection Provider");
        } catch (SQLException sqlEx) {
            throw new ServiceLocatorException(sqlEx);
        }
        return connection;
    }
}
