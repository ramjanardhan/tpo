/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

public class DataSourceDataProvider {

    private static DataSourceDataProvider _instance;

    private DataSourceDataProvider() {
    }

    public static DataSourceDataProvider getInstance() {

        if (_instance == null) {
            _instance = new DataSourceDataProvider();
        }
        return _instance;
    }

    public String getuserNameByUserId(String userId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String userName = "";
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT NAME FROM MSCVP.TPO_USER WHERE LOGINID ='" + userId + "'");
            while (resultSet.next()) {
                userName = resultSet.getString("NAME");
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return userName;
    }

    public Map getPartnerNamelist() throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        Map PartnerNameMap = new TreeMap();
        try {
            queryString = "SELECT unique(ID),NAME FROM TPO_PARTNERS";
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PartnerNameMap.put(resultSet.getInt("ID"), resultSet.getString("NAME"));
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return PartnerNameMap;
    }

    public Map getAdminUsersList() throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        Map adminUsersMap = new TreeMap();
        try {
            queryString = "SELECT LOGINID,NAME FROM TPO_USER WHERE ROLE_ID = 2";
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                adminUsersMap.put(resultSet.getString("LOGINID"), resultSet.getString("NAME"));
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return adminUsersMap;
    }

    public int getIsExistedUserId(int partnerId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int userid = 0;
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT count(ID) AS user FROM MSCVP.TPO_USER WHERE PARTNER_ID=" + partnerId + "");
            while (resultSet.next()) {
                userid = resultSet.getInt("user");
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return userid;
    }

    public String getTpoPartnerName(int partnerId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String partnerName = "";
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT NAME FROM MSCVP.TPO_PARTNERS WHERE ID=" + partnerId + "");
            while (resultSet.next()) {
                partnerName = resultSet.getString("NAME");
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return partnerName;
    }

    public int getTPOPartnerId(String partnername) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String queryString = null;
        int partnerId = 0;
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT ID FROM MSCVP.TPO_PARTNERS WHERE NAME='" + partnername + "'");
            while (resultSet.next()) {
                partnerId = resultSet.getInt("ID");
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return partnerId;
    }

    public Map getMyUsersList(String loginId, int roleId) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        Map adminUsersMap = new TreeMap();
        try {
            int userRoleId = 0;
            if (roleId == 1) {
                userRoleId = 2;
            }
            if (roleId == 3) {
                userRoleId = 4;
            }
            if (roleId == 4) {
                userRoleId = 5;
            }
            queryString = "SELECT LOGINID,(NAME || ' ' || LNAME) AS NAME FROM TPO_USER WHERE CREATED_BY = '" + loginId + "' AND ROLE_ID = " + userRoleId;
            System.out.println("queryString-->" + queryString);
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                adminUsersMap.put(resultSet.getString("LOGINID"), resultSet.getString("NAME"));
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return adminUsersMap;
    }

    public Map getMyPartnersList(String loginId, int roleId) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        Map partnersMap = new TreeMap();
        try {
            if (roleId == 1) {
                queryString = "SELECT ID,NAME FROM TPO_PARTNERS ";
            } else {
                queryString = "SELECT ID,NAME FROM TPO_PARTNERS Where CREATED_BY='" + loginId + "' OR ASSIGNED_TO = '" + loginId + "'";
            }
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                partnersMap.put(resultSet.getInt("ID"), resultSet.getString("NAME"));
            }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                throw new ServiceLocatorException(ex);
            }
        }
        return partnersMap;
    }

    public String getCertificatePath(String filePath) {
        String CertificatePath = "";
        InputStream inputStream = null;
        BufferedReader br = null;
        //String responseString = "";
        try {
            System.out.println("System.out.println(filepath);--" + filePath);
            inputStream = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            CertificatePath = sb.toString();
            System.out.println(CertificatePath);
            System.out.println("\nDone!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("RESULT=========" + CertificatePath);
        return CertificatePath;
    }
}
