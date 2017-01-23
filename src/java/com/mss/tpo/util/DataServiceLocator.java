package com.mss.tpo.util;

import com.ibm.db2.jcc.DB2DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

/**
 * This is a Data Service Locator object used to abstract all JNDI usage and to
 * hide the complexities of initial context creation, DataSource lookup.
 * Multiple clients can reuse the Service Locator object to reduce code
 * complexity, provide a single point of control, and improve performance by
 * providing a caching facility.
 * <p>
 * This class reduces the client complexity that results from the client's
 * dependency on and need to perform lookup and creation processes, which are
 * resource-intensive. To eliminate these problems, this pattern provides a
 * mechanism to abstract all dependencies and network details into the Service
 * Locator.
 *
 * <p>
 * Usage: This is a Singleton class, usage is as follows:<br>
 * Use the getInstance method to create an instance of the class.
 *
 * <code>ServiceLocator serviceLocator = ServiceLocator.getInstance();</code>
 */
public class DataServiceLocator {

    private Context context;
    private static DataServiceLocator _instance;

    private DataServiceLocator() throws ServiceLocatorException {
        try {
            context = new InitialContext();
        } catch (Exception ex) {
            throw new ServiceLocatorException(ex);
        }
    }

    /**
     * @return An instance of the DataServiceLocator class
     * @throws ServiceLocatorException
     */
    public static DataServiceLocator getInstance()
            throws ServiceLocatorException {
        try {
            if (_instance == null) {
                _instance = new DataServiceLocator();
            }
        } catch (Exception ex) {
            throw new ServiceLocatorException(ex);
        }
        return _instance;
    }

    public DataSource getDataSource(String dataSourceName)
            throws ServiceLocatorException {
        DataSource dataSource = null;
        try {
            if (CacheManager.getCache().containsKey(dataSourceName)) {
                dataSource = (DB2DataSource) CacheManager.getCache().get(dataSourceName);
            } else {
                DB2DataSource datasource = new DB2DataSource();
                datasource.setServerName(ConfigProperties.getProperty(AppConstants.DB_HOST));
                datasource.setUser(ConfigProperties.getProperty(AppConstants.DB_USER));
                datasource.setPassword(ConfigProperties.getProperty(AppConstants.DB_PWD));
                datasource.setDriverType(Integer.parseInt(ConfigProperties.getProperty(AppConstants.DB_DRIVERTYPE))); //Type 4 pure Java JDBC Driver
                datasource.setPortNumber(Integer.parseInt(ConfigProperties.getProperty(AppConstants.DB_Port)));
                datasource.setDatabaseName(ConfigProperties.getProperty(AppConstants.DB_NAME));

                dataSource = datasource;
                CacheManager.getCache().put(dataSourceName, datasource);
            }
        } catch (Exception ex) {
            throw new ServiceLocatorException("Cannot get Data Source REASON:" + ex.getMessage(), ex);
        }
        return dataSource;
    }
}
