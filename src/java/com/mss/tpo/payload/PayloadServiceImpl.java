/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.payload;

import com.mss.tpo.tpOnboarding.TpOnboardingBean;
import com.mss.tpo.util.ConnectionProvider;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.DateUtility;
import com.mss.tpo.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Narendar
 */
public class PayloadServiceImpl implements PayloadService {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    String responseString = null;
    private HttpServletRequest httpServletRequest;
    private ArrayList<PayloadBean> payloadSearchList;
     private ArrayList<TpOnboardingBean> tpoCommunicationsList;

    public ArrayList<PayloadBean> payloadSearch(String loginId, int partnerId) {
        StringBuffer queryString = new StringBuffer();
        queryString.append(" SELECT * FROM MSCVP.TPO_PAYLOAD WHERE PARTNER_ID =" + partnerId);

        try {
            Connection con = ConnectionProvider.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(queryString.toString());
            payloadSearchList = new ArrayList<PayloadBean>();
            while (rs.next()) {
                PayloadBean payloadBean = new PayloadBean();
                payloadBean.setId(rs.getInt("ID"));
                payloadBean.setCorrelationID(rs.getInt("COMMUNICATION_ID"));
                payloadBean.setTransaction(rs.getInt("TRANSACTION"));
                payloadBean.setDirection(rs.getString("DIRECTION"));
                payloadBean.setLastTestStatus(rs.getString("LAST_TEST_STATUS"));
                payloadBean.setLastTestDate(rs.getTimestamp("LAST_TEST_DATE"));
                payloadBean.setCurrentTestStatus(rs.getString("CURRENT_TEST_STATUS"));
                payloadBean.setCurrentTestDate(rs.getTimestamp("CURRENT_TEST_DATE"));
                payloadSearchList.add(payloadBean);
            }
        } catch (Exception ex) {
            Logger.getLogger(PayloadServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payloadSearchList;
    }

    public String doPayloadUpload(int partnerId, String loginId, String filePath, PayloadAction payloadAction) throws ServiceLocatorException {
        int isPayloadInserted = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            String payloadQuery = "INSERT INTO MSCVP.TPO_PAYLOAD (PARTNER_ID, PARTNER_NAME, TRANSACTION, "
                    + "DOC_TYPE, DIRECTION, PATH, STATUS, COMMUNICATION_ID,CREATED_BY,CREATED_TS,CONNECTION_TYPE) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                 preparedStatement = connection.prepareStatement(payloadQuery);
            preparedStatement.setInt(1, partnerId);
            preparedStatement.setString(2, DataSourceDataProvider.getInstance().getTpoPartnerName(partnerId));
            preparedStatement.setString(3, payloadAction.getTransaction());
            preparedStatement.setString(4, payloadAction.getDocType());
            preparedStatement.setString(5, payloadAction.getDirection());
            preparedStatement.setString(6, filePath);
            preparedStatement.setString(7, "UPLOADED");
           // preparedStatement.setInt(8, 00);
            preparedStatement.setInt(8, payloadAction.getCommunicationId());
            preparedStatement.setString(9, loginId);
            preparedStatement.setTimestamp(10, curdate);
           preparedStatement.setString(11, payloadAction.getConn_type());
            isPayloadInserted = isPayloadInserted + preparedStatement.executeUpdate();
            if (isPayloadInserted > 0) {
                responseString = "<font color='green'>Payload uploaded successfully.</font>";
            } else {
                responseString = "<font color='green'>Payload uploaded failed.</font>";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            responseString = "<font color='red'>Please try again!</font>";
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                    callableStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException se) {
                throw new ServiceLocatorException(se);
            }
        }
        return responseString;
    }
 public ArrayList<TpOnboardingBean> getFTPCommunicationsList(String loginId, int roleId, int partnerId, String protocol) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String queryString = null;
        try {
            if (roleId == 3) {
                queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, FTP_METHOD, "
                        + "RECEIVING_PROTOCOL, FTP_HOST, FTP_PORT, FTP_USER_ID, FTP_PASSWORD, FTP_DIRECTORY, "
                        + "CONNECTION_METHOD, RESPONSE_TIMEOUT_SEC, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                        + " FROM MSCVP.TPO_FTP WHERE PARTNER_ID=" + partnerId;
            } else {
                queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, FTP_METHOD, "
                        + "RECEIVING_PROTOCOL, FTP_HOST, FTP_PORT, FTP_USER_ID, FTP_PASSWORD, FTP_DIRECTORY, "
                        + "CONNECTION_METHOD, RESPONSE_TIMEOUT_SEC, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                        + " FROM MSCVP.TPO_FTP WHERE PARTNER_ID=" + partnerId+" AND CREATED_BY='" + loginId + "'";
            }
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            tpoCommunicationsList = new ArrayList<TpOnboardingBean>();

            while (resultSet.next()) {
                TpOnboardingBean tpOnboardingBean = new TpOnboardingBean();
                tpOnboardingBean.setId(resultSet.getInt("COMMUNICATION_ID"));
                tpOnboardingBean.setPartnerId(resultSet.getInt("PARTNER_ID"));
                tpOnboardingBean.setFtp_recv_protocol(resultSet.getString("RECEIVING_PROTOCOL"));
                tpOnboardingBean.setFtp_host(resultSet.getString("FTP_HOST"));
                tpOnboardingBean.setFtp_port(resultSet.getString("FTP_PORT"));
                tpOnboardingBean.setFtp_directory(resultSet.getString("FTP_DIRECTORY"));
                tpoCommunicationsList.add(tpOnboardingBean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tpoCommunicationsList;
    }
    public ArrayList<TpOnboardingBean> getSFTPCommunicationsList(String loginId, int roleId, int partnerId, String protocol) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
         String queryString = null;
        try {
            if (roleId == 3) {
                queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, CONN_METHOD, MY_SSH_PUB_KEY, "
                        + "UPL_YOUR_SSH_PUB_KEY, REMOTE_HOST_IP_ADD, REMOTE_USERID, METHOD, AUTH_METHOD, REMOTE_PORT, "
                        + "REMOTE_PWD, DIRECTORY, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                        + " FROM MSCVP.TPO_SFTP WHERE PARTNER_ID=" + partnerId;
            } else {
                queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, CONN_METHOD, MY_SSH_PUB_KEY, "
                        + "UPL_YOUR_SSH_PUB_KEY, REMOTE_HOST_IP_ADD, REMOTE_USERID, METHOD, AUTH_METHOD, REMOTE_PORT, "
                        + "REMOTE_PWD, DIRECTORY, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                        + " FROM MSCVP.TPO_SFTP WHERE PARTNER_ID=" + partnerId+" AND CREATED_BY='" + loginId + "'";
            }
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            tpoCommunicationsList = new ArrayList<TpOnboardingBean>();

            while (resultSet.next()) {
                TpOnboardingBean tpOnboardingBean = new TpOnboardingBean();
                tpOnboardingBean.setId(resultSet.getInt("COMMUNICATION_ID"));
                tpOnboardingBean.setPartnerId(resultSet.getInt("PARTNER_ID"));
                tpOnboardingBean.setSftp_host_ip(resultSet.getString("REMOTE_HOST_IP_ADD"));
                tpOnboardingBean.setSftp_remote_port(resultSet.getString("REMOTE_PORT"));
                tpOnboardingBean.setSftp_directory(resultSet.getString("DIRECTORY"));
                tpoCommunicationsList.add(tpOnboardingBean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tpoCommunicationsList;
    }
    public ArrayList<TpOnboardingBean> getHTTPCommunicationsList(String loginId, int roleId, int partnerId, String protocol) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
         String queryString = null;
        try {
            if (roleId == 3) {
                queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, RECEIVING_PROTOCOL, HTTP_END_POINT, HTTP_MODE, "
                        + "RESPONSE_TIMEOUT_SEC, HTTP_PORT, URL, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                        + "FROM MSCVP.TPO_HTTP WHERE PARTNER_ID=" + partnerId;
            } else {
               queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, RECEIVING_PROTOCOL, HTTP_END_POINT, HTTP_MODE, "
                       + "RESPONSE_TIMEOUT_SEC, HTTP_PORT, URL, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                       + "FROM MSCVP.TPO_HTTP WHERE PARTNER_ID=" + partnerId+" AND CREATED_BY='" + loginId + "'";
            }
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            tpoCommunicationsList = new ArrayList<TpOnboardingBean>();

            while (resultSet.next()) {
                TpOnboardingBean tpOnboardingBean = new TpOnboardingBean();
                 tpOnboardingBean.setId(resultSet.getInt("COMMUNICATION_ID"));
                tpOnboardingBean.setPartnerId(resultSet.getInt("PARTNER_ID"));
                tpOnboardingBean.setHttp_recv_protocol(resultSet.getString("RECEIVING_PROTOCOL"));
                tpOnboardingBean.setHttp_port(resultSet.getInt("HTTP_PORT"));
                tpOnboardingBean.setHttp_url(resultSet.getString("URL"));
                tpOnboardingBean.setHttp_protocol_mode(resultSet.getString("HTTP_MODE"));
                tpoCommunicationsList.add(tpOnboardingBean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tpoCommunicationsList;
    }
    public ArrayList<TpOnboardingBean> getSMTPCommunicationsList(String loginId, int roleId, int partnerId, String protocol) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
         String queryString = null;
        try {
            if (roleId == 3) {
                queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, RECEIVING_PROTOCOL, SMTP_SERVER_PORT, "
                        + "TO_EMAIL_ADDRESS, SMTP_SERVER_HOST, FROM_EMAIL_ADDRESS, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                        + "FROM MSCVP.TPO_SMTP WHERE PARTNER_ID=" + partnerId;
            } else {
                 queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, RECEIVING_PROTOCOL, SMTP_SERVER_PORT, "
                         + "TO_EMAIL_ADDRESS, SMTP_SERVER_HOST, FROM_EMAIL_ADDRESS, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                         + "FROM MSCVP.TPO_SMTP WHERE PARTNER_ID=" + partnerId+" AND CREATED_BY='" + loginId + "'";
            }
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            tpoCommunicationsList = new ArrayList<TpOnboardingBean>();

            while (resultSet.next()) {
                TpOnboardingBean tpOnboardingBean = new TpOnboardingBean();
                 tpOnboardingBean.setId(resultSet.getInt("COMMUNICATION_ID"));
                tpOnboardingBean.setPartnerId(resultSet.getInt("PARTNER_ID"));
                tpOnboardingBean.setSmtp_recv_protocol(resultSet.getString("SMTP_SERVER_HOST"));
                tpOnboardingBean.setSmtp_server_port(resultSet.getInt("SMTP_SERVER_PORT"));
                tpOnboardingBean.setSmtp_from_email(resultSet.getString("FROM_EMAIL_ADDRESS"));
                tpOnboardingBean.setSmtp_to_email(resultSet.getString("TO_EMAIL_ADDRESS"));
                tpoCommunicationsList.add(tpOnboardingBean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tpoCommunicationsList;
    }
    public ArrayList<TpOnboardingBean> getAS2CommunicationsList(String loginId, int roleId, int partnerId, String protocol) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
         String queryString = null;
        try {
            if (roleId == 3) {
                queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, DWL_MY_SYS_CERT, UPL_YOUR_SYS_CERT, "
                        + "MY_ORG, YOUR_ORG, MY_PART_PRO_NAME, YOUR_PART_PRO_NAME, MY_END_POINT, YOUR_END_POINT, STR_AS2_MSG_IN, "
                        + "WAIT_FOR_SYNC_MDN_PROC, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                        + "FROM MSCVP.TPO_AS2 WHERE PARTNER_ID=" + partnerId;
            } else {
               queryString = "SELECT COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, DWL_MY_SYS_CERT, UPL_YOUR_SYS_CERT, "
                       + "MY_ORG, YOUR_ORG, MY_PART_PRO_NAME, YOUR_PART_PRO_NAME, MY_END_POINT, YOUR_END_POINT, STR_AS2_MSG_IN, "
                       + "WAIT_FOR_SYNC_MDN_PROC, TP_FLAG, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS, STATUS "
                       + "FROM MSCVP.TPO_AS2 WHERE PARTNER_ID=" + partnerId+" AND CREATED_BY='" + loginId + "'";
            }
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            tpoCommunicationsList = new ArrayList<TpOnboardingBean>();

            while (resultSet.next()) {
                TpOnboardingBean tpOnboardingBean = new TpOnboardingBean();
               tpOnboardingBean.setId(resultSet.getInt("COMMUNICATION_ID"));
                tpOnboardingBean.setPartnerId(resultSet.getInt("PARTNER_ID"));
                 tpOnboardingBean.setAs2_myOrgName(resultSet.getString("MY_ORG"));
                 tpOnboardingBean.setAs2_partOrgName(resultSet.getString("YOUR_ORG"));
                 tpOnboardingBean.setAs2_myPartname(resultSet.getString("MY_PART_PRO_NAME"));
                 tpOnboardingBean.setAs2_yourPartname(resultSet.getString("YOUR_PART_PRO_NAME"));
                tpoCommunicationsList.add(tpOnboardingBean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tpoCommunicationsList;
    }
}
