/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.user;

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
public class UserServiceImpl implements UserService {

    private HttpServletRequest httpServletRequest;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    String responseString = null;
    UserBean userBean = null;
    private ArrayList<UserBean> tpoSearchUsersList;

    public String doTpoUserRegister(int partnerId, int roleId, String loginId, UserAction userAction) throws ServiceLocatorException {
        int istpoUserInserted = 0;
        String email = userAction.getRegcontactEmail();
        PasswordUtil passwordUtil = new PasswordUtil();
        String password = RandomPasswordGenerator.generatePswd(4, 7, 2, 2, 0);
        String generatedPassword = passwordUtil.encryptPwd(password);
        String userloginId = email.substring(0, email.indexOf("@")).toLowerCase();
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        String createdBy = userAction.getCreated_by();
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String tpoRegisterQuery = "INSERT INTO MSCVP.TPO_USER(LOGINID, PASSWORD, PARTNER_ID, NAME, EMAIL, "
                    + "PHONE_NO, COUNTRY, CREATED_BY, CREATED_TS,ROLE_ID,LNAME,CITY,STATE, ZIPCODE, ADDRESS, ACTIVE, LOGIN_ACCESS)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(tpoRegisterQuery);
            preparedStatement.setString(1, userloginId);
            preparedStatement.setString(2, generatedPassword);
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, userAction.getRegcontactName());
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, userAction.getRegphoneNo());
            preparedStatement.setString(7, userAction.getRegcountry());
            preparedStatement.setString(8, createdBy);
            preparedStatement.setTimestamp(9, curdate);
            if (roleId == 1) {  //main admin roleId
                preparedStatement.setInt(10, 2);//main admin user
            }
            preparedStatement.setString(11, userAction.getRegcontactLName());
            preparedStatement.setString(12, userAction.getRegcity());
            preparedStatement.setString(13, userAction.getRegstate());
            preparedStatement.setString(14, userAction.getRegzipCode());
            preparedStatement.setString(15, userAction.getRegaddress());
            preparedStatement.setString(16, "A");
            preparedStatement.setString(17, "N");
            istpoUserInserted = istpoUserInserted + preparedStatement.executeUpdate();
            if (istpoUserInserted > 0) {
                responseString = "<font color='green'>User created successfully</font>";
                MailManager.tpoUserIdPwd(userAction.getRegcontactName(), userAction.getRegpartnerName(), userAction.getRegcontactEmail(), userloginId, passwordUtil.decryptPwd(generatedPassword));
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

    public ArrayList<UserBean> tpoSearchUsers(String loginId, int roleId, String flag, UserAction userAction) {
        StringBuffer userSearchQuery = new StringBuffer();
        userSearchQuery.append("SELECT ID, LOGINID, PASSWORD, PARTNER_ID, NAME, EMAIL, PHONE_NO, COUNTRY, CREATED_BY "
                + ", CREATED_TS, ROLE_ID, LNAME, CITY,STATE, ZIPCODE, ADDRESS, ACTIVE FROM MSCVP.TPO_USER WHERE 1=1 ");
        if ("searchFlag".equals(flag)) {
            if (userAction.getContactName() != null && !"".equals(userAction.getContactName().trim())) {
                userSearchQuery.append(" AND lcase(NAME) like lcase('%" + (userAction.getContactName()) + "%') ");
            }
            if (userAction.getCountry() != null && !"-1".equals(userAction.getCountry().trim())) {
                userSearchQuery.append(" AND COUNTRY='" + userAction.getCountry() + "' ");
            }
            if (userAction.getStatus() != null && !"-1".equals(userAction.getStatus().trim())) {
                userSearchQuery.append(" AND ACTIVE='" + userAction.getStatus() + "' ");
            }
        }
        if (roleId == 1) {
            userSearchQuery.append(" AND ROLE_ID = 2 ");
        } else if (roleId == 3) {
            userSearchQuery.append(" AND ((ROLE_ID = 4) OR (ROLE_ID = 5)) ");
        } else if (roleId == 4) {
            userSearchQuery.append(" AND CREATED_BY='" + loginId + "' AND ROLE_ID = 5 ");
        }
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(userSearchQuery.toString());
            tpoSearchUsersList = new ArrayList<UserBean>();
            while (resultSet.next()) {
                UserBean userBean = new UserBean();
                userBean.setId(resultSet.getInt("ID"));
                userBean.setContactName(resultSet.getString("NAME"));
                userBean.setRoleId(resultSet.getInt("ROLE_ID"));
                userBean.setRoleName(DataSourceDataProvider.getInstance().getRoleNameByRoleId((resultSet.getInt("ROLE_ID"))));
                userBean.setPhoneNo(resultSet.getString("PHONE_NO"));
                userBean.setStatus(resultSet.getString("ACTIVE"));
                userBean.setCity(resultSet.getString("CITY"));
                userBean.setState(resultSet.getString("STATE"));
                userBean.setCountry(resultSet.getString("COUNTRY"));
                userBean.setZipCode(resultSet.getString("ZIPCODE"));
                userBean.setCreated_by(resultSet.getString("CREATED_BY"));
                userBean.setCreated_ts(resultSet.getTimestamp("CREATED_TS"));
                tpoSearchUsersList.add(userBean);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tpoSearchUsersList;
    }

    public String doResetMyPassword(int roleId, String loginId, String pwd) throws ServiceLocatorException {
        int istpoMyPwdUpdated = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        String encryptedPwd = PasswordUtil.encryptPwd(pwd);
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            String updateTpoMyPwdQuery = ("UPDATE MSCVP.TPO_USER SET PASSWORD = ?,MODIFIED_BY=?, MODIFIED_TS=?, LOGIN_ACCESS=? WHERE LOGINID =? AND ROLE_ID=?");
            preparedStatement = connection.prepareStatement(updateTpoMyPwdQuery);
            preparedStatement.setString(1, encryptedPwd);
            preparedStatement.setString(2, loginId);
            preparedStatement.setTimestamp(3, curdate);
            preparedStatement.setString(4, "Y");
            preparedStatement.setString(5, loginId);
            preparedStatement.setInt(6, roleId);
            istpoMyPwdUpdated = istpoMyPwdUpdated + preparedStatement.executeUpdate();
            if (istpoMyPwdUpdated > 0) {
                responseString = "<font color='green'>Password reset successfully. Please logout and login again</font>";
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
                String userName = DataSourceDataProvider.getInstance().getuserNameByUserId(userLoginId);
                responseString = "<font color='green'>'"+userName+"' password reset successfully</font>";
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
                 String partnerName1 = DataSourceDataProvider.getInstance().getTpoPartnerName(Integer.parseInt(partnreName));
                responseString = "<font color='green'>'"+partnerName1+"' password reset successfully</font>";
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
}
