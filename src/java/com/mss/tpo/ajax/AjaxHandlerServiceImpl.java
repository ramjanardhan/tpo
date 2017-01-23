/*
 * AjaxHandlerServiceImpl.java
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.mss.tpo.ajax;

import com.mss.tpo.util.ConnectionProvider;
import com.mss.tpo.util.ServiceLocatorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Statement;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.DateUtility;
import com.mss.tpo.util.MailManager;
import com.mss.tpo.util.PasswordUtil;
import com.mss.tpo.util.RandomPasswordGenerator;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class AjaxHandlerServiceImpl implements AjaxHandlerService {

    /**
     * Creating a reference variable for Connection
     */
    private Connection connection;
    /**
     * Creating a reference variable for preparedStatement
     */
    private PreparedStatement preparedStatement;
    /**
     * Creating a reference variable for Resultset
     */
    private ResultSet resultSet;
    /**
     * Creating a reference variable for String Buffer
     */
    private StringBuffer stringBuffer;
    /**
     * Creating a String queryString used to store SQL Query
     */
    private String queryString;
    /**
     * Creates a new instance of AjaxHandlerServiceImpl
     */
    private HttpServletRequest httpServletRequest;
    private Statement statement;

    public AjaxHandlerServiceImpl() {
    }

    /**
     * This method is used to get the Consultant Details
     *
     * @param consultantMail
     * @return String
     * @throws com.mss.mirage.util.ServiceLocatorException
     */
    public String forgotPassword(String userid, String regEmail) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String str = null;
        String sqlQuery = "SELECT EMAIL FROM TPO_USER WHERE LOGINID=? AND EMAIL = ?";
        try {
            String email = null;
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, userid);
            preparedStatement.setString(2, regEmail);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //password = passwordUtil.decryptPwd(resultSet.getString("PASSWORD"));
                email = resultSet.getString("EMAIL");
            }
            if (regEmail.equals(email)) {
                String password = RandomPasswordGenerator.generatePswd(4, 7, 2, 2, 0);
                int count = 0;
                Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
                String updatePwdQuery = ("UPDATE MSCVP.TPO_USER SET PASSWORD = ?,MODIFIED_BY=?, MODIFIED_TS=? WHERE LOGINID=? AND EMAIL = ?");
                preparedStatement = connection.prepareStatement(updatePwdQuery);
                preparedStatement.setString(1, PasswordUtil.encryptPwd(password));
                preparedStatement.setString(2, userid);
                preparedStatement.setTimestamp(3, curdate);
                preparedStatement.setString(4, userid);
                preparedStatement.setString(5, email);
                count = count + preparedStatement.executeUpdate();
                if (count > 0) {
                    str = MailManager.sendUserIdPwd(userid, email, password);
                } else {
                    str = "<font size='2' color='red'>please try again later.</font>";
                }
            } else {
                str = "<font size='2' color='red'>Given mail id is not matched with registered mail id</font>";
            }

        } catch (Exception ex) {
            throw new ServiceLocatorException(ex);
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
            } catch (SQLException sqle) {

                throw new ServiceLocatorException(sqle);
            }
        }
        return str;
    }

    public String isExistedPartnerName(String partnerName) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement PreparedStatement = null;
        ResultSet resultSet = null;
        String responseString = "";
        try {
            queryString = "SELECT NAME FROM TPO_PARTNERS where REPLACE(UPPER(NAME),' ','')=REPLACE(UPPER('" + partnerName + "'),' ','') ";
            // queryString = "SELECT PARTNER_NAME FROM TPO_USER where PARTNER_NAME='"+ partnerName +"'";
            connection = ConnectionProvider.getInstance().getConnection();
            PreparedStatement = connection.prepareStatement(queryString);
            resultSet = PreparedStatement.executeQuery();
            if (resultSet.next()) {
                responseString = "No";
            } else {
                responseString = "Yes";
            }
        } catch (Exception sqe) {
            sqe.printStackTrace();
            responseString = "<font size='2' color='red'>Error!</font>";
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
            } catch (SQLException sqle) {
            }
        }
        return responseString;
    }

    public String isExistedUserEmail(String email) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String responseString = "";
        try {
            //  queryString = "SELECT EMAIL FROM TPO_USER where REPLACE(UPPER(EMAIL),' ','')=REPLACE(UPPER('" + email + "'),' ','')  ";
            queryString = "SELECT EMAIL FROM TPO_USER where EMAIL='" + email + "'";
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                responseString = "No";
            } else {
                responseString = "Yes";
            }
        } catch (Exception sqe) {
            sqe.printStackTrace();
            responseString = "<font size='2' color='red'>Error!</font>";
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
            } catch (SQLException sqle) {
            }
        }
        return responseString;
    }

    public String doAddTpoPartner(int roleId, String createdBy, AjaxHandlerAction ajaxHandlerAction) throws ServiceLocatorException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        int insertedRows = 0;
        String responseString = "";
        PasswordUtil passwordUtil = new PasswordUtil();
        String password = RandomPasswordGenerator.generatePswd(4, 7, 2, 2, 0);
        String generatedPassword = passwordUtil.encryptPwd(password);
        String userloginId = ajaxHandlerAction.getEmail().substring(0, ajaxHandlerAction.getEmail().indexOf("@")).toLowerCase();
        try {
            connection = ConnectionProvider.getInstance().getConnection();

            connection = ConnectionProvider.getInstance().getConnection();
            callableStatement = connection.prepareCall("call SP_PARTNER_REGISTRATION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            callableStatement.setString(1, ajaxHandlerAction.getAddpartnerName());
            callableStatement.setString(2, ajaxHandlerAction.getName());
            callableStatement.setString(3, userloginId);
            callableStatement.setString(4, ajaxHandlerAction.getEmail());
            callableStatement.setString(5, generatedPassword);
            callableStatement.setString(6, ajaxHandlerAction.getAddphoneNo());
            callableStatement.setString(7, ajaxHandlerAction.getAddaddress1());
            callableStatement.setString(8, ajaxHandlerAction.getAddcity());
            callableStatement.setString(9, ajaxHandlerAction.getAddstate());
            callableStatement.setString(10, ajaxHandlerAction.getAddcountry());
            callableStatement.setString(11, ajaxHandlerAction.getAddzipCode());
            callableStatement.setString(12, ajaxHandlerAction.getUrl());
            callableStatement.setString(13, ajaxHandlerAction.getDescription());
            callableStatement.setString(14, createdBy);
            callableStatement.setString(15, ajaxHandlerAction.getLastName());
            if (roleId == 1) {
                callableStatement.setString(16, ajaxHandlerAction.getAssignTo());
            } else {
                callableStatement.setString(16, "");
            }
            callableStatement.setString(17, ajaxHandlerAction.getFlag());
            insertedRows = callableStatement.executeUpdate();

            if ("self".equalsIgnoreCase(ajaxHandlerAction.getFlag())) {
                MailManager.tpoPartnerSelfRegistrationMail(userloginId, password, ajaxHandlerAction);
            } else if ("admin".equalsIgnoreCase(ajaxHandlerAction.getFlag())) {
                MailManager.tpoPartnerAddMail(userloginId, password, ajaxHandlerAction);
            }

            responseString = "<font size='2' color='green'>Partner added successfully</font>";
        } catch (SQLException e) {
            responseString = "<font color='red'>Please try with different Id!</font>";
            e.printStackTrace();
        } catch (ServiceLocatorException sle) {
            responseString = "<font color='red'>Please try later!</font>";
            sle.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return responseString;
    }

    public String doUpdateEnvelope(String envelopeDetials, String loginId, int partnerId, String transaction, String direction) throws ServiceLocatorException {
        int count = 0;
        String responseString = "";
        try {
            String envelopeData[] = envelopeDetials.substring(0, envelopeDetials.length()).split(Pattern.quote("@"));
            connection = ConnectionProvider.getInstance().getConnection();
            String updateEnvelopeQuery = ("UPDATE MSCVP.TPO_ENVELOPES SET TRANSACTION = ?,DIRECTION = ?,SENDERID_ISA = ?,"
                    + "SENDERID_GS = ?,SENDERID_ST = ?,RECEIVERID_ISA = ?,RECEIVERID_GS = ?,RECEIVERID_ST = ?,VERSION_ISA = ?,"
                    + "VERSION_GS = ?,VERSION_ST = ?,FUNCTIONAL_ID_CODE_GS = ?,RESPONSIBLE_AGENCY_CODE_GS = ?,"
                    + "GENERATE_AN_ACKNOWLEDGEMENT_GS = ?,TRANSACTION_SET_ID_CODE_ST = ?,MODIFIED_BY = ?,MODIFIED_TS =?,TP_FLAG=? "
                    + "WHERE TRANSACTION = ? AND DIRECTION = ? AND PARTNER_ID = ?");
            preparedStatement = connection.prepareStatement(updateEnvelopeQuery);
            preparedStatement.setString(1, envelopeData[0]);
            preparedStatement.setString(2, envelopeData[1]);
            preparedStatement.setString(3, envelopeData[2]);
            preparedStatement.setString(4, envelopeData[3]);
            preparedStatement.setString(5, envelopeData[4]);
            preparedStatement.setString(6, envelopeData[5]);
            preparedStatement.setString(7, envelopeData[6]);
            preparedStatement.setString(8, envelopeData[7]);
            preparedStatement.setString(9, envelopeData[8]);
            preparedStatement.setString(10, envelopeData[9]);
            preparedStatement.setString(11, envelopeData[10]);
            preparedStatement.setString(12, envelopeData[11]);
            preparedStatement.setString(13, envelopeData[12]);
            preparedStatement.setString(14, envelopeData[13]);
            preparedStatement.setString(15, envelopeData[14]);
            preparedStatement.setString(16, loginId);
            preparedStatement.setTimestamp(17, DateUtility.getInstance().getCurrentDB2Timestamp());
            preparedStatement.setString(18, "N");
            preparedStatement.setString(19, transaction);
            preparedStatement.setString(20, direction);
            preparedStatement.setInt(21, partnerId);
            count = count + preparedStatement.executeUpdate();
            if (count > 0) {
                responseString = "<font color='green'>Envelope updated sucessfully</font>";
            } else {
                responseString = "<font color='red'>Envelope update filaed</font>";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ServiceLocatorException sle) {
            sle.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return responseString;
    }

    public String getProtocolDetails(String transferMode, String protocol) throws ServiceLocatorException {
        stringBuffer = new StringBuffer();
        Statement statement = null;
        ResultSet resultSet = null;
        JSONObject subJson = null;
        int partnerId = 0;
        int i = 0;
        String empty = "";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            if ("FTP".equalsIgnoreCase(protocol) && "get".equalsIgnoreCase(transferMode)) {
                String ftpTransferModeQuery = "";
                String ftpSslQuery = "";
                String ftp_ssl_req = "";
                subJson = new JSONObject();
                // if ("get".equalsIgnoreCase(transferMode)) {
                i = 1;
                ftpTransferModeQuery = "SELECT ID,COMMUNICATION_ID,PARTNER_ID,FTP_METHOD,RECEIVING_PROTOCOL,FTP_HOST,FTP_PORT,"
                        + "FTP_USER_ID,FTP_PASSWORD,FTP_DIRECTORY,CONNECTION_METHOD,RESPONSE_TIMEOUT_SEC,TRANSFER_MODE,"
                        + "SSL_FLAG,CREATED_BY,CREATED_TS,MODIFIED_BY,MODIFIED_TS "
                        + "FROM MSCVP.TPO_FTP WHERE  RECEIVING_PROTOCOL = 'FTP' AND ID = 1 AND TRANSFER_MODE = 'get'";
                resultSet = statement.executeQuery(ftpTransferModeQuery);
                if (resultSet.next()) {
                    subJson.put("FTP_METHOD", resultSet.getString("FTP_METHOD"));
                    subJson.put("CONNECTION_METHOD", resultSet.getString("CONNECTION_METHOD"));
                    subJson.put("RECEIVING_PROTOCOL", resultSet.getString("RECEIVING_PROTOCOL"));
                    subJson.put("RESPONSE_TIMEOUT_SEC", resultSet.getInt("RESPONSE_TIMEOUT_SEC"));
                    subJson.put("FTP_HOST", resultSet.getString("FTP_HOST"));
                    subJson.put("FTP_PORT", resultSet.getString("FTP_PORT"));
                    subJson.put("FTP_USER_ID", resultSet.getString("FTP_USER_ID"));
                    subJson.put("FTP_PASSWORD", resultSet.getString("FTP_PASSWORD"));
                    subJson.put("FTP_DIRECTORY", resultSet.getString("FTP_DIRECTORY"));
                    ftp_ssl_req = resultSet.getString("SSL_FLAG");
                    subJson.put("SSL_REQUIRED_FLAG", ftp_ssl_req);
                }
                if ("true".equalsIgnoreCase(ftp_ssl_req)) {
                    ftpSslQuery = "SELECT ID,COMMUNICATION_ID,PROTOCOL, SSL_PRIORITY, KEY_CERTIFICATE_PASSPHRASE, CIPHER_STRENGTH, KEY_CERTIFICATE, CA_CERTIFICATES, TP_FLAG,PARTNER_ID,TRANSFER_MODE FROM MSCVP.TPO_SSL WHERE TRANSFER_MODE = 'get' AND PROTOCOL = 'FTP' AND ID = 1";
                    resultSet = statement.executeQuery(ftpSslQuery);
                    if (resultSet.next()) {
                        subJson.put("SSL_PRIORITY", resultSet.getString("SSL_PRIORITY"));
                        subJson.put("KEY_CERTIFICATE_PASSPHRASE", resultSet.getString("KEY_CERTIFICATE_PASSPHRASE"));
                        subJson.put("CIPHER_STRENGTH", resultSet.getString("CIPHER_STRENGTH"));
                    }
                }
            } else if ("SFTP".equalsIgnoreCase(protocol)) {
                String sftpTransferModeQuery = "";
                if ("get".equalsIgnoreCase(transferMode)) {
                    i = 1;
                    sftpTransferModeQuery = "SELECT ID,PARTNER_ID,CONN_METHOD,MY_SSH_PUB_KEY,UPL_YOUR_SSH_PUB_KEY,REMOTE_HOST_IP_ADD,"
                            + "REMOTE_USERID,METHOD,AUTH_METHOD,REMOTE_PORT,REMOTE_PWD,DIRECTORY,TRANSFER_MODE,CREATED_BY,"
                            + "CREATED_TS,MODIFIED_BY,MODIFIED_TS FROM MSCVP.TPO_SFTP WHERE ID =1 AND TRANSFER_MODE = 'get'";
                    resultSet = statement.executeQuery(sftpTransferModeQuery);
                    if (resultSet.next()) {
                        subJson = new JSONObject();
                        subJson.put("CONN_METHOD", resultSet.getString("CONN_METHOD"));
                        subJson.put("AUTH_METHOD", resultSet.getString("AUTH_METHOD"));
                        //subJson.put("MY_SSH_PUB_KEY", resultSet.getString("MY_SSH_PUB_KEY"));
                        // subJson.put("UPL_YOUR_SSH_PUB_KEY", resultSet.getInt("UPL_YOUR_SSH_PUB_KEY"));
                        subJson.put("REMOTE_HOST_IP_ADD", resultSet.getString("REMOTE_HOST_IP_ADD"));
                        subJson.put("REMOTE_PORT", resultSet.getString("REMOTE_PORT"));
                        subJson.put("REMOTE_USERID", resultSet.getString("REMOTE_USERID"));
                        subJson.put("REMOTE_PWD", resultSet.getString("REMOTE_PWD"));
                        subJson.put("METHOD", resultSet.getString("METHOD"));
                        subJson.put("DIRECTORY", resultSet.getString("DIRECTORY"));
                    }
                }
            } else if ("HTTP".equalsIgnoreCase(protocol)) {
                String httpTransferModeQuery = "";
                String http_ssl_req = "";
                String httpSslQuery = "";
                subJson = new JSONObject();
                if ("put".equalsIgnoreCase(transferMode)) {
                    i = 1;
                    httpTransferModeQuery = "SELECT ID, PARTNER_ID, RECEIVING_PROTOCOL, HTTP_END_POINT, HTTP_MODE, RESPONSE_TIMEOUT_SEC, HTTP_PORT, "
                            + "SSL_FLAG, TRANSFER_MODE, CREATED_BY, CREATED_TS, MODIFIED_BY, MODIFIED_TS,URL "
                            + "FROM MSCVP.TPO_HTTP WHERE ID = 1 AND TRANSFER_MODE = 'put'";
                    resultSet = statement.executeQuery(httpTransferModeQuery);
                    if (resultSet.next()) {
                        subJson.put("RECEIVING_PROTOCOL", resultSet.getString("RECEIVING_PROTOCOL"));
                        subJson.put("RESPONSE_TIMEOUT_SEC", resultSet.getString("RESPONSE_TIMEOUT_SEC"));
                        subJson.put("HTTP_END_POINT", resultSet.getString("HTTP_END_POINT"));
                        subJson.put("HTTP_PORT", resultSet.getInt("HTTP_PORT"));
                        subJson.put("HTTP_MODE", resultSet.getString("HTTP_MODE"));
                        http_ssl_req = resultSet.getString("SSL_FLAG");
                        subJson.put("SSL_REQUIRED", http_ssl_req);
                    }
                    if ("true".equalsIgnoreCase(http_ssl_req)) {
                        httpSslQuery = "SELECT ID, PROTOCOL, SSL_PRIORITY, KEY_CERTIFICATE_PASSPHRASE, CIPHER_STRENGTH, KEY_CERTIFICATE, CA_CERTIFICATES, TP_FLAG,PARTNER_ID,TRANSFER_MODE FROM MSCVP.TPO_SSL WHERE TRANSFER_MODE = 'put' AND PROTOCOL = 'HTTP' AND ID = 2";
                        resultSet = statement.executeQuery(httpSslQuery);
                        if (resultSet.next()) {
                            subJson.put("SSL_PRIORITY", resultSet.getString("SSL_PRIORITY"));
                            subJson.put("KEY_CERTIFICATE_PASSPHRASE", resultSet.getString("KEY_CERTIFICATE_PASSPHRASE"));
                            subJson.put("CIPHER_STRENGTH", resultSet.getString("CIPHER_STRENGTH"));
                        }
                    }
                }
            }
        } catch (Exception sqe) {
            sqe.printStackTrace();
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
            } catch (SQLException sqle) {
            }
        }
        if (i > 0) {
            return subJson.toString();
        } else {
            return empty;
        }
    }

    public String isExistedAS2PartnerProfileName(String name, String partnerName) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String responseString = "";
        int partnerId = DataSourceDataProvider.getInstance().getTPOPartnerId(partnerName);
        try {
            queryString = "SELECT MY_ORG FROM TPO_AS2 where (REPLACE(UPPER(MY_ORG),' ','')=REPLACE(UPPER('" + name + "'),' ','')) AND PARTNER_ID != " + partnerId;
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                responseString = "No";
            } else {
                responseString = "Yes";
            }
        } catch (Exception sqe) {
            sqe.printStackTrace();
            responseString = "<font size='2' color='red'>Error!</font>";
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
            } catch (SQLException sqle) {
            }
        }
        return responseString;
    }

    public String doAcceptPartner(String loginId, String assignTo, int partnerId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String responseString = "";
        int count = 0;
        int count1 = 0;
        try {
            queryString = "UPDATE MSCVP.TPO_PARTNERS SET STATUS =?, ASSIGNED_TO =?, MODIFIED_BY =?, MODIFIED_TS =? WHERE ID=?";
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            statement.setString(1, "ACTIVE");
            statement.setString(2, assignTo);
            statement.setString(3, loginId);
            statement.setTimestamp(4, DateUtility.getInstance().getCurrentDB2Timestamp());
            statement.setInt(5, partnerId);
            count = statement.executeUpdate();
            if (count > 0) {
                queryString = "UPDATE MSCVP.TPO_USER SET ACTIVE =?, MODIFIED_BY = ?, MODIFIED_TS =? WHERE PARTNER_ID=?";
                connection = ConnectionProvider.getInstance().getConnection();
                statement = connection.prepareStatement(queryString);
                statement.setString(1, "A");
                statement.setString(2, loginId);
                statement.setTimestamp(3, DateUtility.getInstance().getCurrentDB2Timestamp());
                statement.setInt(4, partnerId);
                count1 = statement.executeUpdate();
            }
            if (count > 0 && count1 > 0) {
                queryString = "SELECT TPO_USER.NAME AS contactName,TPO_USER.EMAIL,TPO_USER.LOGINID,TPO_USER.PASSWORD,TPO_PARTNERS.NAME AS partnerName FROM TPO_USER JOIN TPO_PARTNERS ON (TPO_USER.PARTNER_ID=TPO_PARTNERS.ID) WHERE TPO_USER.PARTNER_ID=" + partnerId;
                connection = ConnectionProvider.getInstance().getConnection();
                statement = connection.prepareStatement(queryString);
                resultSet = statement.executeQuery();
                resultSet.next();
                responseString = "<font size='2' color='green'>Partner accepted successfully</font>";
                MailManager.tpoAcceptOrRejectPartner("acceptPartner", resultSet.getString("contactName"), resultSet.getString("partnerName"), resultSet.getString("EMAIL"), resultSet.getString("LOGINID"), PasswordUtil.decryptPwd(resultSet.getString("PASSWORD")));
            } else {
                responseString = "<font size='2' color='red'>Partner accept failed</font>";
            }

        } catch (Exception sqe) {
            sqe.printStackTrace();
            responseString = "<font size='2' color='red'>Please try again!</font>";
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
            } catch (SQLException sqle) {
            }
        }
        return responseString;
    }

    public String doRejectPartner(String loginId, int partnerId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String responseString = "";
        int count = 0;
        try {
            queryString = "UPDATE MSCVP.TPO_PARTNERS SET STATUS =?, MODIFIED_BY =?, MODIFIED_TS =? WHERE ID=?";
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            statement.setString(1, "REJECTED");
            statement.setString(2, loginId);
            statement.setTimestamp(3, DateUtility.getInstance().getCurrentDB2Timestamp());
            statement.setInt(4, partnerId);
            count = statement.executeUpdate();
            if (count > 0) {
                queryString = "SELECT TPO_USER.NAME AS contactName,TPO_USER.EMAIL,TPO_USER.LOGINID,TPO_USER.PASSWORD,TPO_PARTNERS.NAME AS partnerName FROM TPO_USER JOIN TPO_PARTNERS ON (TPO_USER.PARTNER_ID=TPO_PARTNERS.ID) WHERE TPO_USER.PARTNER_ID=" + partnerId;
                connection = ConnectionProvider.getInstance().getConnection();
                statement = connection.prepareStatement(queryString);
                resultSet = statement.executeQuery();
                resultSet.next();
                responseString = "<font size='2' color='green'>Partner rejected successfully</font>";
                MailManager.tpoAcceptOrRejectPartner("rejectPartner", resultSet.getString("contactName"), resultSet.getString("partnerName"), resultSet.getString("EMAIL"), resultSet.getString("LOGINID"), PasswordUtil.decryptPwd(resultSet.getString("PASSWORD")));
            } else {
                responseString = "<font size='2' color='red'>Partner reject failed</font>";
            }
        } catch (Exception sqe) {
            sqe.printStackTrace();
            responseString = "<font size='2' color='red'>Please try again!</font>";
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
            } catch (SQLException sqle) {
            }
        }
        return responseString;
    }

    /**
     * This method is used to get the Consultant Resumes
     *
     * @param consultantId
     * @return String
     * @throws com.mss.mirage.util.ServiceLocatorException
     */
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
}
