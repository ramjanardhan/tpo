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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Narendar
 */
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

    public String getuserNameByUserId(String loginId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String userName = "";
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT NAME FROM MSCVP.TPO_USER WHERE LOGINID ='" + loginId + "'");
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

    public Map getPartnerRolesList(int roleId) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        Map partnerRolesMap = new TreeMap();
        try {
            if (roleId == 3) {
                queryString = "SELECT ID, ROLE FROM TPO_USER_ROLES WHERE (ID = 4 OR ID = 5) ";
            } else if (roleId == 4) {
                queryString = "SELECT ID, ROLE FROM TPO_USER_ROLES WHERE ID = 5 ";
            }
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                partnerRolesMap.put(resultSet.getString("ID"), resultSet.getString("ROLE"));
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
        return partnerRolesMap;
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
            inputStream = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            CertificatePath = sb.toString();
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
        return CertificatePath;
    }

    public String getEmaiIdByloginId(String loginId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String email = "";
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT EMAIL FROM MSCVP.TPO_USER WHERE LOGINID ='" + loginId + "'");
            while (resultSet.next()) {
                email = resultSet.getString("EMAIL");
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
        return email;
    }

    public static List<String> getTxList() throws ServiceLocatorException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        List<String> list = new ArrayList<String>();
        try {
            queryString = "SELECT TRANSACTION_TYPE FROM TPO_LOOK_TX";
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("TRANSACTION_TYPE"));
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
        return list;
    }

    public Map getPayloadProtocols(String loginId, int roleId, int partnerId) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        Map protocolsMap = new TreeMap();
        try {
            // if (roleId == 3) {
            queryString = "select DISTINCT(PROTOCOL) as protocol from MSCVP.TPO_COMMUNICATION where PARTNER_ID=" + partnerId;
//            } else {
//                queryString = "select DISTINCT(PROTOCOL) as protocol from MSCVP.TPO_COMMUNICATION where PARTNER_ID=" + partnerId + " and CREATED_BY='" + loginId + "'";
//            }
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //protocolsMap.put(resultSet.getString("protocol"), resultSet.getString("protocol"));
                protocolsMap.put(resultSet.getString("protocol"), getProtocolNameByProtocol(resultSet.getString("protocol")));
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
        return protocolsMap;
    }

    public String getProtocolNameByProtocol(String protocol) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String protocolName = "";
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT PROTOCOL_NAME FROM MSCVP.TPO_PROTOCOLS WHERE PROTOCOL ='" + protocol + "' ");
            while (resultSet.next()) {
                protocolName = resultSet.getString("PROTOCOL_NAME");
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
        return protocolName;
    }

    public Map getCommunicationProtocols(int roleId) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        Map protocolsMap = new TreeMap();
        try {
            if (roleId == 1 || roleId == 2) {
                queryString = "SELECT PROTOCOL, PROTOCOL_NAME FROM MSCVP.TPO_PROTOCOLS WHERE PROTOCOL != 'AS2' AND PROTOCOL != 'SMTP' ";
            } else {
                queryString = "SELECT PROTOCOL, PROTOCOL_NAME FROM MSCVP.TPO_PROTOCOLS";
            }
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                protocolsMap.put(resultSet.getString("PROTOCOL"), resultSet.getString("PROTOCOL_NAME"));
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
        return protocolsMap;
    }

    public String getProtocolByCommunicationId(int communicationId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String protocol = "";
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT PROTOCOL FROM MSCVP.TPO_COMMUNICATION WHERE ID =" + communicationId + " ");
            while (resultSet.next()) {
                protocol = resultSet.getString("PROTOCOL");
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
        return protocol;
    }

    public String getRoleNameByRoleId(int roleId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String roleName = "";
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT ROLE FROM MSCVP.TPO_USER_ROLES WHERE ID = " + roleId);
            while (resultSet.next()) {
                roleName = resultSet.getString("ROLE");
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
        return roleName;
    }

    public String getAdminEmail() throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String adminEmail = "";
        connection = ConnectionProvider.getInstance().getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT EMAIL FROM MSCVP.TPO_USER WHERE ROLE_ID = 1");
            while (resultSet.next()) {
                adminEmail = resultSet.getString("EMAIL");
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
        return adminEmail;
    }

    // get List_Name for CodeList 
    public List getListName() throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getOracleConnection();
        List listNameMap = new ArrayList();
        String listName = null;
        try {
            queryString = "SELECT  DISTINCT(LIST_NAME) FROM CODELIST_XREF_ITEM";
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listNameMap.add(resultSet.getString("LIST_NAME"));
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
        return listNameMap;
    }

    public int getAssignedCommunications(int partnerId, String protocol) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getConnection();
        int assignedCommunication = 0;
        try {
            queryString = "SELECT DISTINCT(COMMUNICATION_ID) AS COMM_ID FROM MSCVP.TPO_PARTNER_COMMUNICATIONS WHERE PARTNER_ID =" + partnerId + " AND PROTOCOL = '" + protocol + "'";
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                assignedCommunication = resultSet.getInt("COMM_ID");
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
        return assignedCommunication;
    }

    public String getFilePath(int communicationId) throws ServiceLocatorException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String filePath = "";
        try {
            String protocol = "";
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            String queryString = "SELECT PROTOCOL FROM MSCVP.TPO_COMMUNICATION WHERE ID =" + communicationId;
            resultSet = statement.executeQuery(queryString);
            while (resultSet.next()) {
                protocol = resultSet.getString("PROTOCOL");
            }
            if ("AS2".equals(protocol)) {
                resultSet = statement.executeQuery("SELECT UPL_YOUR_SYS_CERT FROM MSCVP.TPO_AS2 where COMMUNICATION_ID = " + communicationId);
                while (resultSet.next()) {
                    filePath = resultSet.getString("UPL_YOUR_SYS_CERT");
                }
            } //            else if("FTP".equals(protocol)){
            //                protocolQuery = "SELECT PROTOCOL FROM MSCVP.TPO_COMMUNICATION WHERE ID ="+communicationId;
            //            }
            else if ("SFTP".equals(protocol)) {
                resultSet = statement.executeQuery("SELECT UPL_YOUR_SSH_PUB_KEY FROM MSCVP.TPO_SFTP WHERE COMMUNICATION_ID =" + communicationId);
                while (resultSet.next()) {
                    filePath = resultSet.getString("UPL_YOUR_SSH_PUB_KEY");
                }
            }
//            else if("HTTP".equals(protocol)){
//                protocolQuery = "SELECT PROTOCOL FROM MSCVP.TPO_COMMUNICATION WHERE ID ="+communicationId;
//            }

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
        return filePath;
    }
}
