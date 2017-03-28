/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.partner;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Narendar
 */
public class PartnerServiceImpl implements PartnerService {

    private HttpServletRequest httpServletRequest;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    String responseString = null;
    PartnerBean partnerBean = null;
    private ArrayList<PartnerBean> tpoSearchPartnersList;

    public PartnerBean getPartnerInfo(int partnerId, String loginId) throws ServiceLocatorException {
        try {
            partnerBean = new PartnerBean();
            connection = ConnectionProvider.getInstance().getConnection();
            String partnerInfoQuery = "SELECT NAME, PHONE_NO, EMAIL, ADDRESS, CITY, STATE, COUNTRY, ZIPCODE, CREATED_BY, CREATED_TS, TP_FLAG FROM MSCVP.TPO_PARTNERS WHERE ID=?";
            preparedStatement = connection.prepareStatement(partnerInfoQuery);
            preparedStatement.setInt(1, partnerId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                partnerBean.setPartnerName(resultSet.getString("NAME"));
                partnerBean.setPhoneNo(resultSet.getString("PHONE_NO"));
                partnerBean.setAddress1(resultSet.getString("ADDRESS"));
                partnerBean.setCity(resultSet.getString("CITY"));
                partnerBean.setState(resultSet.getString("STATE"));
                partnerBean.setCountry(resultSet.getString("COUNTRY"));
                partnerBean.setZipCode(resultSet.getString("ZIPCODE"));
                partnerBean.setContactEmail(resultSet.getString("EMAIL"));
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
        return partnerBean;
    }

    public String doAddPartnerUser(int partnerId, int roleId, String loginId, PartnerAction tpAction) throws ServiceLocatorException {
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
                    + "STATE, ZIPCODE, ADDRESS, PHONE_NO, COUNTRY, ACTIVE, PARTNER_ID, CREATED_BY, CREATED_TS, LOGIN_ACCESS)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(tpoAddPartnerUserQuery);
            preparedStatement.setString(1, userLoginId);
            preparedStatement.setString(2, generatedPassword);
            preparedStatement.setString(3, tpAction.getContactName());
            preparedStatement.setString(4, tpAction.getContactLastName());
            preparedStatement.setString(5, email);
            preparedStatement.setInt(6, tpAction.getPartnerRole());
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
            preparedStatement.setString(17, "N");
            istpoUserInserted = istpoUserInserted + preparedStatement.executeUpdate();
            if (istpoUserInserted > 0) {
                responseString = "<font color='green'>User created successfully</font>";
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

    public String updatePartnerInfo(PartnerAction tpAction, String loginId, int partnerId) throws ServiceLocatorException {
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

    public ArrayList<PartnerBean> tpoSearchPartners(String loginId, int roleId, String flag, PartnerAction tpAction) {
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
            tpoSearchPartnersList = new ArrayList<PartnerBean>();
            while (resultSet.next()) {
                PartnerBean partnerBean = new PartnerBean();
                partnerBean.setId(resultSet.getInt("ID"));
                partnerBean.setPartnerName(resultSet.getString("NAME"));
                partnerBean.setPhoneNo(resultSet.getString("PHONE_NO"));
                partnerBean.setStatus(resultSet.getString("STATUS"));
                partnerBean.setCity(resultSet.getString("CITY"));
                partnerBean.setState(resultSet.getString("STATE"));
                partnerBean.setCountry(resultSet.getString("COUNTRY"));
                partnerBean.setZipCode(resultSet.getString("ZIPCODE"));
                partnerBean.setCreated_by(resultSet.getString("CREATED_BY"));
                partnerBean.setCreated_ts(resultSet.getTimestamp("CREATED_TS"));
                tpoSearchPartnersList.add(partnerBean);
            }
        } catch (Exception ex) {
            Logger.getLogger(PartnerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tpoSearchPartnersList;
    }

}
