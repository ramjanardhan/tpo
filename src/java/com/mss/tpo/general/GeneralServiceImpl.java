package com.mss.tpo.general;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.mss.tpo.util.ConnectionProvider;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.ServiceLocatorException;

/**
 *
 * @author Narendar
 */
public class GeneralServiceImpl implements GeneralService {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private static Logger logger = Logger.getLogger(GeneralServiceImpl.class.getName());

    /**
     * constructor of GeneralServiceIml
     */
    public GeneralServiceImpl() {
    }

    /*
     * (non-Javadoc)
     * @see com.bcbsm.edi.general.GeneralService#getUserInfo(java.lang.String)
     */
    //@Override
    public UserInfoBean tpLoginCheck(String loginId) throws ServiceLocatorException {
        UserInfoBean userInfoBean = null;
        String tpoLoginQuery = "SELECT ID, LOGINID, PASSWORD, ROLE_ID, ACTIVE, PARTNER_ID, NAME, EMAIL, PHONE_NO, COUNTRY, "
                + "LAST_LOGIN_TS, LAST_LOGOUT_TS,LOGIN_ACCESS FROM TPO_USER WHERE LOGINID=?";
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(tpoLoginQuery);
            preparedStatement.setString(1, loginId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userInfoBean = new UserInfoBean();
                userInfoBean.setUserId(resultSet.getInt("ID"));
                userInfoBean.setLoginId(resultSet.getString("LOGINID"));
                userInfoBean.setPwd(resultSet.getString("PASSWORD"));
                int roleId = (resultSet.getInt("ROLE_ID"));
                userInfoBean.setRoleId(roleId);
                userInfoBean.setPartnerId(resultSet.getInt("PARTNER_ID"));
                if ((roleId == 1) || (roleId == 2)) {
                    userInfoBean.setPartnerName("TPO_ADMIN");
                } else {
                    userInfoBean.setPartnerName(DataSourceDataProvider.getInstance().getTpoPartnerName(resultSet.getInt("PARTNER_ID")));
                }
                userInfoBean.setContactName(resultSet.getString("NAME"));
                userInfoBean.setContactEmail(resultSet.getString("EMAIL"));
                userInfoBean.setPhoneNo(resultSet.getString("PHONE_NO"));
                userInfoBean.setCountry(resultSet.getString("COUNTRY"));
                userInfoBean.setActive(resultSet.getString("ACTIVE"));
                userInfoBean.setLoginAccess(resultSet.getString("LOGIN_ACCESS"));
                userInfoBean.setLastLoginTS(resultSet.getTimestamp("LAST_LOGIN_TS"));
                userInfoBean.setLastLogoutTS(resultSet.getTimestamp("LAST_LOGOUT_TS"));
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
        return userInfoBean;
    }
}
