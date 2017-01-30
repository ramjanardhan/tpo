 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.tpOnboarding;

import com.mss.tpo.util.ConnectionProvider;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.DateUtility;
import com.mss.tpo.util.MailManager;
import com.mss.tpo.util.PasswordUtil;
import com.mss.tpo.util.RandomPasswordGenerator;
import com.mss.tpo.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class TpOnboardingServiceImpl implements TpOnboardingService {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    String responseString = null;
    Map mp;
    TpOnboardingBean tpOnboardingBean = null;
    private ArrayList<TpOnboardingBean> tpoSearchProfileList;
    private HttpServletRequest httpServletRequest;
    private ArrayList<TpOnboardingBean> tpoSearchEnvelopeList;
    private ArrayList<TpOnboardingBean> tpoSearchPartnersList;

    public TpOnboardingBean getPartnerInfo(int partnerId, String loginId) throws ServiceLocatorException {
        try {
            tpOnboardingBean = new TpOnboardingBean();
            connection = ConnectionProvider.getInstance().getConnection();
            String partnerInfoQuery = "SELECT NAME, PHONE_NO, EMAIL, ADDRESS, CITY, STATE, COUNTRY, ZIPCODE, CREATED_BY, CREATED_TS, TP_FLAG FROM MSCVP.TPO_PARTNERS WHERE ID=?";
            preparedStatement = connection.prepareStatement(partnerInfoQuery);
            preparedStatement.setInt(1, partnerId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tpOnboardingBean.setPartnerName(resultSet.getString("NAME"));
                tpOnboardingBean.setPhoneNo(resultSet.getString("PHONE_NO"));
                tpOnboardingBean.setAddress1(resultSet.getString("ADDRESS"));
                tpOnboardingBean.setCity(resultSet.getString("CITY"));
                tpOnboardingBean.setState(resultSet.getString("STATE"));
                tpOnboardingBean.setCountry(resultSet.getString("COUNTRY"));
                tpOnboardingBean.setZipCode(resultSet.getString("ZIPCODE"));
                tpOnboardingBean.setContactEmail(resultSet.getString("EMAIL"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        return tpOnboardingBean;
    }

    public String doTpoUserRegister(int partnerId, int roleId, String loginId, TpOnboardingAction tpAction) throws ServiceLocatorException {
        int istpoUserInserted = 0;
        String email = tpAction.getRegcontactEmail();
        PasswordUtil passwordUtil = new PasswordUtil();
        String password = RandomPasswordGenerator.generatePswd(4, 7, 2, 2, 0);
        String generatedPassword = passwordUtil.encryptPwd(password);
        String userloginId = email.substring(0, email.indexOf("@")).toLowerCase();
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        String createdBy = tpAction.getCreated_by();
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String tpoRegisterQuery = "INSERT INTO MSCVP.TPO_USER(LOGINID, PASSWORD, PARTNER_ID, NAME, EMAIL, "
                    + "PHONE_NO, COUNTRY, CREATED_BY, CREATED_TS,ROLE_ID,LNAME,CITY,STATE, ZIPCODE, ADDRESS,ACTIVE)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(tpoRegisterQuery);
            preparedStatement.setString(1, userloginId);
            preparedStatement.setString(2, generatedPassword);
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, tpAction.getRegcontactName());
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, tpAction.getRegphoneNo());
            preparedStatement.setString(7, tpAction.getRegcountry());
            preparedStatement.setString(8, createdBy);
            preparedStatement.setTimestamp(9, curdate);
            if (roleId == 1) {  //main admin roleId
                preparedStatement.setInt(10, 2);//main admin user
            }
            preparedStatement.setString(11, tpAction.getRegcontactLName());
            preparedStatement.setString(12, tpAction.getRegcity());
            preparedStatement.setString(13, tpAction.getRegstate());
            preparedStatement.setString(14, tpAction.getRegzipCode());
            preparedStatement.setString(15, tpAction.getRegaddress());
            preparedStatement.setString(16, "A");
            istpoUserInserted = istpoUserInserted + preparedStatement.executeUpdate();
            if (istpoUserInserted > 0) {
                responseString = "<font color='green'>Register successfully</font>";
                MailManager.tpoUserIdPwd(tpAction.getRegcontactName(), tpAction.getRegpartnerName(), tpAction.getRegcontactEmail(), userloginId, passwordUtil.decryptPwd(generatedPassword));
            }
        } catch (Exception e) {
            responseString = "<font color='red'>Please try again!</font>";
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
                se.printStackTrace();
                throw new ServiceLocatorException(se);
            }
        }
        return responseString;
    }

    public String doAddPartnerUser(int partnerId, int roleId, String loginId, TpOnboardingAction tpAction) throws ServiceLocatorException {
        int istpoUserInserted = 0;
        String email = tpAction.getContactEmail();
        PasswordUtil passwordUtil = new PasswordUtil();
        String password = RandomPasswordGenerator.generatePswd(4, 7, 2, 2, 0);
        String generatedPassword = passwordUtil.encryptPwd(password);
        String userLoginId = email.substring(0, email.indexOf("@")).toLowerCase();
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        String partnerName = DataSourceDataProvider.getInstance().getTpoPartnerName(partnerId);
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String tpoAddPartnerUserQuery = "INSERT INTO MSCVP.TPO_USER(LOGINID, PASSWORD, NAME, LNAME, EMAIL, ROLE_ID, CITY, "
                    + "STATE, ZIPCODE, ADDRESS, PHONE_NO, COUNTRY, ACTIVE, PARTNER_ID, CREATED_BY, CREATED_TS )"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(tpoAddPartnerUserQuery);
            preparedStatement.setString(1, userLoginId);
            preparedStatement.setString(2, generatedPassword);
            preparedStatement.setString(3, tpAction.getContactName());
            preparedStatement.setString(4, tpAction.getContactLastName());
            preparedStatement.setString(5, email);
            if (roleId == 3) { // partner admin roleId
                preparedStatement.setInt(6, 4);//partner main user
            } else if (roleId == 4) { // partner main user roleId
                preparedStatement.setInt(6, 5);//partner users
            }
            preparedStatement.setString(7, tpAction.getCity());
            preparedStatement.setString(8, tpAction.getState());
            preparedStatement.setString(9, tpAction.getZipCode());
            preparedStatement.setString(10, tpAction.getAddress1());
            preparedStatement.setString(11, tpAction.getPhoneNo());
            preparedStatement.setString(12, tpAction.getCountry());
            preparedStatement.setString(13, "A");
            preparedStatement.setInt(14, partnerId);
            preparedStatement.setString(15, loginId);
            preparedStatement.setTimestamp(16, curdate);
            istpoUserInserted = istpoUserInserted + preparedStatement.executeUpdate();
            if (istpoUserInserted > 0) {
                responseString = "<font color='green'>Register successfully</font>";
                MailManager.tpoUserIdPwd(tpAction.getContactName(), partnerName, email, userLoginId, passwordUtil.decryptPwd(generatedPassword));
            }
        } catch (Exception e) {
            responseString = "<font color='red'>Please try again!</font>";
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
                se.printStackTrace();
                throw new ServiceLocatorException(se);
            }
        }
        return responseString;
    }

    public String updatePartnerInfo(TpOnboardingAction tpAction, String loginId, int partnerId) throws ServiceLocatorException {
        int istpoPartnerUpdated = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String updateTpoUserQuery = ("UPDATE MSCVP.TPO_PARTNERS SET PHONE_NO = ?,ADDRESS = ?,CITY = ?,STATE = ?,"
                    + "COUNTRY = ?,ZIPCODE = ?,MODIFIED_BY = ?,MODIFIED_TS = ?,EMAIL=? WHERE ID =?");
            preparedStatement = connection.prepareStatement(updateTpoUserQuery);
            preparedStatement.setString(1, tpAction.getPhoneNo());
            preparedStatement.setString(2, tpAction.getAddress1());
            preparedStatement.setString(3, tpAction.getCity());
            preparedStatement.setString(4, tpAction.getState());
            preparedStatement.setString(5, tpAction.getCountry());
            preparedStatement.setString(6, tpAction.getZipCode());
            preparedStatement.setString(7, loginId);
            preparedStatement.setTimestamp(8, curdate);
            preparedStatement.setString(9, tpAction.getContactEmail());
            preparedStatement.setInt(10, partnerId);
            istpoPartnerUpdated = istpoPartnerUpdated + preparedStatement.executeUpdate();
            if (istpoPartnerUpdated > 0) {
                responseString = "<font color='green'>Partner updated successfully</font>";
            } else {
                responseString = "<font color='red'>Please try again!</font>";
            }
        } catch (Exception e) {
            responseString = "<font color='red'>Please try again!</font>";
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
                se.printStackTrace();
                throw new ServiceLocatorException(se);
            }
        }
        return responseString;
    }

    @Override
    public ArrayList<TpOnboardingBean> tpoSearchPartners(String loginId, int roleId, String flag, TpOnboardingAction tpAction) {
        StringBuffer partnerSearchQuery = new StringBuffer();
        partnerSearchQuery.append("SELECT ID,NAME, PHONE_NO, CITY, STATUS, STATE, COUNTRY, ZIPCODE, CREATED_BY, CREATED_TS FROM MSCVP.TPO_PARTNERS WHERE 1=1 ");
        if ("searchFlag".equals(flag)) {
            if (tpAction.getPartnerName() != null && !"".equals(tpAction.getPartnerName().trim())) {
                partnerSearchQuery.append(" AND lcase(NAME) like lcase('%" + (tpAction.getPartnerName()) + "%') ");
            }
            if (tpAction.getCountry() != null && !"-1".equals(tpAction.getCountry().trim())) {
                partnerSearchQuery.append(" AND COUNTRY='" + tpAction.getCountry() + "' ");
            }
            if (tpAction.getStatus() != null && !"-1".equals(tpAction.getStatus().trim())) {
                partnerSearchQuery.append(" AND STATUS='" + tpAction.getStatus() + "' ");
            }
        }
        if (roleId == 2) {
            partnerSearchQuery.append(" AND (CREATED_BY='" + loginId + "' OR ASSIGNED_TO = '" + loginId + "') ");
        }
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(partnerSearchQuery.toString());
            tpoSearchPartnersList = new ArrayList<TpOnboardingBean>();
            while (resultSet.next()) {
                TpOnboardingBean tpOnboardingBean = new TpOnboardingBean();
                tpOnboardingBean.setId(resultSet.getInt("ID"));
                tpOnboardingBean.setPartnerName(resultSet.getString("NAME"));
                tpOnboardingBean.setPhoneNo(resultSet.getString("PHONE_NO"));
                tpOnboardingBean.setStatus(resultSet.getString("STATUS"));
                tpOnboardingBean.setCity(resultSet.getString("CITY"));
                tpOnboardingBean.setState(resultSet.getString("STATE"));
                tpOnboardingBean.setCountry(resultSet.getString("COUNTRY"));
                tpOnboardingBean.setZipCode(resultSet.getString("ZIPCODE"));
                tpOnboardingBean.setCreated_by(resultSet.getString("CREATED_BY"));
                tpOnboardingBean.setCreated_ts(resultSet.getTimestamp("CREATED_TS"));
                tpoSearchPartnersList.add(tpOnboardingBean);
            }
        } catch (Exception ex) {
            Logger.getLogger(TpOnboardingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tpoSearchPartnersList;
    }

    @Override
    public ArrayList<TpOnboardingBean> tpoSearchProfile(String loginId, int partnerId, String flag, TpOnboardingAction tpAction) {
        StringBuffer profileSearchQuery = new StringBuffer();
        profileSearchQuery.append("SELECT ID, PROTOCOL, TRANSFER_MODE, STATUS, CREATED_BY, CREATED_TS FROM MSCVP.TPO_COMMUNICATION WHERE 1=1 ");
        if ("searchFlag".equals(flag)) {
            if (tpAction.getCommnProtocol() != null && !"-1".equals(tpAction.getCommnProtocol().trim())) {
                profileSearchQuery.append(" AND PROTOCOL='" + tpAction.getCommnProtocol() + "' ");
            }
            if (tpAction.getStatus() != null && !"-1".equals(tpAction.getStatus().trim())) {
                profileSearchQuery.append(" AND STATUS='" + tpAction.getStatus() + "' ");
            }
            /*   if ((!"AS2".equals(tpAction.getCommnProtocol().trim())) || (!"SMTP".equals(tpAction.getCommnProtocol().trim()))) {
             if (tpAction.getTransferMode() != null && !"".equals(tpAction.getTransferMode().trim())) {
             profileSearchQuery.append(" TRANSFER_MODE='" + tpAction.getTransferMode() + "' AND");
             }
             } */
        }
        profileSearchQuery.append(" AND PARTNER_ID=" + partnerId + " AND CREATED_BY = '" + loginId + "' ");
        profileSearchQuery.append(" order By CREATED_TS DESC");
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(profileSearchQuery.toString());
            tpoSearchProfileList = new ArrayList<TpOnboardingBean>();

            while (resultSet.next()) {
                TpOnboardingBean tpOnboardingBean = new TpOnboardingBean();
                tpOnboardingBean.setId(resultSet.getInt("ID"));
                tpOnboardingBean.setCommnProtocol(resultSet.getString("PROTOCOL"));
                if (("FTP".equalsIgnoreCase((resultSet.getString("PROTOCOL")))) || ("HTTP".equalsIgnoreCase((resultSet.getString("PROTOCOL")))) || ("SFTP".equalsIgnoreCase((resultSet.getString("PROTOCOL"))))) {
                    tpOnboardingBean.setTransferMode(resultSet.getString("TRANSFER_MODE"));
                } else {
                    tpOnboardingBean.setTransferMode("--");
                }
                tpOnboardingBean.setStatus(resultSet.getString("STATUS"));
                tpOnboardingBean.setCreated_by(resultSet.getString("CREATED_BY"));
                tpOnboardingBean.setCreated_ts(resultSet.getTimestamp("CREATED_TS"));
                tpoSearchProfileList.add(tpOnboardingBean);

            }
        } catch (Exception ex) {
            Logger.getLogger(TpOnboardingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tpoSearchProfileList;
    }

    public String addTpoProfile(int partnerId, String partnerName, String loginId, String Email, TpOnboardingAction tpAction) throws ServiceLocatorException {
        int isProfileCommnInserted = 0;
        int isProtocolInserted = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        try {
            int communicationId = 0;
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            String addTpoProfileQuery = "INSERT INTO MSCVP.TPO_COMMUNICATION(PARTNER_ID, PARTNER_NAME, PROTOCOL, TRANSFER_MODE, TP_FLAG, CREATED_BY, CREATED_TS, STATUS) "
                    + "VALUES(?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(addTpoProfileQuery);
            preparedStatement.setInt(1, partnerId);
            preparedStatement.setString(2, partnerName);
            preparedStatement.setString(3, tpAction.getCommnProtocol());
            if (("AS2".equalsIgnoreCase(tpAction.getCommnProtocol())) || ("SMTP".equalsIgnoreCase(tpAction.getCommnProtocol()))) {
                preparedStatement.setString(4, "");
            } else {
                preparedStatement.setString(4, tpAction.getTransferMode());
            }
            preparedStatement.setString(5, "N");
            preparedStatement.setString(6, tpAction.getCreated_by());
            preparedStatement.setTimestamp(7, curdate);
            preparedStatement.setString(8, "INACTIVE");// , STATUS
            isProfileCommnInserted = isProfileCommnInserted + preparedStatement.executeUpdate();

            String commnIdQuery = "SELECT max(ID) AS communicationId FROM MSCVP.TPO_COMMUNICATION WHERE PARTNER_ID =" + partnerId + " AND CREATED_BY='" + tpAction.getCreated_by() + "' ";
            resultSet = statement.executeQuery(commnIdQuery);
            if (resultSet.next()) {
                communicationId = resultSet.getInt("communicationId");
            }

            if ((isProfileCommnInserted > 0) && tpAction.getCommnProtocol().equals("FTP") && tpAction.getTransferMode().equals("put")) {
                String ftpInsertQuery = "INSERT INTO MSCVP.TPO_FTP(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, FTP_METHOD, RECEIVING_PROTOCOL,"
                        + " FTP_HOST, FTP_PORT, FTP_USER_ID, FTP_PASSWORD, FTP_DIRECTORY, CONNECTION_METHOD, RESPONSE_TIMEOUT_SEC, TP_FLAG, CREATED_BY,"
                        + " CREATED_TS, STATUS) VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(ftpInsertQuery);
                preparedStatement.setInt(1, communicationId);
                preparedStatement.setInt(2, partnerId);
                preparedStatement.setString(3, tpAction.getTransferMode());
                preparedStatement.setString(4, tpAction.getFtp_ssl_req());
                preparedStatement.setString(5, tpAction.getFtp_method());
                preparedStatement.setString(6, tpAction.getFtp_recv_protocol());
                preparedStatement.setString(7, tpAction.getFtp_host());
                preparedStatement.setString(8, tpAction.getFtp_port());
                preparedStatement.setString(9, tpAction.getFtp_userId());
                preparedStatement.setString(10, tpAction.getFtp_pwd());
                preparedStatement.setString(11, tpAction.getFtp_directory());
                preparedStatement.setString(12, tpAction.getFtp_conn_method());
                preparedStatement.setInt(13, tpAction.getFtp_resp_time());
                preparedStatement.setString(14, "N");
                preparedStatement.setString(15, tpAction.getCreated_by());
                preparedStatement.setTimestamp(16, curdate);
                preparedStatement.setString(17, "INACTIVE");
                isProtocolInserted = isProtocolInserted + preparedStatement.executeUpdate();
                if ((isProtocolInserted > 0) && ("true".equalsIgnoreCase(tpAction.getFtp_ssl_req()))) {
                    String insertQuery = "INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,"
                            + " CIPHER_STRENGTH, TP_FLAG, CREATED_BY, CREATED_TS, CA_CERTIFICATES,SSL_CERT_DATA) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, communicationId);
                    preparedStatement.setInt(2, partnerId);
                    preparedStatement.setString(3, tpAction.getTransferMode());
                    preparedStatement.setString(4, tpAction.getCommnProtocol());
                    preparedStatement.setString(5, tpAction.getSsl_priority2());
                    preparedStatement.setString(6, tpAction.getSsl_cipher_stergth2());
                    preparedStatement.setString(7, "N");
                    preparedStatement.setString(8, tpAction.getCreated_by());
                    preparedStatement.setTimestamp(9, curdate);
                    preparedStatement.setString(10, tpAction.getCertGroups());
                    if (!"".equalsIgnoreCase(tpAction.getCertGroups())) {
                        preparedStatement.setString(11, DataSourceDataProvider.getInstance().getCertificatePath(tpAction.getCertGroups()));
                    } else {
                        preparedStatement.setString(11, "");
                    }
                    preparedStatement.executeUpdate();
                }
            } else if ((isProfileCommnInserted > 0) && tpAction.getCommnProtocol().equals("AS2")) {
                String as2InsertQuery = "INSERT INTO MSCVP.TPO_AS2(COMMUNICATION_ID, PARTNER_ID, SSL_FLAG, UPL_YOUR_SYS_CERT, MY_ORG, "
                        + "YOUR_ORG, MY_PART_PRO_NAME, YOUR_PART_PRO_NAME, MY_END_POINT, YOUR_END_POINT, STR_AS2_MSG_IN, "
                        + "WAIT_FOR_SYNC_MDN_PROC, TP_FLAG, CREATED_BY, CREATED_TS, STATUS, TRANSFER_MODE,SYS_CERT_DATA) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(as2InsertQuery);
                preparedStatement.setInt(1, communicationId);
                preparedStatement.setInt(2, partnerId);
                //preparedStatement.setString(3, tpAction.getTransferMode());
                preparedStatement.setString(3, tpAction.getAs2_ssl_req());
                preparedStatement.setString(4, tpAction.getFilepath());
                preparedStatement.setString(5, tpAction.getAs2_myOrgName());
                preparedStatement.setString(6, tpAction.getAs2_partOrgName());
                preparedStatement.setString(7, tpAction.getAs2_myPartname());
                preparedStatement.setString(8, tpAction.getAs2_yourPartname());
                preparedStatement.setString(9, tpAction.getAs2_myEndPoint());
                preparedStatement.setString(10, tpAction.getAs2_partendpoint());
                preparedStatement.setString(11, tpAction.getAs2_strMsg());
                preparedStatement.setString(12, tpAction.getAs2_waitForSync());
                preparedStatement.setString(13, "N");
                preparedStatement.setString(14, tpAction.getCreated_by());
                preparedStatement.setTimestamp(15, curdate);
                preparedStatement.setString(16, "INACTIVE");
                preparedStatement.setString(17, tpAction.getAs2_payloadSendMode());
                if (!"".equalsIgnoreCase(tpAction.getFilepath())) {
                    preparedStatement.setString(18, DataSourceDataProvider.getInstance().getCertificatePath(tpAction.getFilepath()));
                } else {
                    preparedStatement.setString(18, "");
                }
                isProtocolInserted = isProtocolInserted + preparedStatement.executeUpdate();
                if ((isProtocolInserted > 0) && ("true".equalsIgnoreCase(tpAction.getAs2_ssl_req()))) {
                    String insertQuery = "INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,"
                            + " CIPHER_STRENGTH, CA_CERTIFICATES, TP_FLAG, CREATED_BY, CREATED_TS,SSL_CERT_DATA) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, communicationId);
                    preparedStatement.setInt(2, partnerId);
                    preparedStatement.setString(3, tpAction.getTransferMode());
                    preparedStatement.setString(4, tpAction.getCommnProtocol());
                    preparedStatement.setString(5, tpAction.getSsl_priority2());
                    preparedStatement.setString(6, tpAction.getSsl_cipher_stergth2());
                    preparedStatement.setString(7, tpAction.getCertGroups());
                    preparedStatement.setString(8, "N");
                    preparedStatement.setString(9, tpAction.getCreated_by());
                    preparedStatement.setTimestamp(10, curdate);
                    if (!"".equalsIgnoreCase(tpAction.getCertGroups())) {
                        preparedStatement.setString(11, DataSourceDataProvider.getInstance().getCertificatePath(tpAction.getCertGroups()));
                    } else {
                        preparedStatement.setString(11, "");
                    }
                    preparedStatement.executeUpdate();

                }
            } else if ((isProfileCommnInserted > 0) && tpAction.getCommnProtocol().equals("HTTP") && tpAction.getTransferMode().equals("get")) {
                String httpInsertQuery = "INSERT INTO MSCVP.TPO_HTTP(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, SSL_FLAG, "
                        + "RECEIVING_PROTOCOL, HTTP_END_POINT, HTTP_MODE, RESPONSE_TIMEOUT_SEC, HTTP_PORT, TP_FLAG, "
                        + "CREATED_BY, CREATED_TS, URL, STATUS) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(httpInsertQuery);
                preparedStatement.setInt(1, communicationId);
                preparedStatement.setInt(2, partnerId);
                preparedStatement.setString(3, tpAction.getTransferMode());
                preparedStatement.setString(4, tpAction.getHttp_ssl_req());
                preparedStatement.setString(5, tpAction.getHttp_recv_protocol());
                preparedStatement.setString(6, tpAction.getHttp_endpoint());
                preparedStatement.setString(7, tpAction.getHttp_protocol_mode());
                preparedStatement.setInt(8, Integer.parseInt(tpAction.getHttp_resp_time()));
                preparedStatement.setInt(9, Integer.parseInt(tpAction.getHttp_port()));
                preparedStatement.setString(10, "N");
                preparedStatement.setString(11, tpAction.getCreated_by());
                preparedStatement.setTimestamp(12, curdate);
                preparedStatement.setString(13, tpAction.getHttp_url());
                preparedStatement.setString(14, "INACTIVE");
                isProtocolInserted = isProtocolInserted + preparedStatement.executeUpdate();
                if ((isProtocolInserted > 0) && ("true".equalsIgnoreCase(tpAction.getHttp_ssl_req()))) {
                    String insertQuery = "INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,"
                            + " CIPHER_STRENGTH, CA_CERTIFICATES, TP_FLAG, CREATED_BY, CREATED_TS,SSL_CERT_DATA) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setInt(1, communicationId);
                    preparedStatement.setInt(2, partnerId);
                    preparedStatement.setString(3, tpAction.getTransferMode());
                    preparedStatement.setString(4, tpAction.getCommnProtocol());
                    preparedStatement.setString(5, tpAction.getSsl_priority2());
                    preparedStatement.setString(6, tpAction.getSsl_cipher_stergth2());
                    preparedStatement.setString(7, tpAction.getCertGroups());
                    preparedStatement.setString(8, "N");
                    preparedStatement.setString(9, tpAction.getCreated_by());
                    preparedStatement.setTimestamp(10, curdate);
                    if (!"".equalsIgnoreCase(tpAction.getCertGroups())) {
                        preparedStatement.setString(11, DataSourceDataProvider.getInstance().getCertificatePath(tpAction.getCertGroups()));
                    } else {
                        preparedStatement.setString(11, "");
                    }
                    preparedStatement.executeUpdate();
                }
            } else if ((isProfileCommnInserted > 0) && tpAction.getCommnProtocol().equals("SFTP") && tpAction.getTransferMode().equals("put")) {
                String sftpInsertQuery = "INSERT INTO MSCVP.TPO_SFTP(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, "
                        + "CONN_METHOD, MY_SSH_PUB_KEY, UPL_YOUR_SSH_PUB_KEY, REMOTE_HOST_IP_ADD, REMOTE_USERID, "
                        + "METHOD, AUTH_METHOD, REMOTE_PORT, REMOTE_PWD, DIRECTORY, TP_FLAG, CREATED_BY, CREATED_TS, STATUS,SSH_CERT_DATA)"
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sftpInsertQuery);
                preparedStatement.setInt(1, communicationId);
                preparedStatement.setInt(2, partnerId);
                preparedStatement.setString(3, tpAction.getTransferMode());
                preparedStatement.setString(4, tpAction.getSftp_conn_method());
                preparedStatement.setString(5, tpAction.getSftp_public_key());
                preparedStatement.setString(6, tpAction.getFilepath());
                preparedStatement.setString(7, tpAction.getSftp_host_ip());
                preparedStatement.setString(8, tpAction.getSftp_remote_userId());
                preparedStatement.setString(9, tpAction.getSftp_method());
                preparedStatement.setString(10, tpAction.getSftp_auth_method());
                preparedStatement.setString(11, tpAction.getSftp_remote_port());
                preparedStatement.setString(12, tpAction.getSftp_remote_pwd());
                preparedStatement.setString(13, tpAction.getSftp_directory());
                preparedStatement.setString(14, "N");
                preparedStatement.setString(15, tpAction.getCreated_by());
                preparedStatement.setTimestamp(16, curdate);
                preparedStatement.setString(17, "INACTIVE");
                if (!"".equalsIgnoreCase(tpAction.getFilepath())) {
                    preparedStatement.setString(18, DataSourceDataProvider.getInstance().getCertificatePath(tpAction.getFilepath()));
                } else {
                    preparedStatement.setString(18, "");
                }
                isProtocolInserted = isProtocolInserted + preparedStatement.executeUpdate();
            } else if ((isProfileCommnInserted > 0) && tpAction.getCommnProtocol().equals("SMTP")) {
                String smtpInsertQuery = "INSERT INTO MSCVP.TPO_SMTP (COMMUNICATION_ID, PARTNER_ID,RECEIVING_PROTOCOL, "
                        + "SMTP_SERVER_PORT, TO_EMAIL_ADDRESS, SMTP_SERVER_HOST, FROM_EMAIL_ADDRESS, TP_FLAG, CREATED_BY, CREATED_TS, STATUS)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(smtpInsertQuery);
                preparedStatement.setInt(1, communicationId);
                preparedStatement.setInt(2, partnerId);
                preparedStatement.setString(3, tpAction.getSmtp_recv_protocol());
                preparedStatement.setInt(4, Integer.parseInt(tpAction.getSmtp_server_port()));
                preparedStatement.setString(5, tpAction.getSmtp_to_email());
                preparedStatement.setString(6, tpAction.getSmtp_server_protocol());
                preparedStatement.setString(7, tpAction.getSmtp_from_email());
                preparedStatement.setString(8, "N");
                preparedStatement.setString(9, tpAction.getCreated_by());
                preparedStatement.setTimestamp(10, curdate);
                preparedStatement.setString(11, "INACTIVE");
                isProtocolInserted = isProtocolInserted + preparedStatement.executeUpdate();
            }
            mp = tpAction.getMap();
            if ((isProfileCommnInserted > 0) && (isProtocolInserted > 0)) {
                responseString = "<font color='green'>Inserted successfully</font>";
                MailManager.tpoDetails(partnerId, loginId, Email, tpAction.getPartnerName(), tpAction.getCommnProtocol(), tpAction.getTransferMode(),
                        tpAction.getFilepath(), tpAction.getCertGroups(), mp, "Insert");
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

    public TpOnboardingAction tpogetProfile(int Id, String commonprotocol, TpOnboardingAction tpOnboardingAction) {
        //  TpOnboardingAction tpOnboardingAction = null;
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
                        + "and TPO_COMMUNICATION.PROTOCOL='" + commonprotocol + "'";
                preparedStatement = connection.prepareStatement(profileInfoQuery.toString());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int communicationid = resultSet.getInt("COMMUNICATION_ID");
                    tpOnboardingAction.setCommunicationId(communicationid);
                    tpOnboardingAction.setId(resultSet.getInt("PARTNER_ID"));
                    String transfermode = resultSet.getString("TRANSFER_MODE");
                    tpOnboardingAction.setTransferMode(transfermode);
                    tpOnboardingAction.setFtp_method(resultSet.getString("FTP_METHOD"));
                    tpOnboardingAction.setFtp_recv_protocol(resultSet.getString("RECEIVING_PROTOCOL"));
                    tpOnboardingAction.setFtp_host(resultSet.getString("FTP_HOST"));
                    tpOnboardingAction.setFtp_port(resultSet.getString("FTP_PORT"));
                    tpOnboardingAction.setFtp_userId(resultSet.getString("FTP_USER_ID"));
                    tpOnboardingAction.setFtp_pwd(resultSet.getString("FTP_PASSWORD"));
                    tpOnboardingAction.setFtp_directory(resultSet.getString("FTP_DIRECTORY"));
                    tpOnboardingAction.setFtp_conn_method(resultSet.getString("CONNECTION_METHOD"));
                    tpOnboardingAction.setFtp_resp_time(resultSet.getInt("RESPONSE_TIMEOUT_SEC"));
                    String ssl_flag = resultSet.getString("SSL_FLAG");
                    tpOnboardingAction.setFtp_ssl_req(ssl_flag);
                    if (ssl_flag.equalsIgnoreCase("true")) {
                        sslprofileInfoQuery = "SELECT SSL_PRIORITY, CIPHER_STRENGTH, CA_CERTIFICATES FROM MSCVP.TPO_SSL where COMMUNICATION_ID=" + communicationid;
                        preparedStatement = connection.prepareStatement(sslprofileInfoQuery.toString());
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            if (transfermode.equalsIgnoreCase("get")) {
                                tpOnboardingAction.setSsl_priority(resultSet.getString("SSL_PRIORITY"));
                                tpOnboardingAction.setSsl_cipher_stergth(resultSet.getString("CIPHER_STRENGTH"));
                                //tpOnboardingAction.setCertGroups(resultSet.getString("CA_CERTIFICATES"));
                            } else if (transfermode.equalsIgnoreCase("put")) {
                                tpOnboardingAction.setSsl_priority2(resultSet.getString("SSL_PRIORITY"));
                                tpOnboardingAction.setSsl_cipher_stergth2(resultSet.getString("CIPHER_STRENGTH"));
                                tpOnboardingAction.setCertGroups(resultSet.getString("CA_CERTIFICATES"));
                            }
                        }
                    }
                    tpOnboardingAction.setCommnProtocol(commonprotocol);
                }
            } else if (commonprotocol.equalsIgnoreCase("AS2")) {
                profileInfoQuery = "SELECT TPO_AS2.COMMUNICATION_ID,TPO_AS2.PARTNER_ID,TPO_AS2.TRANSFER_MODE,"
                        + "TPO_AS2.UPL_YOUR_SYS_CERT,TPO_AS2.MY_ORG,TPO_AS2.YOUR_ORG,TPO_AS2.MY_PART_PRO_NAME,TPO_AS2.YOUR_PART_PRO_NAME,"
                        + "TPO_AS2.MY_END_POINT,TPO_AS2.YOUR_END_POINT,TPO_AS2.STR_AS2_MSG_IN,TPO_AS2.WAIT_FOR_SYNC_MDN_PROC,TPO_AS2.SSL_FLAG "
                        + "FROM MSCVP.TPO_AS2 join TPO_COMMUNICATION on (TPO_AS2.COMMUNICATION_ID = TPO_COMMUNICATION.ID) "
                        + "where TPO_AS2.COMMUNICATION_ID = " + Id + " and TPO_COMMUNICATION.PROTOCOL='" + commonprotocol + "'";
                preparedStatement = connection.prepareStatement(profileInfoQuery.toString());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int communicationid = resultSet.getInt("COMMUNICATION_ID");
                    tpOnboardingAction.setCommunicationId(communicationid);
                    tpOnboardingAction.setId(resultSet.getInt("PARTNER_ID"));
                    tpOnboardingAction.setAs2_sysCert(resultSet.getString("UPL_YOUR_SYS_CERT"));
                    tpOnboardingAction.setAs2_myOrgName(resultSet.getString("MY_ORG"));
                    tpOnboardingAction.setAs2_partOrgName(resultSet.getString("YOUR_ORG"));
                    tpOnboardingAction.setAs2_myPartname(resultSet.getString("MY_PART_PRO_NAME"));
                    tpOnboardingAction.setAs2_yourPartname(resultSet.getString("YOUR_PART_PRO_NAME"));
                    tpOnboardingAction.setAs2_myEndPoint(resultSet.getString("MY_END_POINT"));
                    tpOnboardingAction.setAs2_partendpoint(resultSet.getString("YOUR_END_POINT"));
                    tpOnboardingAction.setAs2_strMsg(resultSet.getString("STR_AS2_MSG_IN"));
                    tpOnboardingAction.setAs2_waitForSync(resultSet.getString("WAIT_FOR_SYNC_MDN_PROC"));
                    tpOnboardingAction.setAs2_payloadSendMode(resultSet.getString("TRANSFER_MODE"));
                    String ssl_flag = resultSet.getString("SSL_FLAG");
                    tpOnboardingAction.setAs2_ssl_req(ssl_flag);
                    if (ssl_flag.equalsIgnoreCase("true")) {
                        sslprofileInfoQuery = "SELECT SSL_PRIORITY, CIPHER_STRENGTH, CA_CERTIFICATES FROM MSCVP.TPO_SSL where COMMUNICATION_ID=" + communicationid;
                        preparedStatement = connection.prepareStatement(sslprofileInfoQuery.toString());
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            tpOnboardingAction.setSsl_priority2(resultSet.getString("SSL_PRIORITY"));
                            tpOnboardingAction.setSsl_cipher_stergth2(resultSet.getString("CIPHER_STRENGTH"));
                            tpOnboardingAction.setCertGroups(resultSet.getString("CA_CERTIFICATES"));
                        }
                    }
                    tpOnboardingAction.setCommnProtocol(commonprotocol);
                }
            } else if (commonprotocol.equalsIgnoreCase("HTTP")) {
                profileInfoQuery = "SELECT TPO_HTTP.COMMUNICATION_ID,TPO_HTTP.PARTNER_ID,TPO_HTTP.TRANSFER_MODE,TPO_HTTP.SSL_FLAG,"
                        + "TPO_HTTP.RECEIVING_PROTOCOL,TPO_HTTP.HTTP_END_POINT,TPO_HTTP.HTTP_MODE,"
                        + "TPO_HTTP.RESPONSE_TIMEOUT_SEC,TPO_HTTP.HTTP_PORT,TPO_HTTP.URL "
                        + "FROM MSCVP.TPO_HTTP join TPO_COMMUNICATION on (TPO_HTTP.COMMUNICATION_ID = TPO_COMMUNICATION.ID)"
                        + " where TPO_HTTP.COMMUNICATION_ID =" + Id + " and TPO_COMMUNICATION.PROTOCOL='" + commonprotocol + "'";
                preparedStatement = connection.prepareStatement(profileInfoQuery.toString());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int communicationid = resultSet.getInt("COMMUNICATION_ID");
                    tpOnboardingAction.setCommunicationId(communicationid);
                    tpOnboardingAction.setId(resultSet.getInt("PARTNER_ID"));
                    String transfermode = resultSet.getString("TRANSFER_MODE");
                    tpOnboardingAction.setTransferMode(transfermode);
                    tpOnboardingAction.setHttp_recv_protocol(resultSet.getString("RECEIVING_PROTOCOL"));
                    tpOnboardingAction.setHttp_endpoint(resultSet.getString("HTTP_END_POINT"));
                    tpOnboardingAction.setHttp_protocol_mode(resultSet.getString("HTTP_MODE"));
                    tpOnboardingAction.setHttp_resp_time(resultSet.getString("RESPONSE_TIMEOUT_SEC"));
                    tpOnboardingAction.setHttp_port(resultSet.getString("HTTP_PORT"));
                    tpOnboardingAction.setHttp_url(resultSet.getString("URL"));
                    //tpOnboardingAction.setHttp_ssl_req(resultSet.getString("SSL_FLAG"));

                    String ssl_flag = resultSet.getString("SSL_FLAG");
                    tpOnboardingAction.setHttp_ssl_req(ssl_flag);
                    if (ssl_flag.equalsIgnoreCase("true")) {
                        sslprofileInfoQuery = "SELECT SSL_PRIORITY, CIPHER_STRENGTH, CA_CERTIFICATES FROM MSCVP.TPO_SSL where COMMUNICATION_ID=" + communicationid;
                        preparedStatement = connection.prepareStatement(sslprofileInfoQuery.toString());
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            if (transfermode.equalsIgnoreCase("put")) {
                                tpOnboardingAction.setSsl_priority(resultSet.getString("SSL_PRIORITY"));
                                tpOnboardingAction.setSsl_cipher_stergth(resultSet.getString("CIPHER_STRENGTH"));
                                //tpOnboardingAction.setCertGroups(resultSet.getString("CA_CERTIFICATES"));
                            } else if (transfermode.equalsIgnoreCase("get")) {
                                tpOnboardingAction.setSsl_priority2(resultSet.getString("SSL_PRIORITY"));
                                tpOnboardingAction.setSsl_cipher_stergth2(resultSet.getString("CIPHER_STRENGTH"));
                                tpOnboardingAction.setCertGroups(resultSet.getString("CA_CERTIFICATES"));
                            }
                        }
                    }
                }
            } else if (commonprotocol.equalsIgnoreCase("SFTP")) {
                profileInfoQuery = "SELECT TPO_SFTP.COMMUNICATION_ID,TPO_SFTP.PARTNER_ID,TPO_SFTP.TRANSFER_MODE,"
                        + "TPO_SFTP.CONN_METHOD,TPO_SFTP.UPL_YOUR_SSH_PUB_KEY,TPO_SFTP.REMOTE_HOST_IP_ADD,"
                        + "TPO_SFTP.REMOTE_USERID,TPO_SFTP.METHOD,TPO_SFTP.AUTH_METHOD,TPO_SFTP.REMOTE_PORT,TPO_SFTP.REMOTE_PWD,"
                        + "TPO_SFTP.DIRECTORY FROM MSCVP.TPO_SFTP join TPO_COMMUNICATION on (TPO_SFTP.COMMUNICATION_ID = TPO_COMMUNICATION.ID) "
                        + "where TPO_SFTP.COMMUNICATION_ID = " + Id + " and TPO_COMMUNICATION.PROTOCOL='" + commonprotocol + "'";
                preparedStatement = connection.prepareStatement(profileInfoQuery.toString());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int communicationid = resultSet.getInt("COMMUNICATION_ID");
                    tpOnboardingAction.setCommunicationId(communicationid);
                    tpOnboardingAction.setId(resultSet.getInt("PARTNER_ID"));
                    tpOnboardingAction.setTransferMode(resultSet.getString("TRANSFER_MODE"));
                    tpOnboardingAction.setSftp_conn_method(resultSet.getString("CONN_METHOD"));
                    tpOnboardingAction.setSftp_upload_public_key(resultSet.getString("UPL_YOUR_SSH_PUB_KEY"));
                    tpOnboardingAction.setSftp_host_ip(resultSet.getString("REMOTE_HOST_IP_ADD"));
                    tpOnboardingAction.setSftp_remote_userId(resultSet.getString("REMOTE_USERID"));
                    tpOnboardingAction.setSftp_method(resultSet.getString("METHOD"));
                    tpOnboardingAction.setSftp_auth_method(resultSet.getString("AUTH_METHOD"));
                    tpOnboardingAction.setSftp_remote_port(resultSet.getString("REMOTE_PORT"));
                    tpOnboardingAction.setSftp_remote_pwd(resultSet.getString("REMOTE_PWD"));
                    tpOnboardingAction.setSftp_directory(resultSet.getString("DIRECTORY"));
                }
            } else if (commonprotocol.equalsIgnoreCase("SMTP")) {
                profileInfoQuery = " SELECT TPO_SMTP.COMMUNICATION_ID,TPO_SMTP.PARTNER_ID,TPO_SMTP.RECEIVING_PROTOCOL,"
                        + "TPO_SMTP.SMTP_SERVER_PORT,TPO_SMTP.TO_EMAIL_ADDRESS,TPO_SMTP.SMTP_SERVER_HOST,TPO_SMTP.FROM_EMAIL_ADDRESS "
                        + "FROM MSCVP.TPO_SMTP join TPO_COMMUNICATION on (TPO_SMTP.COMMUNICATION_ID = TPO_COMMUNICATION.ID) "
                        + "where TPO_SMTP.COMMUNICATION_ID =" + Id + " and TPO_COMMUNICATION.PROTOCOL='" + commonprotocol + "'";
                preparedStatement = connection.prepareStatement(profileInfoQuery.toString());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    tpOnboardingAction.setCommunicationId(resultSet.getInt("COMMUNICATION_ID"));
                    tpOnboardingAction.setId(resultSet.getInt("PARTNER_ID"));
                    tpOnboardingAction.setSmtp_recv_protocol(resultSet.getString("RECEIVING_PROTOCOL"));
                    tpOnboardingAction.setSmtp_server_port(resultSet.getString("SMTP_SERVER_PORT"));
                    tpOnboardingAction.setSmtp_to_email(resultSet.getString("TO_EMAIL_ADDRESS"));
                    tpOnboardingAction.setSmtp_from_email(resultSet.getString("FROM_EMAIL_ADDRESS"));
                    tpOnboardingAction.setSmtp_server_protocol(resultSet.getString("SMTP_SERVER_HOST"));
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
        return tpOnboardingAction;
    }

    public String tpoUpdateProfile(int partnerId, int communicationId, String commonprotocol, String Email, TpOnboardingAction tpOnboardingAction) {
        int isCommunicationUpdated = 0;
        int isProtocolUpdated = 0;
        try {
            Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
            connection = ConnectionProvider.getInstance().getConnection();

            String communicationQuery = "UPDATE MSCVP.TPO_COMMUNICATION SET TP_FLAG = ?, MODIFIED_BY = ?, MODIFIED_TS = ?, STATUS = ? WHERE ID=" + communicationId;
            preparedStatement = connection.prepareStatement(communicationQuery);
            preparedStatement.setString(1, "N");
            preparedStatement.setString(2, tpOnboardingAction.getCreated_by());
            preparedStatement.setTimestamp(3, curdate);
            preparedStatement.setString(4, "INACTIVE");
            isCommunicationUpdated = isCommunicationUpdated + preparedStatement.executeUpdate();

            if (isCommunicationUpdated > 0) {
                if (commonprotocol.equalsIgnoreCase("FTP") && tpOnboardingAction.getTransferMode().equals("put")) {
                    String ftpUpdateQuery = "UPDATE MSCVP.TPO_FTP SET FTP_METHOD = ?, FTP_HOST = ?, FTP_PORT = ?, "
                            + "FTP_USER_ID = ?, FTP_PASSWORD = ?, FTP_DIRECTORY = ?, CONNECTION_METHOD = ?, RESPONSE_TIMEOUT_SEC = ?,"
                            + " SSL_FLAG = ?, TP_FLAG = ?, MODIFIED_BY = ?, MODIFIED_TS = ?, STATUS = ? WHERE COMMUNICATION_ID=" + communicationId;
                    preparedStatement = connection.prepareStatement(ftpUpdateQuery);
                    preparedStatement.setString(1, tpOnboardingAction.getFtp_method());
                    preparedStatement.setString(2, tpOnboardingAction.getFtp_host());
                    preparedStatement.setString(3, tpOnboardingAction.getFtp_port());
                    preparedStatement.setString(4, tpOnboardingAction.getFtp_userId());
                    preparedStatement.setString(5, tpOnboardingAction.getFtp_pwd());
                    preparedStatement.setString(6, tpOnboardingAction.getFtp_directory());
                    preparedStatement.setString(7, tpOnboardingAction.getFtp_conn_method());
                    preparedStatement.setInt(8, tpOnboardingAction.getFtp_resp_time());
                    preparedStatement.setString(9, tpOnboardingAction.getFtp_ssl_req());
                    preparedStatement.setString(10, "N");
                    preparedStatement.setString(11, tpOnboardingAction.getCreated_by());
                    preparedStatement.setTimestamp(12, curdate);
                    preparedStatement.setString(13, "INACTIVE");
                    isProtocolUpdated = isProtocolUpdated + preparedStatement.executeUpdate();
                    if ((isProtocolUpdated > 0) && ("true".equalsIgnoreCase(tpOnboardingAction.getFtp_ssl_req()))) {
                        String selectQuery = "SELECT count(ID)  FROM MSCVP.TPO_SSL where COMMUNICATION_ID=? and PROTOCOL=?";
                        preparedStatement = connection.prepareStatement(selectQuery);
                        preparedStatement.setInt(1, communicationId);
                        preparedStatement.setString(2, commonprotocol);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            StringBuffer ftpSslUpdateQuery = new StringBuffer();
                            if (resultSet.getInt(1) > 0) {
                                ftpSslUpdateQuery.append("Update MSCVP.TPO_SSL set SSL_PRIORITY=?,CIPHER_STRENGTH=?, TP_FLAG = ?,  MODIFIED_BY=?, MODIFIED_TS=? ");
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    ftpSslUpdateQuery.append(",CA_CERTIFICATES=? ,SSL_CERT_DATA=? ");
                                }
                                ftpSslUpdateQuery.append(" WHERE COMMUNICATION_ID= " + communicationId + "");
                                preparedStatement = connection.prepareStatement(ftpSslUpdateQuery.toString());
                                preparedStatement.setString(1, tpOnboardingAction.getSsl_priority2());
                                preparedStatement.setString(2, tpOnboardingAction.getSsl_cipher_stergth2());
                                if (tpOnboardingAction.getUpload1() != null) {
                                    System.out.println("SSL File is uploaded");
                                    preparedStatement.setString(3, "N");

                                } else {
                                    System.out.println("no ssl file to upload");
                                    preparedStatement.setString(3, "U");
                                }
                                preparedStatement.setString(4, tpOnboardingAction.getCreated_by());
                                preparedStatement.setTimestamp(5, curdate);
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    preparedStatement.setString(6, tpOnboardingAction.getCertGroups());
                                    preparedStatement.setString(7, DataSourceDataProvider.getInstance().getCertificatePath(tpOnboardingAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();
                            } else {
                                ftpSslUpdateQuery.append("INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,CIPHER_STRENGTH, TP_FLAG, CREATED_BY, CREATED_TS ");
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    ftpSslUpdateQuery.append(" , CA_CERTIFICATES ,SSL_CERT_DATA ");
                                }
                                ftpSslUpdateQuery.append("  ) VALUES(?,?,?,?,?,?,?,?,? ");
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    ftpSslUpdateQuery.append("  ,?,?) ");
                                } else {
                                    ftpSslUpdateQuery.append("  ) ");
                                }

                                preparedStatement = connection.prepareStatement(ftpSslUpdateQuery.toString());
                                preparedStatement.setInt(1, communicationId);
                                preparedStatement.setInt(2, partnerId);
                                preparedStatement.setString(3, tpOnboardingAction.getTransferMode());
                                preparedStatement.setString(4, tpOnboardingAction.getCommnProtocol());
                                preparedStatement.setString(5, tpOnboardingAction.getSsl_priority2());
                                preparedStatement.setString(6, tpOnboardingAction.getSsl_cipher_stergth2());
                                preparedStatement.setString(7, "N");
                                preparedStatement.setString(8, tpOnboardingAction.getCreated_by());
                                preparedStatement.setTimestamp(9, curdate);
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    preparedStatement.setString(10, tpOnboardingAction.getCertGroups());
                                    preparedStatement.setString(11, DataSourceDataProvider.getInstance().getCertificatePath(tpOnboardingAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();

                            }
                        }
                    }
                } else if (commonprotocol.equalsIgnoreCase("AS2")) {
                    StringBuffer as2UpdateQuery = new StringBuffer();
                    as2UpdateQuery.append("UPDATE MSCVP.TPO_AS2 SET MY_ORG = ?,YOUR_ORG = ?, ");
                    as2UpdateQuery.append("MY_PART_PRO_NAME = ?,YOUR_PART_PRO_NAME = ?,MY_END_POINT = ?,YOUR_END_POINT = ?,STR_AS2_MSG_IN = ?,");
                    as2UpdateQuery.append("WAIT_FOR_SYNC_MDN_PROC = ?,SSL_FLAG=?, TP_FLAG = ?,MODIFIED_BY = ?,MODIFIED_TS = ?, STATUS = ?,TRANSFER_MODE = ? ");
                    if (tpOnboardingAction.getFilepath() != null) {
                        as2UpdateQuery.append(",UPL_YOUR_SYS_CERT=?,SYS_CERT_DATA=? ");
                    }
                    as2UpdateQuery.append(" WHERE COMMUNICATION_ID=" + communicationId);
                    System.out.println("certificate is " + tpOnboardingAction.getAs2_part_cert());
                    preparedStatement = connection.prepareStatement(as2UpdateQuery.toString());
                    preparedStatement.setString(1, tpOnboardingAction.getAs2_myOrgName());
                    preparedStatement.setString(2, tpOnboardingAction.getAs2_partOrgName());
                    preparedStatement.setString(3, tpOnboardingAction.getAs2_myPartname());
                    preparedStatement.setString(4, tpOnboardingAction.getAs2_yourPartname());
                    preparedStatement.setString(5, tpOnboardingAction.getAs2_myEndPoint());
                    preparedStatement.setString(6, tpOnboardingAction.getAs2_partendpoint());
                    preparedStatement.setString(7, tpOnboardingAction.getAs2_strMsg());
                    preparedStatement.setString(8, tpOnboardingAction.getAs2_waitForSync());
                    preparedStatement.setString(9, tpOnboardingAction.getAs2_ssl_req());
                    if (tpOnboardingAction.getUpload() != null) {
                        System.out.println("file is uploaded");
                        preparedStatement.setString(10, "N");
                    } else {
                        System.out.println("no file to upload");
                        preparedStatement.setString(10, "U");
                    }
                    preparedStatement.setString(11, tpOnboardingAction.getCreated_by());
                    preparedStatement.setTimestamp(12, curdate);
                    preparedStatement.setString(13, "INACTIVE");
                    preparedStatement.setString(14, tpOnboardingAction.getAs2_payloadSendMode());
                    if (tpOnboardingAction.getFilepath() != null) {
                        preparedStatement.setString(15, tpOnboardingAction.getFilepath());
                        preparedStatement.setString(16, DataSourceDataProvider.getInstance().getCertificatePath(tpOnboardingAction.getFilepath()));
                    }
                    isProtocolUpdated = isProtocolUpdated + preparedStatement.executeUpdate();
                    if ((isProtocolUpdated > 0) && tpOnboardingAction.getAs2_ssl_req().equalsIgnoreCase("true")) {
                        String selectQuery = "SELECT count(ID)  FROM MSCVP.TPO_SSL where COMMUNICATION_ID=? and PROTOCOL=?";
                        preparedStatement = connection.prepareStatement(selectQuery);
                        preparedStatement.setInt(1, communicationId);
                        preparedStatement.setString(2, commonprotocol);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            if (resultSet.getInt(1) > 0) {
                                StringBuffer as2SslUpdateQuery = new StringBuffer();
                                as2SslUpdateQuery.append("Update MSCVP.TPO_SSL set SSL_PRIORITY=?, CIPHER_STRENGTH=?, TP_FLAG = ?,  MODIFIED_BY=?, MODIFIED_TS=? ");
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    as2SslUpdateQuery.append(",CA_CERTIFICATES=?,SSL_CERT_DATA=? ");
                                }
                                as2SslUpdateQuery.append(" WHERE COMMUNICATION_ID=" + communicationId);
                                preparedStatement = connection.prepareStatement(as2SslUpdateQuery.toString());
                                preparedStatement.setString(1, tpOnboardingAction.getSsl_priority2());
                                preparedStatement.setString(2, tpOnboardingAction.getSsl_cipher_stergth2());
                                if (tpOnboardingAction.getUpload1() != null) {
                                    System.out.println("SSL File is uploaded");
                                    preparedStatement.setString(3, "N");

                                } else {
                                    System.out.println("no ssl file to upload");
                                    preparedStatement.setString(3, "U");
                                }
                                preparedStatement.setString(4, tpOnboardingAction.getCreated_by());
                                preparedStatement.setTimestamp(5, curdate);
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    preparedStatement.setString(6, tpOnboardingAction.getCertGroups());
                                    preparedStatement.setString(7, DataSourceDataProvider.getInstance().getCertificatePath(tpOnboardingAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();
                            } else {
                                StringBuffer as2SslUpdateQuery = new StringBuffer();
                                as2SslUpdateQuery.append("INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,");
                                as2SslUpdateQuery.append(" CIPHER_STRENGTH, TP_FLAG, CREATED_BY, CREATED_TS ");
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    as2SslUpdateQuery.append(" , CA_CERTIFICATES,SSL_CERT_DATA ");
                                }
                                as2SslUpdateQuery.append("  ) VALUES(?,?,?,?,?,?,?,?,? ");
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    as2SslUpdateQuery.append("  ,?,?) ");
                                } else {
                                    as2SslUpdateQuery.append("  ) ");
                                }

                                preparedStatement = connection.prepareStatement(as2SslUpdateQuery.toString());
                                preparedStatement.setInt(1, communicationId);
                                preparedStatement.setInt(2, partnerId);
                                preparedStatement.setString(3, tpOnboardingAction.getTransferMode());
                                preparedStatement.setString(4, tpOnboardingAction.getCommnProtocol());
                                preparedStatement.setString(5, tpOnboardingAction.getSsl_priority2());
                                preparedStatement.setString(6, tpOnboardingAction.getSsl_cipher_stergth2());
                                preparedStatement.setString(7, "N");
                                preparedStatement.setString(8, tpOnboardingAction.getCreated_by());
                                preparedStatement.setTimestamp(9, curdate);
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    preparedStatement.setString(10, tpOnboardingAction.getCertGroups());
                                    preparedStatement.setString(11, DataSourceDataProvider.getInstance().getCertificatePath(tpOnboardingAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();
                            }
                        }
                    }
                } else if (commonprotocol.equalsIgnoreCase("HTTP") && tpOnboardingAction.getTransferMode().equals("get")) {
                    String httpUpdateQuery = "UPDATE MSCVP.TPO_HTTP SET HTTP_END_POINT = ?,HTTP_MODE = ?,"
                            + "RESPONSE_TIMEOUT_SEC = ?,HTTP_PORT = ?,MODIFIED_BY = ?,MODIFIED_TS =?,SSL_FLAG=?, URL=? ,TP_FLAG = ?, STATUS = ? WHERE COMMUNICATION_ID=" + communicationId;
                    preparedStatement = connection.prepareStatement(httpUpdateQuery);
                    preparedStatement.setString(1, tpOnboardingAction.getHttp_endpoint());
                    preparedStatement.setString(2, tpOnboardingAction.getHttp_protocol_mode());
                    preparedStatement.setInt(3, Integer.parseInt(tpOnboardingAction.getHttp_resp_time()));
                    preparedStatement.setInt(4, Integer.parseInt(tpOnboardingAction.getHttp_port()));
                    preparedStatement.setString(5, tpOnboardingAction.getCreated_by());
                    preparedStatement.setTimestamp(6, curdate);
                    preparedStatement.setString(7, tpOnboardingAction.getHttp_ssl_req());
                    preparedStatement.setString(8, tpOnboardingAction.getHttp_url());
                    preparedStatement.setString(9, "N");
                    preparedStatement.setString(10, "INACTIVE");
                    isProtocolUpdated = isProtocolUpdated + preparedStatement.executeUpdate();
                    if ((isProtocolUpdated > 0) && tpOnboardingAction.getHttp_ssl_req().equalsIgnoreCase("true")) {
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
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    httpSslUpdateQuery.append(" ,CA_CERTIFICATES=?,SSL_CERT_DATA=?");
                                }
                                httpSslUpdateQuery.append(" WHERE COMMUNICATION_ID=" + communicationId);
                                preparedStatement = connection.prepareStatement(httpSslUpdateQuery.toString());
                                preparedStatement.setString(1, tpOnboardingAction.getSsl_priority2());
                                preparedStatement.setString(2, tpOnboardingAction.getSsl_cipher_stergth2());
                               if (tpOnboardingAction.getUpload1()!=null) {
                                    System.out.println("SSL File is uploaded");
                                    preparedStatement.setString(3, "N");

                                } else {
                                    System.out.println("no ssl file to upload");
                                    preparedStatement.setString(3, "U");
                                }
                                preparedStatement.setString(4, tpOnboardingAction.getCreated_by());
                                preparedStatement.setTimestamp(5, curdate);
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    preparedStatement.setString(6, tpOnboardingAction.getCertGroups());
                                    preparedStatement.setString(7, DataSourceDataProvider.getInstance().getCertificatePath(tpOnboardingAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();
                            } else {

                                StringBuffer httpSslUpdateQuery = new StringBuffer();
                                httpSslUpdateQuery.append("INSERT INTO MSCVP.TPO_SSL(COMMUNICATION_ID, PARTNER_ID, TRANSFER_MODE, PROTOCOL, SSL_PRIORITY,");
                                httpSslUpdateQuery.append(" CIPHER_STRENGTH, TP_FLAG, CREATED_BY, CREATED_TS ");
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    httpSslUpdateQuery.append(" , CA_CERTIFICATES,SSL_CERT_DATA ");
                                }
                                httpSslUpdateQuery.append("  ) VALUES(?,?,?,?,?,?,?,?,? ");
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    httpSslUpdateQuery.append("  ,?,?) ");
                                } else {
                                    httpSslUpdateQuery.append("  ) ");
                                }
                                preparedStatement = connection.prepareStatement(httpSslUpdateQuery.toString());
                                preparedStatement.setInt(1, communicationId);
                                preparedStatement.setInt(2, partnerId);
                                preparedStatement.setString(3, tpOnboardingAction.getTransferMode());
                                preparedStatement.setString(4, tpOnboardingAction.getCommnProtocol());
                                preparedStatement.setString(5, tpOnboardingAction.getSsl_priority2());
                                preparedStatement.setString(6, tpOnboardingAction.getSsl_cipher_stergth2());
                                preparedStatement.setString(7, "N");
                                preparedStatement.setString(8, tpOnboardingAction.getCreated_by());
                                preparedStatement.setTimestamp(9, curdate);
                                if (tpOnboardingAction.getCertGroups() != null) {
                                    preparedStatement.setString(10, tpOnboardingAction.getCertGroups());
                                    preparedStatement.setString(11, DataSourceDataProvider.getInstance().getCertificatePath(tpOnboardingAction.getCertGroups()));
                                }
                                preparedStatement.executeUpdate();
                            }
                        }
                    }
                } else if (commonprotocol.equalsIgnoreCase("SFTP") && tpOnboardingAction.getTransferMode().equals("put")) {

                    StringBuffer sftpUpdateQuery = new StringBuffer();
                    sftpUpdateQuery.append("UPDATE MSCVP.TPO_SFTP SET CONN_METHOD = ?,MY_SSH_PUB_KEY = ?,REMOTE_HOST_IP_ADD = ?,REMOTE_USERID = ?");
                    sftpUpdateQuery.append(",METHOD = ?,AUTH_METHOD = ?,REMOTE_PORT = ?,REMOTE_PWD = ?,DIRECTORY = ?,MODIFIED_BY = ?,MODIFIED_TS = ?,TP_FLAG = ?, STATUS = ?");
                    if (tpOnboardingAction.getFilepath() != null) {
                        sftpUpdateQuery.append(" ,UPL_YOUR_SSH_PUB_KEY = ?,SSH_CERT_DATA=? ");
                    }
                    sftpUpdateQuery.append(" WHERE COMMUNICATION_ID=" + communicationId);

                    preparedStatement = connection.prepareStatement(sftpUpdateQuery.toString());
                    preparedStatement.setString(1, tpOnboardingAction.getSftp_conn_method());
                    preparedStatement.setString(2, tpOnboardingAction.getSftp_public_key());
                    preparedStatement.setString(3, tpOnboardingAction.getSftp_host_ip());
                    preparedStatement.setString(4, tpOnboardingAction.getSftp_remote_userId());
                    preparedStatement.setString(5, tpOnboardingAction.getSftp_method());
                    preparedStatement.setString(6, tpOnboardingAction.getSftp_auth_method());
                    preparedStatement.setString(7, tpOnboardingAction.getSftp_remote_port());
                    preparedStatement.setString(8, tpOnboardingAction.getSftp_remote_pwd());
                    preparedStatement.setString(9, tpOnboardingAction.getSftp_directory());
                    preparedStatement.setString(10, tpOnboardingAction.getCreated_by());
                    preparedStatement.setTimestamp(11, curdate);
                    if (tpOnboardingAction.getUpload() != null) {
                        System.out.println("SSL File is uploaded");
                        preparedStatement.setString(12, "N");

                    } else {
                        System.out.println("no ssl file to upload");
                        preparedStatement.setString(12, "U");
                    }
                    preparedStatement.setString(13, "INACTIVE");
                    if (tpOnboardingAction.getFilepath() != null) {
                        preparedStatement.setString(14, tpOnboardingAction.getFilepath());
                        preparedStatement.setString(15, DataSourceDataProvider.getInstance().getCertificatePath(tpOnboardingAction.getFilepath()));
                    }
                    isProtocolUpdated = isProtocolUpdated + preparedStatement.executeUpdate();
                } else if (commonprotocol.equalsIgnoreCase("SMTP")) {
                    String smtpUpdateQuery = "UPDATE MSCVP.TPO_SMTP SET SMTP_SERVER_PORT = ?,TO_EMAIL_ADDRESS = ?,"
                            + "SMTP_SERVER_HOST = ?,FROM_EMAIL_ADDRESS = ?, MODIFIED_BY = ?,MODIFIED_TS = ?,TP_FLAG = ?, STATUS = ? WHERE COMMUNICATION_ID=" + communicationId;
                    preparedStatement = connection.prepareStatement(smtpUpdateQuery);
                    preparedStatement.setInt(1, Integer.parseInt(tpOnboardingAction.getSmtp_server_port()));
                    preparedStatement.setString(2, tpOnboardingAction.getSmtp_to_email());
                    preparedStatement.setString(3, tpOnboardingAction.getSmtp_server_protocol());
                    preparedStatement.setString(4, tpOnboardingAction.getSmtp_from_email());
                    preparedStatement.setString(5, tpOnboardingAction.getCreated_by());
                    preparedStatement.setTimestamp(6, curdate);
                    preparedStatement.setString(7, "N");
                    preparedStatement.setString(8, "INACTIVE");
                    isProtocolUpdated = isProtocolUpdated + preparedStatement.executeUpdate();
                }
            }
            mp = tpOnboardingAction.getMap();
            if (((isProtocolUpdated > 0))) {
                responseString = "<font color='green'>Updated successfully</font>";
                //tpOnboardingAction.getCreated_by() is login id..
                MailManager.tpoDetails(partnerId, tpOnboardingAction.getCreated_by(), Email, tpOnboardingAction.getPartnerName(), tpOnboardingAction.getCommnProtocol(), tpOnboardingAction.getTransferMode(),
                        tpOnboardingAction.getFilepath(), tpOnboardingAction.getCertGroups(), mp, "Update");
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

    public String getDeleteProfile(int communicationId, String commnProtocol, int PartnerId, String transferMode) throws ServiceLocatorException {
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

    @Override
    public ArrayList<TpOnboardingBean> tpoSearchEnvelope(String loginId, int partnerId, String flag, TpOnboardingAction tpAction) {
        StringBuffer envelopeSearchQuery = new StringBuffer();
        envelopeSearchQuery.append("SELECT TRANSACTION, DIRECTION, CREATED_BY, CREATED_TS FROM MSCVP.TPO_ENVELOPES WHERE 1=1 ");
        if ("searchFlag".equals(flag)) {
            if (tpAction.getTransaction() != null && !"-1".equals(tpAction.getTransaction().trim())) {
                envelopeSearchQuery.append(" AND TRANSACTION='" + tpAction.getTransaction() + "' ");
            }
            if (tpAction.getDirection() != null && !"-1".equals(tpAction.getDirection().trim())) {
                envelopeSearchQuery.append(" AND DIRECTION='" + tpAction.getDirection() + "' ");
            }
        }
        envelopeSearchQuery.append(" AND PARTNER_ID='" + partnerId + "' AND CREATED_BY = '" + loginId + "' ");
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(envelopeSearchQuery.toString());
            tpoSearchEnvelopeList = new ArrayList<TpOnboardingBean>();

            while (resultSet.next()) {
                TpOnboardingBean tpOnboardingBean = new TpOnboardingBean();
                tpOnboardingBean.setTransaction(resultSet.getString("TRANSACTION"));
                tpOnboardingBean.setDirection(resultSet.getString("DIRECTION"));
                tpOnboardingBean.setCreated_by(resultSet.getString("CREATED_BY"));
                tpOnboardingBean.setCreated_ts(resultSet.getTimestamp("CREATED_TS"));
                tpoSearchEnvelopeList.add(tpOnboardingBean);
            }
        } catch (Exception ex) {
            Logger.getLogger(TpOnboardingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tpoSearchEnvelopeList;
    }

    public String addTpoEnvelope(int partnerId, TpOnboardingAction tpAction) throws ServiceLocatorException {
        int isEnevelopInserted = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String envelopsInsertQuery = "INSERT INTO TPO_ENVELOPES (PARTNER_ID, TRANSACTION, DIRECTION,  SENDERID_ISA,"
                    + " SENDERID_GS, SENDERID_ST, RECEIVERID_ISA, RECEIVERID_GS, RECEIVERID_ST, VERSION_ISA, "
                    + "VERSION_GS, VERSION_ST, FUNCTIONAL_ID_CODE_GS, RESPONSIBLE_AGENCY_CODE_GS, GENERATE_AN_ACKNOWLEDGEMENT_GS, "
                    + "TRANSACTION_SET_ID_CODE_ST, TP_FLAG, CREATED_BY, CREATED_TS) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

            String allTransactions = "";
            if (tpAction.isIb850() == true) {
                allTransactions = allTransactions + tpAction.getIB850Transaction() + "#";
            }
            if (tpAction.isIb855() == true) {
                allTransactions = allTransactions + tpAction.getIB855Transaction() + "#";
            }
            if (tpAction.isIb856() == true) {
                allTransactions = allTransactions + tpAction.getIB856Transaction() + "#";
            }
            if (tpAction.isIb810() == true) {
                allTransactions = allTransactions + tpAction.getIB810Transaction() + "#";
            }
            if (tpAction.isOb850() == true) {
                allTransactions = allTransactions + tpAction.getOB850Transaction() + "#";
            }
            if (tpAction.isOb855() == true) {
                allTransactions = allTransactions + tpAction.getOB855Transaction() + "#";
            }
            if (tpAction.isOb856() == true) {
                allTransactions = allTransactions + tpAction.getOB856Transaction() + "#";
            }
            if (tpAction.isOb810() == true) {
                allTransactions = allTransactions + tpAction.getOB810Transaction() + "#";
            }

            if ((tpAction.isIb850() == true) || (tpAction.isIb855() == true) || (tpAction.isIb856() == true) || (tpAction.isIb810() == true) || (tpAction.isOb850() == true) || (tpAction.isOb855() == true) || (tpAction.isOb856() == true) || (tpAction.isOb810() == true)) {
                String transactionsSplit[] = allTransactions.substring(0, allTransactions.length() - 1).split(Pattern.quote("#"));

                for (int i = 0; i < transactionsSplit.length; i++) {
                    String envelopData[] = transactionsSplit[i].substring(0, transactionsSplit[i].length()).split(Pattern.quote("@"));
                    preparedStatement = connection.prepareStatement(envelopsInsertQuery);
                    preparedStatement.setInt(1, partnerId);
                    preparedStatement.setString(2, envelopData[0]);
                    preparedStatement.setString(3, envelopData[1]);
                    preparedStatement.setString(4, envelopData[2]);
                    preparedStatement.setString(5, envelopData[3]);
                    preparedStatement.setString(6, envelopData[4]);
                    preparedStatement.setString(7, envelopData[5]);
                    preparedStatement.setString(8, envelopData[6]);
                    preparedStatement.setString(9, envelopData[7]);
                    preparedStatement.setString(10, envelopData[8]);
                    preparedStatement.setString(11, envelopData[9]);
                    preparedStatement.setString(12, envelopData[10]);
                    preparedStatement.setString(13, envelopData[11]);
                    preparedStatement.setString(14, envelopData[12]);
                    preparedStatement.setString(15, envelopData[13]);
                    preparedStatement.setString(16, envelopData[14]);
                    preparedStatement.setString(17, "N");
                    preparedStatement.setString(18, tpAction.getCreated_by());
                    preparedStatement.setTimestamp(19, curdate);
                    isEnevelopInserted = isEnevelopInserted + preparedStatement.executeUpdate();
                }
            }

            //       mp = tpAction.getMap();
            if (isEnevelopInserted > 0) {
                responseString = "<font color='green'>Inserted successfully</font>";
//                MailManager.tpoDetails(partnerId, tpAction.getPartnerName(), tpAction.getContactName(), tpAction.getContactEmail(),
//                        tpAction.getPhoneNo(), tpAction.getCountry(), tpAction.getCommnProtocol(), tpAction.getTransferMode(),
//                        tpAction.getFilepath(), tpAction.getCertGroups(), mp, tpAction.isIb850(), tpAction.isIb855(), tpAction.isIb856(), tpAction.isIb810(),
//                        tpAction.isOb850(), tpAction.isOb855(), tpAction.isOb856(), tpAction.isOb810(), "Insert");
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

    public TpOnboardingBean tpoEditEnvelope(int partnerId, String transaction, String direction) throws ServiceLocatorException {
        try {
            tpOnboardingBean = new TpOnboardingBean();
            connection = ConnectionProvider.getInstance().getConnection();
            String editEnvelopeQuery = "SELECT PARTNER_ID,TRANSACTION,DIRECTION,PROTOCOL,SENDERID_ISA,SENDERID_GS,SENDERID_ST,RECEIVERID_ISA,RECEIVERID_GS,RECEIVERID_ST,"
                    + "VERSION_ISA,VERSION_GS,VERSION_ST,FUNCTIONAL_ID_CODE_GS,RESPONSIBLE_AGENCY_CODE_GS,GENERATE_AN_ACKNOWLEDGEMENT_GS,"
                    + "TRANSACTION_SET_ID_CODE_ST,TP_FLAG,CREATED_BY,CREATED_TS,MODIFIED_BY,MODIFIED_TS "
                    + "FROM TPO_ENVELOPES WHERE TRANSACTION = '" + transaction + "' AND lcase(DIRECTION) like lcase('%" + direction + "%') AND PARTNER_ID = " + partnerId + "";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(editEnvelopeQuery);
            while (resultSet.next()) {
                tpOnboardingBean.setTransaction(resultSet.getString("TRANSACTION"));
                tpOnboardingBean.setDirection(resultSet.getString("DIRECTION"));
                tpOnboardingBean.setIsaSenderId(resultSet.getString("SENDERID_ISA"));
                tpOnboardingBean.setGsSenderId(resultSet.getString("SENDERID_GS"));
                tpOnboardingBean.setStSenderId(resultSet.getString("SENDERID_ST"));
                tpOnboardingBean.setIsaReceiverId(resultSet.getString("RECEIVERID_ISA"));
                tpOnboardingBean.setGsReceiverId(resultSet.getString("RECEIVERID_GS"));
                tpOnboardingBean.setStReceiverId(resultSet.getString("RECEIVERID_ST"));
                tpOnboardingBean.setIsaVersion(resultSet.getString("VERSION_ISA"));
                tpOnboardingBean.setGsVersion(resultSet.getString("VERSION_GS"));
                tpOnboardingBean.setStVersion(resultSet.getString("VERSION_ST"));
                tpOnboardingBean.setFunctionalGroupId(resultSet.getString("FUNCTIONAL_ID_CODE_GS"));
                tpOnboardingBean.setResponsibleAgencyCode(resultSet.getString("RESPONSIBLE_AGENCY_CODE_GS"));
                if ("true".equalsIgnoreCase(resultSet.getString("GENERATE_AN_ACKNOWLEDGEMENT_GS"))) {
                    tpOnboardingBean.setGenerateAcknowledgement(true);
                } else {
                    tpOnboardingBean.setGenerateAcknowledgement(false);
                }
                tpOnboardingBean.setTransactionSetIdCode(resultSet.getString("TRANSACTION_SET_ID_CODE_ST"));
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
        return tpOnboardingBean;
    }

    public String doResetMyPassword(int roleId, String loginId, String pwd) throws ServiceLocatorException {
        int istpoMyPwdUpdated = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        String encryptedPwd = PasswordUtil.encryptPwd(pwd);
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String updateTpoMyPwdQuery = ("UPDATE MSCVP.TPO_USER SET PASSWORD = ?,MODIFIED_BY=?, MODIFIED_TS=? WHERE LOGINID =? AND ROLE_ID=?");
            preparedStatement = connection.prepareStatement(updateTpoMyPwdQuery);
            preparedStatement.setString(1, encryptedPwd);
            preparedStatement.setString(2, loginId);
            preparedStatement.setTimestamp(3, curdate);
            preparedStatement.setString(4, loginId);
            preparedStatement.setInt(5, roleId);
            istpoMyPwdUpdated = istpoMyPwdUpdated + preparedStatement.executeUpdate();
            if (istpoMyPwdUpdated > 0) {
                responseString = "<font color='green'>Your password updated successfully</font>";
            } else {
                responseString = "<font color='red'>Please try again!</font>";
            }
        } catch (Exception e) {
            responseString = "<font color='red'>Please try again!</font>";
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
                se.printStackTrace();
                throw new ServiceLocatorException(se);
            }
        }
        return responseString;
    }

    public String doTpoResetUserPwd(String loginId, String userLoginId, String pwd) throws ServiceLocatorException {
        int istpoUserPwdUpdated = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        String encryptedPwd = PasswordUtil.encryptPwd(pwd);
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String updateTpoUserPwdQuery = ("UPDATE MSCVP.TPO_USER SET PASSWORD = ?,MODIFIED_BY=?, MODIFIED_TS=? WHERE LOGINID =?");
            preparedStatement = connection.prepareStatement(updateTpoUserPwdQuery);
            preparedStatement.setString(1, encryptedPwd);
            preparedStatement.setString(2, loginId);
            preparedStatement.setTimestamp(3, curdate);
            preparedStatement.setString(4, userLoginId);
            istpoUserPwdUpdated = istpoUserPwdUpdated + preparedStatement.executeUpdate();
            if (istpoUserPwdUpdated > 0) {
                responseString = "<font color='green'>User password updated successfully</font>";
                String email = DataSourceDataProvider.getInstance().getEmaiIdByloginId(userLoginId);
                MailManager.resetUserPwd(userLoginId, email, PasswordUtil.decryptPwd(encryptedPwd));
            } else {
                responseString = "<font color='red'>Please try again!</font>";
            }
        } catch (Exception e) {
            responseString = "<font color='red'>Please try again!</font>";
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
                se.printStackTrace();
                throw new ServiceLocatorException(se);
            }
        }
        return responseString;
    }

    public String doTpoResetPartnerPwd(String loginId, String partnreName, String pwd) throws ServiceLocatorException {
        int istpoPartnerPwdUpdated = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        String encryptedPwd = PasswordUtil.encryptPwd(pwd);
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String updateTpoPartnerPwdQuery = ("UPDATE MSCVP.TPO_USER SET PASSWORD = ?,MODIFIED_BY=?, MODIFIED_TS=? WHERE PARTNER_ID =? AND ROLE_ID=3");
            preparedStatement = connection.prepareStatement(updateTpoPartnerPwdQuery);
            preparedStatement.setString(1, encryptedPwd);
            preparedStatement.setString(2, loginId);
            preparedStatement.setTimestamp(3, curdate);
            preparedStatement.setInt(4, Integer.parseInt(partnreName));
            istpoPartnerPwdUpdated = istpoPartnerPwdUpdated + preparedStatement.executeUpdate();
            if (istpoPartnerPwdUpdated > 0) {
                responseString = "<font color='green'>User password updated successfully</font>";
            } else {
                responseString = "<font color='red'>Please try again!</font>";
            }
        } catch (Exception e) {
            responseString = "<font color='red'>Please try again!</font>";
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
                se.printStackTrace();
                throw new ServiceLocatorException(se);
            }
        }
        return responseString;
    }

    public String getDeleteEnvelope(int id, String transaction, String direction) throws ServiceLocatorException {
        int count = 0;
        String responseString = "";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String query = "DELETE FROM MSCVP.TPO_ENVELOPES WHERE PARTNER_ID =" + id + " AND TRANSACTION='" + transaction + "' AND DIRECTION='" + direction + "'";
            preparedStatement = connection.prepareStatement(query);
            count = preparedStatement.executeUpdate();
            if (count > 0) {
                responseString = "<font color='green'>Envelope deleted sucessfully</font>";
            } else {
                responseString = "<font color='red'>Envelope delete filaed</font>";
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
}
