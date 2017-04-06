/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.admin;

import com.mss.tpo.util.ConnectionProvider;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.DateUtility;
import com.mss.tpo.util.MailManager;
import com.mss.tpo.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Narendar
 */
public class AdminServiceImpl implements AdminService {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    String responseString = null;
    Map mp;
    AdminBean adminBean = null;
    private ArrayList<AdminBean> tpoSearchProfileList;
    private ArrayList<AdminBean> tpoManageCommunicationList;

    public ArrayList<AdminBean> tpoAdminSearchProfile(String loginId, int roleId, int partnerId, String flag, AdminAction adminAction) {
        StringBuffer profileSearchQuery = new StringBuffer();
        profileSearchQuery.append("SELECT ID, PROTOCOL, TRANSFER_MODE, STATUS, CREATED_BY, CREATED_TS FROM MSCVP.TPO_COMMUNICATION WHERE 1=1 ");
        if ("searchFlag".equals(flag)) {
            if (adminAction.getCommnProtocol() != null && !"-1".equals(adminAction.getCommnProtocol().trim())) {
                profileSearchQuery.append(" AND PROTOCOL='" + adminAction.getCommnProtocol() + "' ");
            }
            if (adminAction.getStatus() != null && !"-1".equals(adminAction.getStatus().trim())) {
                profileSearchQuery.append(" AND STATUS='" + adminAction.getStatus() + "' ");
            }
        }
        if (roleId == 1) {
            profileSearchQuery.append(" AND PARTNER_ID=" + partnerId + " AND ADMIN_PROTOCOL_FLAG = 'Y' ");
        } else {
            profileSearchQuery.append(" AND PARTNER_ID=" + partnerId + " AND CREATED_BY = '" + loginId + "' AND ADMIN_PROTOCOL_FLAG = 'Y' ");
        }
        profileSearchQuery.append(" order By CREATED_TS DESC");
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(profileSearchQuery.toString());
            tpoSearchProfileList = new ArrayList<AdminBean>();

            while (resultSet.next()) {
                AdminBean adminBean = new AdminBean();
                adminBean.setId(resultSet.getInt("ID"));
                adminBean.setCommnProtocol(resultSet.getString("PROTOCOL"));
                if (("FTP".equalsIgnoreCase((resultSet.getString("PROTOCOL")))) || ("HTTP".equalsIgnoreCase((resultSet.getString("PROTOCOL")))) || ("SFTP".equalsIgnoreCase((resultSet.getString("PROTOCOL"))))) {
                    adminBean.setTransferMode(resultSet.getString("TRANSFER_MODE"));
                } else {
                    adminBean.setTransferMode("--");
                }
                adminBean.setStatus(resultSet.getString("STATUS"));
                adminBean.setCreated_by(resultSet.getString("CREATED_BY"));
                adminBean.setCreated_ts(resultSet.getTimestamp("CREATED_TS"));
                tpoSearchProfileList.add(adminBean);

            }
        } catch (Exception ex) {
            Logger.getLogger(AdminServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tpoSearchProfileList;
    }

    public String addAdminProfile(int partnerId, String partnerName, String loginId, String Email, AdminAction adminAction) throws ServiceLocatorException {
        int isProfileCommnInserted = 0;
        int isProtocolInserted = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        try {
            int communicationId = 0;
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            String addTpoProfileQuery = "INSERT INTO MSCVP.TPO_COMMUNICATION(PARTNER_ID, PARTNER_NAME, PROTOCOL, TRANSFER_MODE, TP_FLAG, CREATED_BY, CREATED_TS, STATUS, ADMIN_PROTOCOL_FLAG) "
                    + "VALUES(?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(addTpoProfileQuery);
            preparedStatement.setInt(1, partnerId);
            preparedStatement.setString(2, partnerName);
            preparedStatement.setString(3, adminAction.getCommnProtocol());
            if (("FTP".equalsIgnoreCase(adminAction.getCommnProtocol())) || ("SFTP".equalsIgnoreCase(adminAction.getCommnProtocol()))) {
                preparedStatement.setString(4, "pull");
            } else {
                preparedStatement.setString(4, "push");
            }
            preparedStatement.setString(5, "N");
            preparedStatement.setString(6, adminAction.getCreated_by());
            preparedStatement.setTimestamp(7, curdate);
            preparedStatement.setString(8, "INACTIVE");
            preparedStatement.setString(9, "Y");
            isProfileCommnInserted = isProfileCommnInserted + preparedStatement.executeUpdate();

            String commnIdQuery = "SELECT max(ID) AS communicationId FROM MSCVP.TPO_COMMUNICATION WHERE PARTNER_ID =" + partnerId + " AND CREATED_BY='" + adminAction.getCreated_by() + "' ";
            resultSet = statement.executeQuery(commnIdQuery);
            if (resultSet.next()) {
                communicationId = resultSet.getInt("communicationId");
            }

            if ((isProfileCommnInserted > 0) && (adminAction.getCommnProtocol().equals("FTP"))) {
                String ftpInsertQuery = "INSERT INTO MSCVP.TPO_FTP(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, FTP_METHOD, RECEIVING_PROTOCOL,"
                        + " FTP_HOST, FTP_PORT, FTP_USER_ID, FTP_PASSWORD, FTP_DIRECTORY, CONNECTION_METHOD, RESPONSE_TIMEOUT_SEC, TP_FLAG, CREATED_BY,"
                        + " CREATED_TS, STATUS, ADMIN_PROTOCOL_FLAG) VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(ftpInsertQuery);
                preparedStatement.setInt(1, communicationId);
                preparedStatement.setInt(2, partnerId);
                preparedStatement.setString(3, "pull");
                preparedStatement.setString(4, adminAction.getFtp_ssl_req());
                preparedStatement.setString(5, adminAction.getFtp_method());
                preparedStatement.setString(6, adminAction.getFtp_recv_protocol());
                preparedStatement.setString(7, adminAction.getFtp_host());
                preparedStatement.setString(8, adminAction.getFtp_port());
                preparedStatement.setString(9, adminAction.getFtp_userId());
                preparedStatement.setString(10, adminAction.getFtp_pwd());
                preparedStatement.setString(11, adminAction.getFtp_directory());
                preparedStatement.setString(12, adminAction.getFtp_conn_method());
                preparedStatement.setInt(13, adminAction.getFtp_resp_time());
                preparedStatement.setString(14, "N");
                preparedStatement.setString(15, adminAction.getCreated_by());
                preparedStatement.setTimestamp(16, curdate);
                preparedStatement.setString(17, "INACTIVE");
                preparedStatement.setString(18, "Y");
                isProtocolInserted = isProtocolInserted + preparedStatement.executeUpdate();
                if ((isProtocolInserted > 0) && ("true".equalsIgnoreCase(adminAction.getFtp_ssl_req()))) {
                    String insertQuery = "INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,"
                            + " CIPHER_STRENGTH, TP_FLAG, CREATED_BY, CREATED_TS, CA_CERTIFICATES,SSL_CERT_DATA, ADMIN_PROTOCOL_FLAG) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, communicationId);
                    preparedStatement.setInt(2, partnerId);
                    preparedStatement.setString(3, "pull");
                    preparedStatement.setString(4, adminAction.getCommnProtocol());
                    preparedStatement.setString(5, adminAction.getSsl_priority());
                    preparedStatement.setString(6, adminAction.getSsl_cipher_stergth());
                    preparedStatement.setString(7, "N");
                    preparedStatement.setString(8, adminAction.getCreated_by());
                    preparedStatement.setTimestamp(9, curdate);
                    preparedStatement.setString(10, adminAction.getCertGroups());
                    if (!"".equalsIgnoreCase(adminAction.getCertGroups())) {
                        preparedStatement.setString(11, DataSourceDataProvider.getInstance().getCertificatePath(adminAction.getCertGroups()));
                    } else {
                        preparedStatement.setString(11, "");
                    }
                    preparedStatement.setString(12, "Y");
                    preparedStatement.executeUpdate();
                }
            } else if ((isProfileCommnInserted > 0) && (adminAction.getCommnProtocol().equals("HTTP"))) {
                String httpInsertQuery = "INSERT INTO MSCVP.TPO_HTTP(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, "
                        + "RECEIVING_PROTOCOL, HTTP_END_POINT, HTTP_MODE, RESPONSE_TIMEOUT_SEC, HTTP_PORT, TP_FLAG, "
                        + "CREATED_BY, CREATED_TS, URL, STATUS, ADMIN_PROTOCOL_FLAG) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(httpInsertQuery);
                preparedStatement.setInt(1, communicationId);
                preparedStatement.setInt(2, partnerId);
                preparedStatement.setString(3, "push");
                preparedStatement.setString(4, adminAction.getHttp_ssl_req());
                preparedStatement.setString(5, adminAction.getHttp_recv_protocol());
                preparedStatement.setString(6, adminAction.getHttp_endpoint());
                preparedStatement.setString(7, adminAction.getHttp_protocol_mode());
                preparedStatement.setInt(8, Integer.parseInt(adminAction.getHttp_resp_time()));
                preparedStatement.setInt(9, Integer.parseInt(adminAction.getHttp_port()));
                preparedStatement.setString(10, "N");
                preparedStatement.setString(11, adminAction.getCreated_by());
                preparedStatement.setTimestamp(12, curdate);
                preparedStatement.setString(13, adminAction.getHttp_url());
                preparedStatement.setString(14, "INACTIVE");
                preparedStatement.setString(15, "Y");
                isProtocolInserted = isProtocolInserted + preparedStatement.executeUpdate();
                if ((isProtocolInserted > 0) && ("true".equalsIgnoreCase(adminAction.getHttp_ssl_req()))) {
                    String insertQuery = "INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,"
                            + " CIPHER_STRENGTH, CA_CERTIFICATES, TP_FLAG, CREATED_BY, CREATED_TS,SSL_CERT_DATA, ADMIN_PROTOCOL_FLAG) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, communicationId);
                    preparedStatement.setInt(2, partnerId);
                    preparedStatement.setString(3, "push");
                    preparedStatement.setString(4, adminAction.getCommnProtocol());
                    preparedStatement.setString(5, adminAction.getSsl_priority());
                    preparedStatement.setString(6, adminAction.getSsl_cipher_stergth());
                    preparedStatement.setString(7, adminAction.getCertGroups());
                    preparedStatement.setString(8, "N");
                    preparedStatement.setString(9, adminAction.getCreated_by());
                    preparedStatement.setTimestamp(10, curdate);
                    if (!"".equalsIgnoreCase(adminAction.getCertGroups())) {
                        preparedStatement.setString(11, DataSourceDataProvider.getInstance().getCertificatePath(adminAction.getCertGroups()));
                    } else {
                        preparedStatement.setString(11, "");
                    }
                    preparedStatement.setString(12, "Y");
                    preparedStatement.executeUpdate();
                }
            } else if ((isProfileCommnInserted > 0) && (adminAction.getCommnProtocol().equals("SFTP"))) {
                String sftpInsertQuery = "INSERT INTO MSCVP.TPO_SFTP(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, "
                        + "CONN_METHOD, MY_SSH_PUB_KEY, UPL_YOUR_SSH_PUB_KEY, REMOTE_HOST_IP_ADD, REMOTE_USERID, "
                        + "METHOD, AUTH_METHOD, REMOTE_PORT, REMOTE_PWD, DIRECTORY, TP_FLAG, CREATED_BY, "
                        + "CREATED_TS, STATUS,SSH_CERT_DATA, ADMIN_PROTOCOL_FLAG)"
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sftpInsertQuery);
                preparedStatement.setInt(1, communicationId);
                preparedStatement.setInt(2, partnerId);
                preparedStatement.setString(3, "pull");
                preparedStatement.setString(4, adminAction.getSftp_conn_method());
                preparedStatement.setString(5, adminAction.getSftp_public_key());
                preparedStatement.setString(6, adminAction.getFilepath());
                preparedStatement.setString(7, adminAction.getSftp_host_ip());
                preparedStatement.setString(8, adminAction.getSftp_remote_userId());
                preparedStatement.setString(9, adminAction.getSftp_method());
                preparedStatement.setString(10, adminAction.getSftp_auth_method());
                preparedStatement.setString(11, adminAction.getSftp_remote_port());
                preparedStatement.setString(12, adminAction.getSftp_remote_pwd());
                preparedStatement.setString(13, adminAction.getSftp_directory());
                preparedStatement.setString(14, "N");
                preparedStatement.setString(15, adminAction.getCreated_by());
                preparedStatement.setTimestamp(16, curdate);
                preparedStatement.setString(17, "INACTIVE");
                if (!"".equalsIgnoreCase(adminAction.getFilepath())) {
                    preparedStatement.setString(18, DataSourceDataProvider.getInstance().getCertificatePath(adminAction.getFilepath()));
                } else {
                    preparedStatement.setString(18, "");
                }
                preparedStatement.setString(19, "Y");
                isProtocolInserted = isProtocolInserted + preparedStatement.executeUpdate();
            }
            mp = adminAction.getMap();
            if ((isProfileCommnInserted > 0) && (isProtocolInserted > 0)) {
                responseString = "<font color='green'>Inserted successfully</font>";
                MailManager.tpoDetails(partnerId, loginId, Email, adminAction.getPartnerName(), adminAction.getCommnProtocol(), adminAction.getTransferMode(),
                        adminAction.getFilepath(), adminAction.getCertGroups(), mp, "Insert");
            } else {
                responseString = "<font color='red'>Please try again!</font>";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public AdminAction tpogetAdminProfile(int Id, String commonprotocol, AdminAction adminAction) {
        try {
            String profileInfoQuery = null;
            String sslprofileInfoQuery = null;
            int partnerId = 0;
            connection = ConnectionProvider.getInstance().getConnection();
            if (commonprotocol.equalsIgnoreCase("FTP")) {
                profileInfoQuery = "select TPO_FTP.COMMUNICATION_ID,TPO_FTP.PARTNER_ID,TPO_FTP.TRANSFER_MODE,TPO_FTP.FTP_METHOD, "
                        + "TPO_FTP.RECEIVING_PROTOCOL,TPO_FTP.FTP_HOST,TPO_FTP.FTP_PORT,TPO_FTP.FTP_USER_ID,TPO_FTP.FTP_PASSWORD,TPO_FTP.FTP_DIRECTORY,"
                        + "TPO_FTP.CONNECTION_METHOD,TPO_FTP.RESPONSE_TIMEOUT_SEC,TPO_COMMUNICATION.PROTOCOL,TPO_FTP.SSL_FLAG"
                        + " from TPO_FTP join TPO_COMMUNICATION on (TPO_FTP.COMMUNICATION_ID = TPO_COMMUNICATION.ID) where TPO_FTP.COMMUNICATION_ID =" + Id + " "
                        + "and TPO_COMMUNICATION.PROTOCOL='" + commonprotocol + "' AND TPO_FTP.ADMIN_PROTOCOL_FLAG = 'Y'";
                preparedStatement = connection.prepareStatement(profileInfoQuery);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int communicationid = resultSet.getInt("COMMUNICATION_ID");
                    adminAction.setCommunicationId(communicationid);
                    adminAction.setId(resultSet.getInt("PARTNER_ID"));
                    String transfermode = resultSet.getString("TRANSFER_MODE");
                    adminAction.setTransferMode(transfermode);
                    adminAction.setFtp_method(resultSet.getString("FTP_METHOD"));
                    adminAction.setFtp_recv_protocol(resultSet.getString("RECEIVING_PROTOCOL"));
                    adminAction.setFtp_host(resultSet.getString("FTP_HOST"));
                    adminAction.setFtp_port(resultSet.getString("FTP_PORT"));
                    adminAction.setFtp_userId(resultSet.getString("FTP_USER_ID"));
                    adminAction.setFtp_pwd(resultSet.getString("FTP_PASSWORD"));
                    adminAction.setFtp_directory(resultSet.getString("FTP_DIRECTORY"));
                    adminAction.setFtp_conn_method(resultSet.getString("CONNECTION_METHOD"));
                    adminAction.setFtp_resp_time(resultSet.getInt("RESPONSE_TIMEOUT_SEC"));
                    String ssl_flag = resultSet.getString("SSL_FLAG");
                    adminAction.setFtp_ssl_req(ssl_flag);
                    if (ssl_flag.equalsIgnoreCase("true")) {
                        sslprofileInfoQuery = "SELECT SSL_PRIORITY, CIPHER_STRENGTH, CA_CERTIFICATES FROM MSCVP.TPO_SSL where ADMIN_PROTOCOL_FLAG = 'Y' AND COMMUNICATION_ID=" + communicationid;
                        preparedStatement = connection.prepareStatement(sslprofileInfoQuery.toString());
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            adminAction.setSsl_priority(resultSet.getString("SSL_PRIORITY"));
                            adminAction.setSsl_cipher_stergth(resultSet.getString("CIPHER_STRENGTH"));
                            adminAction.setCertGroups(resultSet.getString("CA_CERTIFICATES"));
                        }
                    }
                    adminAction.setCommnProtocol(commonprotocol);
                }
            } else if (commonprotocol.equalsIgnoreCase("HTTP")) {
                profileInfoQuery = "SELECT TPO_HTTP.COMMUNICATION_ID,TPO_HTTP.PARTNER_ID,TPO_HTTP.TRANSFER_MODE,TPO_HTTP.SSL_FLAG,"
                        + "TPO_HTTP.RECEIVING_PROTOCOL,TPO_HTTP.HTTP_END_POINT,TPO_HTTP.HTTP_MODE,"
                        + "TPO_HTTP.RESPONSE_TIMEOUT_SEC,TPO_HTTP.HTTP_PORT,TPO_HTTP.URL "
                        + "FROM MSCVP.TPO_HTTP join TPO_COMMUNICATION on (TPO_HTTP.COMMUNICATION_ID = TPO_COMMUNICATION.ID)"
                        + " where TPO_HTTP.COMMUNICATION_ID =" + Id + " and TPO_COMMUNICATION.PROTOCOL='" + commonprotocol + "' AND TPO_HTTP.ADMIN_PROTOCOL_FLAG = 'Y'";
                preparedStatement = connection.prepareStatement(profileInfoQuery);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int communicationid = resultSet.getInt("COMMUNICATION_ID");
                    adminAction.setCommunicationId(communicationid);
                    adminAction.setId(resultSet.getInt("PARTNER_ID"));
                    String transfermode = resultSet.getString("TRANSFER_MODE");
                    adminAction.setTransferMode(transfermode);
                    adminAction.setHttp_recv_protocol(resultSet.getString("RECEIVING_PROTOCOL"));
                    adminAction.setHttp_endpoint(resultSet.getString("HTTP_END_POINT"));
                    adminAction.setHttp_protocol_mode(resultSet.getString("HTTP_MODE"));
                    adminAction.setHttp_resp_time(resultSet.getString("RESPONSE_TIMEOUT_SEC"));
                    adminAction.setHttp_port(resultSet.getString("HTTP_PORT"));
                    adminAction.setHttp_url(resultSet.getString("URL"));
                    String ssl_flag = resultSet.getString("SSL_FLAG");
                    adminAction.setHttp_ssl_req(ssl_flag);
                    if (ssl_flag.equalsIgnoreCase("true")) {
                        sslprofileInfoQuery = "SELECT SSL_PRIORITY, CIPHER_STRENGTH, CA_CERTIFICATES FROM MSCVP.TPO_SSL where ADMIN_PROTOCOL_FLAG = 'Y' AND COMMUNICATION_ID=" + communicationid;
                        preparedStatement = connection.prepareStatement(sslprofileInfoQuery.toString());
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            adminAction.setSsl_priority(resultSet.getString("SSL_PRIORITY"));
                            adminAction.setSsl_cipher_stergth(resultSet.getString("CIPHER_STRENGTH"));
                            adminAction.setCertGroups(resultSet.getString("CA_CERTIFICATES"));
                        }
                    }
                }
            } else if (commonprotocol.equalsIgnoreCase("SFTP")) {
                profileInfoQuery = "SELECT TPO_SFTP.COMMUNICATION_ID,TPO_SFTP.PARTNER_ID,TPO_SFTP.TRANSFER_MODE,"
                        + "TPO_SFTP.CONN_METHOD,TPO_SFTP.UPL_YOUR_SSH_PUB_KEY,TPO_SFTP.REMOTE_HOST_IP_ADD,"
                        + "TPO_SFTP.REMOTE_USERID,TPO_SFTP.METHOD,TPO_SFTP.AUTH_METHOD,TPO_SFTP.REMOTE_PORT,TPO_SFTP.REMOTE_PWD,"
                        + "TPO_SFTP.DIRECTORY FROM MSCVP.TPO_SFTP join TPO_COMMUNICATION on (TPO_SFTP.COMMUNICATION_ID = TPO_COMMUNICATION.ID) "
                        + "where TPO_SFTP.COMMUNICATION_ID = " + Id + " and TPO_COMMUNICATION.PROTOCOL='" + commonprotocol + "' AND TPO_SFTP.ADMIN_PROTOCOL_FLAG = 'Y'";
                preparedStatement = connection.prepareStatement(profileInfoQuery);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int communicationid = resultSet.getInt("COMMUNICATION_ID");
                    adminAction.setCommunicationId(communicationid);
                    adminAction.setId(resultSet.getInt("PARTNER_ID"));
                    adminAction.setTransferMode(resultSet.getString("TRANSFER_MODE"));
                    adminAction.setSftp_conn_method(resultSet.getString("CONN_METHOD"));
                    adminAction.setSftp_upload_public_key(resultSet.getString("UPL_YOUR_SSH_PUB_KEY"));
                    adminAction.setSftp_host_ip(resultSet.getString("REMOTE_HOST_IP_ADD"));
                    adminAction.setSftp_remote_userId(resultSet.getString("REMOTE_USERID"));
                    adminAction.setSftp_method(resultSet.getString("METHOD"));
                    adminAction.setSftp_auth_method(resultSet.getString("AUTH_METHOD"));
                    adminAction.setSftp_remote_port(resultSet.getString("REMOTE_PORT"));
                    adminAction.setSftp_remote_pwd(resultSet.getString("REMOTE_PWD"));
                    adminAction.setSftp_directory(resultSet.getString("DIRECTORY"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

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
            } catch (Exception sqle) {
                sqle.printStackTrace();

            }
        }
        return adminAction;
    }

    public String tpoUpdateAdminProfile(int partnerId, int communicationId, String commonprotocol, String Email, AdminAction adminAction) {
        int isCommunicationUpdated = 0;
        int isProtocolUpdated = 0;
        try {
            Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
            connection = ConnectionProvider.getInstance().getConnection();

            String communicationQuery = "UPDATE MSCVP.TPO_COMMUNICATION SET TP_FLAG = ?, MODIFIED_BY = ?, MODIFIED_TS = ?, STATUS = ? WHERE ID=" + communicationId + "";
            preparedStatement = connection.prepareStatement(communicationQuery);
            if (commonprotocol.equalsIgnoreCase("FTP") && adminAction.getTransferMode().equals("pull")) {
                if (("true".equalsIgnoreCase(adminAction.getFtp_ssl_req())) && (adminAction.getUpload1() != null)) {
                    preparedStatement.setString(1, "N");
                } else {
                    preparedStatement.setString(1, "U");
                }
            } else if (commonprotocol.equalsIgnoreCase("HTTP") && adminAction.getTransferMode().equals("push")) {
                if ((adminAction.getUpload1() != null) && (adminAction.getHttp_ssl_req().equalsIgnoreCase("true"))) {
                    preparedStatement.setString(1, "N");
                } else {
                    preparedStatement.setString(1, "U");
                }
            } else if (commonprotocol.equalsIgnoreCase("SFTP") && adminAction.getTransferMode().equals("pull")) {
                if (adminAction.getUpload() != null) {
                    preparedStatement.setString(1, "N");
                } else {
                    preparedStatement.setString(1, "U");
                }
            }
            preparedStatement.setString(2, adminAction.getCreated_by());
            preparedStatement.setTimestamp(3, curdate);
            preparedStatement.setString(4, "INACTIVE");
            isCommunicationUpdated = isCommunicationUpdated + preparedStatement.executeUpdate();

            if (isCommunicationUpdated > 0) {
                if (commonprotocol.equalsIgnoreCase("FTP") && adminAction.getTransferMode().equals("pull")) {
                    String ftpUpdateQuery = "UPDATE MSCVP.TPO_FTP SET FTP_METHOD = ?, FTP_HOST = ?, FTP_PORT = ?, "
                            + "FTP_USER_ID = ?, FTP_PASSWORD = ?, FTP_DIRECTORY = ?, CONNECTION_METHOD = ?, RESPONSE_TIMEOUT_SEC = ?,"
                            + " SSL_FLAG = ?, TP_FLAG = ?, MODIFIED_BY = ?, MODIFIED_TS = ?, STATUS = ?,RECEIVING_PROTOCOL=? WHERE COMMUNICATION_ID=" + communicationId;
                    preparedStatement = connection.prepareStatement(ftpUpdateQuery);
                    preparedStatement.setString(1, adminAction.getFtp_method());
                    preparedStatement.setString(2, adminAction.getFtp_host());
                    preparedStatement.setString(3, adminAction.getFtp_port());
                    preparedStatement.setString(4, adminAction.getFtp_userId());
                    preparedStatement.setString(5, adminAction.getFtp_pwd());
                    preparedStatement.setString(6, adminAction.getFtp_directory());
                    preparedStatement.setString(7, adminAction.getFtp_conn_method());
                    preparedStatement.setInt(8, adminAction.getFtp_resp_time());
                    preparedStatement.setString(9, adminAction.getFtp_ssl_req());
                    if (("true".equalsIgnoreCase(adminAction.getFtp_ssl_req())) && (adminAction.getUpload1() != null)) {
                        preparedStatement.setString(10, "N");
                    } else {
                        preparedStatement.setString(10, "U");
                    }
                    preparedStatement.setString(11, adminAction.getCreated_by());
                    preparedStatement.setTimestamp(12, curdate);
                    preparedStatement.setString(13, "INACTIVE");
                    preparedStatement.setString(14, adminAction.getFtp_recv_protocol());
                    isProtocolUpdated = isProtocolUpdated + preparedStatement.executeUpdate();
                    if ((isProtocolUpdated > 0) && ("true".equalsIgnoreCase(adminAction.getFtp_ssl_req()))) {
                        String selectQuery = "SELECT count(ID)  FROM MSCVP.TPO_SSL where COMMUNICATION_ID=? and PROTOCOL=?";
                        preparedStatement = connection.prepareStatement(selectQuery);
                        preparedStatement.setInt(1, communicationId);
                        preparedStatement.setString(2, commonprotocol);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            StringBuffer ftpSslUpdateQuery = new StringBuffer();
                            if (resultSet.getInt(1) > 0) {
                                ftpSslUpdateQuery.append("Update MSCVP.TPO_SSL set SSL_PRIORITY=?,CIPHER_STRENGTH=?, TP_FLAG = ?,  MODIFIED_BY=?, MODIFIED_TS=? ");
                                if (adminAction.getCertGroups() != null) {
                                    ftpSslUpdateQuery.append(",CA_CERTIFICATES=? ,SSL_CERT_DATA=? ");
                                }
                                ftpSslUpdateQuery.append(" WHERE COMMUNICATION_ID= " + communicationId + "");
                                preparedStatement = connection.prepareStatement(ftpSslUpdateQuery.toString());
                                preparedStatement.setString(1, adminAction.getSsl_priority());
                                preparedStatement.setString(2, adminAction.getSsl_cipher_stergth());
                                if (adminAction.getUpload1() != null) {
                                    preparedStatement.setString(3, "N");

                                } else {
                                    preparedStatement.setString(3, "U");
                                }
                                preparedStatement.setString(4, adminAction.getCreated_by());
                                preparedStatement.setTimestamp(5, curdate);
                                if (adminAction.getCertGroups() != null) {
                                    preparedStatement.setString(6, adminAction.getCertGroups());
                                    preparedStatement.setString(7, DataSourceDataProvider.getInstance().getCertificatePath(adminAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();
                            } else {
                                ftpSslUpdateQuery.append("INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, ADMIN_PROTOCOL_FLAG,TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,CIPHER_STRENGTH, TP_FLAG, CREATED_BY, CREATED_TS ");
                                if (adminAction.getCertGroups() != null) {
                                    ftpSslUpdateQuery.append(" , CA_CERTIFICATES ,SSL_CERT_DATA ");
                                }
                                ftpSslUpdateQuery.append("  ) VALUES(?,?,?,?,?,?,?,?,?,? ");
                                if (adminAction.getCertGroups() != null) {
                                    ftpSslUpdateQuery.append("  ,?,?) ");
                                } else {
                                    ftpSslUpdateQuery.append("  ) ");
                                }

                                preparedStatement = connection.prepareStatement(ftpSslUpdateQuery.toString());
                                preparedStatement.setInt(1, communicationId);
                                preparedStatement.setInt(2, partnerId);
                                preparedStatement.setString(3, "Y");
                                preparedStatement.setString(4, adminAction.getTransferMode());
                                preparedStatement.setString(5, adminAction.getCommnProtocol());
                                preparedStatement.setString(6, adminAction.getSsl_priority());
                                preparedStatement.setString(7, adminAction.getSsl_cipher_stergth());
                                preparedStatement.setString(8, "N");
                                preparedStatement.setString(9, adminAction.getCreated_by());
                                preparedStatement.setTimestamp(10, curdate);
                                if (adminAction.getCertGroups() != null) {
                                    preparedStatement.setString(11, adminAction.getCertGroups());
                                    preparedStatement.setString(12, DataSourceDataProvider.getInstance().getCertificatePath(adminAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();
                            }
                        }
                    }
                } else if (commonprotocol.equalsIgnoreCase("HTTP") && adminAction.getTransferMode().equals("push")) {
                    String httpUpdateQuery = "UPDATE MSCVP.TPO_HTTP SET HTTP_END_POINT = ?,HTTP_MODE = ?,"
                            + "RESPONSE_TIMEOUT_SEC = ?,HTTP_PORT = ?,MODIFIED_BY = ?,MODIFIED_TS =?,SSL_FLAG=?, URL=? ,"
                            + "TP_FLAG = ?, STATUS = ?,RECEIVING_PROTOCOL=? WHERE COMMUNICATION_ID=" + communicationId;
                    preparedStatement = connection.prepareStatement(httpUpdateQuery);
                    preparedStatement.setString(1, adminAction.getHttp_endpoint());
                    preparedStatement.setString(2, adminAction.getHttp_protocol_mode());
                    preparedStatement.setInt(3, Integer.parseInt(adminAction.getHttp_resp_time()));
                    preparedStatement.setInt(4, Integer.parseInt(adminAction.getHttp_port()));
                    preparedStatement.setString(5, adminAction.getCreated_by());
                    preparedStatement.setTimestamp(6, curdate);
                    preparedStatement.setString(7, adminAction.getHttp_ssl_req());
                    preparedStatement.setString(8, adminAction.getHttp_url());
                    if ((adminAction.getUpload1() != null) && (adminAction.getHttp_ssl_req().equalsIgnoreCase("true"))) {
                        preparedStatement.setString(9, "N");
                    } else {
                        preparedStatement.setString(9, "U");
                    }
                    preparedStatement.setString(10, "INACTIVE");
                    preparedStatement.setString(11, adminAction.getHttp_recv_protocol());
                    isProtocolUpdated = isProtocolUpdated + preparedStatement.executeUpdate();
                    if ((isProtocolUpdated > 0) && adminAction.getHttp_ssl_req().equalsIgnoreCase("true")) {
                        String selectQuery = "SELECT count(ID)  FROM MSCVP.TPO_SSL where COMMUNICATION_ID=? and PROTOCOL=?";
                        preparedStatement = connection.prepareStatement(selectQuery);
                        preparedStatement.setInt(1, communicationId);
                        preparedStatement.setString(2, commonprotocol);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            if (resultSet.getInt(1) > 0) {
                                StringBuffer httpSslUpdateQuery = new StringBuffer();
                                httpSslUpdateQuery.append("Update MSCVP.TPO_SSL set SSL_PRIORITY=?, CIPHER_STRENGTH=?, ");
                                httpSslUpdateQuery.append(" TP_FLAG = ?, MODIFIED_BY=?, MODIFIED_TS=? ");
                                if (adminAction.getCertGroups() != null) {
                                    httpSslUpdateQuery.append(" ,CA_CERTIFICATES=?,SSL_CERT_DATA=?");
                                }
                                httpSslUpdateQuery.append(" WHERE COMMUNICATION_ID=" + communicationId);
                                preparedStatement = connection.prepareStatement(httpSslUpdateQuery.toString());
                                preparedStatement.setString(1, adminAction.getSsl_priority());
                                preparedStatement.setString(2, adminAction.getSsl_cipher_stergth());
                                if (adminAction.getUpload1() != null) {
                                    preparedStatement.setString(3, "N");

                                } else {
                                    preparedStatement.setString(3, "U");
                                }
                                preparedStatement.setString(4, adminAction.getCreated_by());
                                preparedStatement.setTimestamp(5, curdate);
                                if (adminAction.getCertGroups() != null) {
                                    preparedStatement.setString(6, adminAction.getCertGroups());
                                    preparedStatement.setString(7, DataSourceDataProvider.getInstance().getCertificatePath(adminAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();
                            } else {

                                StringBuffer httpSslUpdateQuery = new StringBuffer();
                                httpSslUpdateQuery.append("INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, ADMIN_PROTOCOL_FLAG, TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,");
                                httpSslUpdateQuery.append(" CIPHER_STRENGTH, TP_FLAG, CREATED_BY, CREATED_TS ");
                                if (adminAction.getCertGroups() != null) {
                                    httpSslUpdateQuery.append(" , CA_CERTIFICATES,SSL_CERT_DATA ");
                                }
                                httpSslUpdateQuery.append("  ) VALUES(?,?,?,?,?,?,?,?,?,? ");
                                if (adminAction.getCertGroups() != null) {
                                    httpSslUpdateQuery.append("  ,?,?) ");
                                } else {
                                    httpSslUpdateQuery.append("  ) ");
                                }
                                preparedStatement = connection.prepareStatement(httpSslUpdateQuery.toString());
                                preparedStatement.setInt(1, communicationId);
                                preparedStatement.setInt(2, partnerId);
                                preparedStatement.setString(3, "Y");
                                preparedStatement.setString(4, adminAction.getTransferMode());
                                preparedStatement.setString(5, adminAction.getCommnProtocol());
                                preparedStatement.setString(6, adminAction.getSsl_priority());
                                preparedStatement.setString(7, adminAction.getSsl_cipher_stergth());
                                preparedStatement.setString(8, "N");
                                preparedStatement.setString(9, adminAction.getCreated_by());
                                preparedStatement.setTimestamp(10, curdate);
                                if (adminAction.getCertGroups() != null) {
                                    preparedStatement.setString(11, adminAction.getCertGroups());
                                    preparedStatement.setString(12, DataSourceDataProvider.getInstance().getCertificatePath(adminAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();
                            }
                        }
                    }
                } else if (commonprotocol.equalsIgnoreCase("SFTP") && adminAction.getTransferMode().equals("pull")) {
                    StringBuffer sftpUpdateQuery = new StringBuffer();
                    sftpUpdateQuery.append("UPDATE MSCVP.TPO_SFTP SET CONN_METHOD = ?,MY_SSH_PUB_KEY = ?,REMOTE_HOST_IP_ADD = ?,REMOTE_USERID = ?");
                    sftpUpdateQuery.append(",METHOD = ?,AUTH_METHOD = ?,REMOTE_PORT = ?,REMOTE_PWD = ?,DIRECTORY = ?,MODIFIED_BY = ?,MODIFIED_TS = ?,TP_FLAG = ?, STATUS = ?");
                    if (adminAction.getFilepath() != null) {
                        sftpUpdateQuery.append(" ,UPL_YOUR_SSH_PUB_KEY = ?,SSH_CERT_DATA=? ");
                    }
                    sftpUpdateQuery.append(" WHERE COMMUNICATION_ID=" + communicationId);
                    preparedStatement = connection.prepareStatement(sftpUpdateQuery.toString());
                    preparedStatement.setString(1, adminAction.getSftp_conn_method());
                    preparedStatement.setString(2, adminAction.getSftp_public_key());
                    preparedStatement.setString(3, adminAction.getSftp_host_ip());
                    preparedStatement.setString(4, adminAction.getSftp_remote_userId());
                    preparedStatement.setString(5, adminAction.getSftp_method());
                    preparedStatement.setString(6, adminAction.getSftp_auth_method());
                    preparedStatement.setString(7, adminAction.getSftp_remote_port());
                    preparedStatement.setString(8, adminAction.getSftp_remote_pwd());
                    preparedStatement.setString(9, adminAction.getSftp_directory());
                    preparedStatement.setString(10, adminAction.getCreated_by());
                    preparedStatement.setTimestamp(11, curdate);
                    if (adminAction.getUpload() != null) {
                        preparedStatement.setString(12, "N");

                    } else {
                        preparedStatement.setString(12, "U");
                    }
                    preparedStatement.setString(13, "INACTIVE");
                    if (adminAction.getFilepath() != null) {
                        preparedStatement.setString(14, adminAction.getFilepath());
                        preparedStatement.setString(15, DataSourceDataProvider.getInstance().getCertificatePath(adminAction.getFilepath()));
                    }
                    isProtocolUpdated = isProtocolUpdated + preparedStatement.executeUpdate();
                }
            }
            mp = adminAction.getMap();
            if (((isProtocolUpdated > 0))) {
                responseString = "<font color='green'>Updated successfully</font>";
                //tpOnboardingAction.getCreated_by() is login id..
                MailManager.tpoDetails(partnerId, adminAction.getCreated_by(), Email, adminAction.getPartnerName(), adminAction.getCommnProtocol(), adminAction.getTransferMode(),
                        adminAction.getFilepath(), adminAction.getCertGroups(), mp, "Update");
            } else {
                responseString = "<font color='red'>Please try again!</font>";
            }
        } catch (Exception ex) {
            ex.printStackTrace();

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
            } catch (Exception sqle) {
                sqle.printStackTrace();

            }
        }
        return responseString;
    }

    public String getDeleteAdminProfile(int communicationId, String commnProtocol, int PartnerId, String transferMode) throws ServiceLocatorException {
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            callableStatement = connection.prepareCall("call SP_TPO_PROFILE_DELETE(?,?,?,?)");
            callableStatement.setInt(1, communicationId);
            callableStatement.setString(2, commnProtocol);
            callableStatement.setInt(3, PartnerId);
            callableStatement.setString(4, transferMode);
            int updatedRows = callableStatement.executeUpdate();
            responseString = "<font color='green'>Profile deleted successfully</font>";
        } catch (SQLException e) {
            responseString = "<font color='red'>Please try with different Id!</font>";
            e.printStackTrace();
        } catch (Exception ex) {
            responseString = "<font color='red'>Please try later!</font>";
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

    //Manage communications for assigning
    public ArrayList<AdminBean> getFTPManageCommunicationsList(int roleId, String loginId, int partnerId, String managecommunication) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String queryString = null;
        try {
            if (managecommunication.equalsIgnoreCase("Assign Communication")) {
                if (roleId == 1) {
                    queryString = "SELECT TPO_FTP.COMMUNICATION_ID,TPO_FTP.FTP_HOST,TPO_FTP.FTP_PORT,TPO_FTP.FTP_USER_ID "
                            + " FROM MSCVP.TPO_FTP LEFT OUTER JOIN TPO_PARTNER_COMMUNICATIONS ON (TPO_FTP.COMMUNICATION_ID = TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID) "
                            + " WHERE TPO_PARTNER_COMMUNICATIONS.PROTOCOL= 'FTP' AND TPO_FTP.ADMIN_PROTOCOL_FLAG='Y' and TPO_FTP.TRANSFER_MODE='pull'"
                            + " AND TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID not in (SELECT COMMUNICATION_ID FROM TPO_PARTNER_COMMUNICATIONS where PARTNER_ID = " + partnerId + " )";
                } else {
                    queryString = "SELECT TPO_FTP.COMMUNICATION_ID,TPO_FTP.FTP_HOST,TPO_FTP.FTP_PORT,TPO_FTP.FTP_USER_ID "
                            + " FROM MSCVP.TPO_FTP LEFT OUTER JOIN TPO_PARTNER_COMMUNICATIONS ON (TPO_FTP.COMMUNICATION_ID = TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID) "
                            + " WHERE TPO_PARTNER_COMMUNICATIONS.PROTOCOL= 'FTP' AND TPO_FTP.ADMIN_PROTOCOL_FLAG='Y' and TPO_FTP.TRANSFER_MODE='pull' and TPO_FTP.CREATED_BY='" + loginId + "'"
                            + " AND TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID not in (SELECT COMMUNICATION_ID FROM TPO_PARTNER_COMMUNICATIONS where PARTNER_ID = " + partnerId + " )";
                }
            }
            if (managecommunication.equalsIgnoreCase("Remove Communication")) {
                queryString = "SELECT TPO_FTP.COMMUNICATION_ID,TPO_FTP.FTP_HOST,TPO_FTP.FTP_PORT,TPO_FTP.FTP_USER_ID "
                        + " FROM TPO_FTP LEFT OUTER JOIN TPO_PARTNER_COMMUNICATIONS ON (TPO_FTP.COMMUNICATION_ID = TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID) "
                        + "WHERE TPO_PARTNER_COMMUNICATIONS.PARTNER_ID = " + partnerId;
            }
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            tpoManageCommunicationList = new ArrayList<AdminBean>();

            while (resultSet.next()) {
                AdminBean adminBean = new AdminBean();
                adminBean.setId(resultSet.getInt("COMMUNICATION_ID"));
                adminBean.setFtp_host(resultSet.getString("FTP_HOST"));
                adminBean.setFtp_port(resultSet.getString("FTP_PORT"));
                adminBean.setFtp_userId(resultSet.getString("FTP_USER_ID"));
                tpoManageCommunicationList.add(adminBean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tpoManageCommunicationList;
    }

    public ArrayList<AdminBean> getSFTPManageCommunicationsList(int roleId, String loginId, int partnerId, String managecommunication) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String queryString = null;
        try {
            if (managecommunication.equalsIgnoreCase("Assign Communication")) {
                if (roleId == 1) {
                    queryString = "SELECT TPO_SFTP.COMMUNICATION_ID,TPO_SFTP.REMOTE_HOST_IP_ADD,TPO_SFTP.REMOTE_PORT,TPO_SFTP.REMOTE_USERID "
                            + " FROM MSCVP.TPO_SFTP LEFT OUTER JOIN TPO_PARTNER_COMMUNICATIONS ON (TPO_SFTP.COMMUNICATION_ID = TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID) "
                            + " WHERE TPO_PARTNER_COMMUNICATIONS.PROTOCOL= 'SFTP' AND TPO_SFTP.ADMIN_PROTOCOL_FLAG='Y' and TPO_SFTP.TRANSFER_MODE='pull'"
                            + " AND TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID not in (SELECT COMMUNICATION_ID FROM TPO_PARTNER_COMMUNICATIONS where PARTNER_ID = " + partnerId + " )";
                } else {
                    queryString = "SELECT TPO_SFTP.COMMUNICATION_ID,TPO_SFTP.REMOTE_HOST_IP_ADD,TPO_SFTP.REMOTE_PORT,TPO_SFTP.REMOTE_USERID "
                            + " FROM MSCVP.TPO_SFTP LEFT OUTER JOIN TPO_PARTNER_COMMUNICATIONS ON (TPO_SFTP.COMMUNICATION_ID = TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID) "
                            + " WHERE TPO_PARTNER_COMMUNICATIONS.PROTOCOL= 'SFTP' AND TPO_SFTP.ADMIN_PROTOCOL_FLAG='Y' and TPO_SFTP.TRANSFER_MODE='pull' and TPO_SFTP.CREATED_BY='" + loginId + "'"
                            + " AND TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID not in (SELECT COMMUNICATION_ID FROM TPO_PARTNER_COMMUNICATIONS where PARTNER_ID = " + partnerId + " )";
                }
            }
            if (managecommunication.equalsIgnoreCase("Remove Communication")) {
                queryString = "SELECT TPO_SFTP.COMMUNICATION_ID,TPO_SFTP.REMOTE_HOST_IP_ADD,TPO_SFTP.REMOTE_PORT,TPO_SFTP.REMOTE_USERID "
                        + " FROM TPO_SFTP LEFT OUTER JOIN TPO_PARTNER_COMMUNICATIONS ON (TPO_SFTP.COMMUNICATION_ID = TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID) "
                        + " WHERE TPO_PARTNER_COMMUNICATIONS.PARTNER_ID = " + partnerId;
            }
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            tpoManageCommunicationList = new ArrayList<AdminBean>();
            while (resultSet.next()) {
                AdminBean adminBean = new AdminBean();
                adminBean.setId(resultSet.getInt("COMMUNICATION_ID"));
                adminBean.setSftp_host_ip(resultSet.getString("REMOTE_HOST_IP_ADD"));
                adminBean.setSftp_remote_port(resultSet.getString("REMOTE_PORT"));
                adminBean.setSftp_remote_userId(resultSet.getString("REMOTE_USERID"));
                tpoManageCommunicationList.add(adminBean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tpoManageCommunicationList;
    }

    public ArrayList<AdminBean> getHTTPManageCommunicationsList(int roleId, String loginId, int partnerId, String managecommunication) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String queryString = null;
        try {
            if (managecommunication.equalsIgnoreCase("Assign Communication")) {
                if (roleId == 1) {
                    queryString = "SELECT TPO_HTTP.COMMUNICATION_ID,TPO_HTTP.HTTP_END_POINT,TPO_HTTP.HTTP_PORT,TPO_HTTP.URL "
                            + " FROM MSCVP.TPO_HTTP LEFT OUTER JOIN TPO_PARTNER_COMMUNICATIONS ON (TPO_HTTP.COMMUNICATION_ID = TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID) "
                            + " WHERE TPO_PARTNER_COMMUNICATIONS.PROTOCOL= 'HTTP' AND TPO_HTTP.ADMIN_PROTOCOL_FLAG='Y' and TPO_HTTP.TRANSFER_MODE='push'"
                            + " AND TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID not in (SELECT COMMUNICATION_ID FROM TPO_PARTNER_COMMUNICATIONS where PARTNER_ID = " + partnerId + " )";
                } else {
                    queryString = "SELECT TPO_HTTP.COMMUNICATION_ID,TPO_HTTP.HTTP_END_POINT,TPO_HTTP.HTTP_PORT,TPO_HTTP.URL "
                            + " FROM MSCVP.TPO_HTTP LEFT OUTER JOIN TPO_PARTNER_COMMUNICATIONS ON (TPO_HTTP.COMMUNICATION_ID = TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID) "
                            + " WHERE TPO_PARTNER_COMMUNICATIONS.PROTOCOL= 'HTTP' AND TPO_HTTP.ADMIN_PROTOCOL_FLAG='Y' and TPO_HTTP.TRANSFER_MODE='push' and TPO_HTTP.CREATED_BY='" + loginId + "'"
                            + " AND TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID not in (SELECT COMMUNICATION_ID FROM TPO_PARTNER_COMMUNICATIONS where PARTNER_ID = " + partnerId + " )";
                }
            }
            if (managecommunication.equalsIgnoreCase("Remove Communication")) {

                queryString = "SELECT TPO_HTTP.COMMUNICATION_ID,TPO_HTTP.HTTP_END_POINT,TPO_HTTP.HTTP_PORT,TPO_HTTP.URL "
                        + " FROM TPO_HTTP LEFT OUTER JOIN TPO_PARTNER_COMMUNICATIONS ON (TPO_HTTP.COMMUNICATION_ID = TPO_PARTNER_COMMUNICATIONS.COMMUNICATION_ID) "
                        + "WHERE TPO_PARTNER_COMMUNICATIONS.PARTNER_ID = " + partnerId;
            }
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.prepareStatement(queryString);
            resultSet = statement.executeQuery();
            tpoManageCommunicationList = new ArrayList<AdminBean>();

            while (resultSet.next()) {
                AdminBean adminBean = new AdminBean();
                adminBean.setId(resultSet.getInt("COMMUNICATION_ID"));
                adminBean.setHttp_endpoint(resultSet.getString("HTTP_END_POINT"));
                adminBean.setHttp_port(resultSet.getInt("HTTP_PORT"));
                adminBean.setHttp_url(resultSet.getString("URL"));
                tpoManageCommunicationList.add(adminBean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tpoManageCommunicationList;
    }

    public String doTpoManageCommunicationAdd(AdminAction adminAction) throws ServiceLocatorException {
        int isManageCommunicationInserted = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            StringBuffer assignCommunicationQuery = new StringBuffer();
            assignCommunicationQuery.append(" INSERT INTO MSCVP.TPO_PARTNER_COMMUNICATIONS(PARTNER_ID, COMMUNICATION_ID, PROTOCOL)");
            assignCommunicationQuery.append(" VALUES(?,?,?)");
            String communications[] = adminAction.getCommunicationMesId();
            for (int i = 0; i < communications.length; i++) {
                preparedStatement = connection.prepareStatement(assignCommunicationQuery.toString());
                preparedStatement.setInt(1, adminAction.getPartnerId());
                preparedStatement.setInt(2, Integer.parseInt(communications[i]));
                preparedStatement.setString(3, DataSourceDataProvider.getInstance().getProtocolByCommunicationId(Integer.parseInt(communications[i])));
                isManageCommunicationInserted = isManageCommunicationInserted + preparedStatement.executeUpdate();
            }
            if (isManageCommunicationInserted > 0) {
                responseString = "<font color='green'>Protocol assigned successfully.</font>";
            } else {
                responseString = "<font color='green'>Protocol assigning failed.</font>";
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

    public String doTpoManageCommunicationRemove(String communicationId) throws ServiceLocatorException {
        int count = 0;
        String responseString = "";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            // String query = "DELETE FROM MSCVP.TPO_PARTNER_COMMUNICATIONS WHERE PARTNER_ID =" + partnerId + " AND COMMUNICATION_ID=" + communicationId + "";
            String query = "DELETE FROM MSCVP.TPO_PARTNER_COMMUNICATIONS WHERE COMMUNICATION_ID='" + communicationId + "'";
            preparedStatement = connection.prepareStatement(query);
            count = preparedStatement.executeUpdate();
            if (count > 0) {
                responseString = "<font color='green'>Assinged Communication Deleted deleted sucessfully</font>";
            } else {
                responseString = "<font color='red'>Assinged Communication Deleted delete filaed</font>";
            }
        } catch (SQLException e) {
            //  e.printStackTrace();
        } catch (Exception ex) {
            //System.out.println("hi"+ex.getMessage());
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
            } catch (SQLException se) {
                throw new ServiceLocatorException(se);
            }
        }
        return responseString;
    }

    public List getCertMonitorData(String certType, String dateFrom, String dateTo) throws ServiceLocatorException {
        String cType = certType;
        Connection con = null;
        List<LinkedHashMap> al = new LinkedList<LinkedHashMap>();
        String date = null;
        String dateto = null;
        if ((dateFrom != null) && !"".equalsIgnoreCase(dateFrom)) {
            date = dateFrom.replace("/", "-").substring(0, 10);
            dateto = dateTo.replace("/", "-").substring(0, 10);
        }
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.179:1521:orcl", "si_user", "SI_admin1");
            Statement st = con.createStatement();
            ResultSet rs = null;
            if ("TRUSTED".equalsIgnoreCase(cType)) {
                if ((dateFrom != null) && !"".equalsIgnoreCase(dateFrom)) {
                    rs = st.executeQuery("SELECT NAME AS CERTIFICATE_NAME,NOT_BEFORE as VALID_FROM , NOT_AFTER as VALID_TILL , (to_date (NOT_AFTER,'dd-MM-yyyy') - to_date(SYSDATE,'dd-MM-yyyy')) AS DAYS FROM TRUSTED_CERT_INFO WHERE NOT_BEFORE > to_date('" + date + "','dd-mm-yyyy') AND NOT_AFTER < to_date('" + dateto + "','dd-mm-yyyy') ORDER BY DAYS ");
                } else {
                    rs = st.executeQuery("SELECT NAME AS CERTIFICATE_NAME,NOT_BEFORE as VALID_FROM , NOT_AFTER as VALID_TILL , (to_date (NOT_AFTER,'dd-MM-yyyy') - to_date(SYSDATE,'dd-MM-yyyy')) AS DAYS FROM TRUSTED_CERT_INFO ORDER BY DAYS ");

                }
            } else if ("CA".equalsIgnoreCase(cType)) {
                if ((dateFrom != null) && !"".equalsIgnoreCase(dateFrom)) {
                    rs = st.executeQuery("SELECT NAME AS CERTIFICATE_NAME,NOT_BEFORE as VALID_FROM , NOT_AFTER as VALID_TILL , (to_date (NOT_AFTER,'dd-MM-yyyy') - to_date(SYSDATE,'dd-MM-yyyy')) AS DAYS FROM CA_CERT_INFO WHERE NOT_BEFORE > to_date('" + date + "','dd-mm-yyyy') AND NOT_AFTER < to_date('" + dateto + "','dd-mm-yyyy')  ORDER BY DAYS");
                } else {
                    rs = st.executeQuery("SELECT NAME AS CERTIFICATE_NAME,NOT_BEFORE as VALID_FROM , NOT_AFTER as VALID_TILL , (to_date (NOT_AFTER,'dd-MM-yyyy') - to_date(SYSDATE,'dd-MM-yyyy')) AS DAYS FROM CA_CERT_INFO   ORDER BY DAYS");
                }
            } else if ("SYSTEM".equalsIgnoreCase(cType)) {
                if ((dateFrom != null) && !"".equalsIgnoreCase(dateFrom)) {
                    rs = st.executeQuery("SELECT NAME AS CERTIFICATE_NAME,NOT_BEFORE as VALID_FROM , NOT_AFTER as VALID_TILL ,(to_date (NOT_AFTER,'dd-MM-yyyy') - to_date(SYSDATE,'dd-MM-yyyy'))  AS DAYS FROM CERTS_AND_PRI_KEY WHERE NOT_BEFORE > to_date('" + date + "','dd-mm-yyyy') AND NOT_AFTER < to_date('" + dateto + "','dd-mm-yyyy')   ORDER BY DAYS");
                } else {
                    rs = st.executeQuery("SELECT NAME AS CERTIFICATE_NAME,NOT_BEFORE as VALID_FROM , NOT_AFTER as VALID_TILL ,(to_date (NOT_AFTER,'dd-MM-yyyy') - to_date(SYSDATE,'dd-MM-yyyy'))  AS DAYS FROM CERTS_AND_PRI_KEY   ORDER BY DAYS");
                }
            }

            ResultSetMetaData md = rs.getMetaData();
            int columncount = md.getColumnCount();
            LinkedHashMap map;

            while (rs.next()) {
                map = new LinkedHashMap();
                for (int i = 1; i <= columncount; i++) {
                    map.put(md.getColumnName(i), rs.getObject(i));
                }
                al.add(map);
            }

        } catch (SQLException s) {

            s.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return al;

    }

    @Override
    public List doTpoCodeListItems(String selectedName) throws ServiceLocatorException {
        System.out.println("in getListName");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getOracleConnection();
        List codeList = new ArrayList();
        CodeListBean codeListBean = null;
        String listName = null;
        System.out.println("before try");
        try {
            queryString = "SELECT  * FROM CODELIST_XREF_ITEM WHERE  LIST_NAME='" + selectedName + "'";
            preparedStatement = connection.prepareStatement(queryString);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                codeListBean = new CodeListBean();
                codeListBean.setListName(resultSet.getString("LIST_NAME"));
                codeListBean.setSender_id(resultSet.getString("SENDER_ID"));
                codeListBean.setReceiver_id(resultSet.getString("RECEIVER_ID"));
                codeListBean.setList_version(resultSet.getString("LIST_VERSION"));
                codeListBean.setSender_item(resultSet.getString("SENDER_ITEM"));
                codeListBean.setReceiver_item(resultSet.getString("RECEIVER_ITEM"));
                codeListBean.setText1(resultSet.getString("TEXT1"));
                codeListBean.setText2(resultSet.getString("TEXT2"));
                codeListBean.setText3(resultSet.getString("TEXT3"));
                codeListBean.setText4(resultSet.getString("TEXT4"));
                codeListBean.setDescription(resultSet.getString("DESCRIPTION"));
                codeListBean.setText5(resultSet.getString("TEXT5"));
                codeListBean.setText6(resultSet.getString("TEXT6"));
                codeListBean.setText7(resultSet.getString("TEXT7"));
                codeListBean.setText8(resultSet.getString("TEXT8"));
                codeListBean.setText9(resultSet.getString("TEXT9"));
                codeList.add(codeListBean);

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
        return codeList;
    }

    @Override
    public List getCodeListNames(String selectedName) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getOracleConnection();
        List listNameMap = new ArrayList();
        String listName = null;
        try {
            queryString = "SELECT  DISTINCT(LIST_NAME) FROM CODELIST_XREF_ITEM WHERE LIST_NAME LIKE '" + selectedName.toUpperCase() + "%'";
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

    @Override
    public String addCodeList(String jsonData) throws ServiceLocatorException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String queryString = null;
        connection = ConnectionProvider.getInstance().getOracleConnection();
        JSONArray array = null;
           JSONObject jsonObj=null;
           int updatedRows=0;
        try {
            array = new JSONArray(jsonData);

           
        }  catch (JSONException ex) {
            Logger.getLogger(AdminServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        try {
            queryString = "INSERT INTO SI_USER.CODELIST_XREF_ITEM "
                    + "(LIST_NAME, SENDER_ID, RECEIVER_ID, LIST_VERSION, SENDER_ITEM, RECEIVER_ITEM, TEXT1, TEXT2, TEXT3, TEXT4, DESCRIPTION, TEXT5, TEXT6, TEXT7, TEXT8, TEXT9)"
                    + " VALUES (?, ?, ?,? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
           for(int i=0; i<array.length(); i++){
       jsonObj  = array.getJSONObject(i);
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setString(1, jsonObj.getString("listName1"));
            preparedStatement.setString(2, jsonObj.getString("senderIdInst"));
            preparedStatement.setString(3, jsonObj.getString("recId"));
            preparedStatement.setInt(4, Integer.parseInt(jsonObj.getString("listVerson")));
            preparedStatement.setString(5, jsonObj.getString("senderItem"));
            preparedStatement.setString(6, jsonObj.getString("recItem"));
            preparedStatement.setString(7, jsonObj.getString("text1"));
            preparedStatement.setString(8, jsonObj.getString("text2"));
            preparedStatement.setString(9, jsonObj.getString("text3"));
            preparedStatement.setString(10, jsonObj.getString("text4"));
            preparedStatement.setString(11, jsonObj.getString("desc"));
            preparedStatement.setString(12, jsonObj.getString("text5"));
            preparedStatement.setString(13, jsonObj.getString("text6"));
            preparedStatement.setString(14, jsonObj.getString("text7"));
            preparedStatement.setString(15, jsonObj.getString("text8"));
            preparedStatement.setString(16, jsonObj.getString("text9"));
            updatedRows = preparedStatement.executeUpdate();
           }
        } catch (SQLException sql) {
            throw new ServiceLocatorException(sql);
        } catch (JSONException e) {
           e.printStackTrace();
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

return "success";
    }

    
}
