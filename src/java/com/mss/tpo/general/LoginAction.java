package com.mss.tpo.general;

import com.mss.tpo.tpOnboarding.TpOnboardingBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.mss.tpo.util.AppConstants;
import com.mss.tpo.util.ConnectionProvider;
import com.mss.tpo.util.DateUtility;
import com.mss.tpo.util.PasswordUtil;
import com.mss.tpo.util.Properties;
import com.mss.tpo.util.ServiceLocator;
import com.mss.tpo.util.ServiceLocatorException;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import org.apache.log4j.Logger;

public class LoginAction extends ActionSupport implements ServletRequestAware {

    private static Logger logger = Logger.getLogger(LoginAction.class.getName());
    private String resultType = SUCCESS;
    private HttpServletRequest httpServletRequest;
    private String loginId;
    private String password;

    public String tpoLoginCheck() throws Exception {
        resultType = LOGIN;
        // HttpSession tpoUserSession = httpServletRequest.getSession(true);
        UserInfoBean userInfoBean = null;
        try {
            userInfoBean = ServiceLocator.getGeneralService().tpLoginCheck(getLoginId().trim().toLowerCase());
            if (userInfoBean != null) {
                String decryptedPwd = PasswordUtil.decryptPwd(userInfoBean.getPwd().trim());
                if (getLoginId().equalsIgnoreCase(userInfoBean.getLoginId()) && getPassword().equalsIgnoreCase(decryptedPwd)) {
                    if ("A".equals(userInfoBean.getActive())) {
                        httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_LOGIN_ID, userInfoBean.getLoginId());
                        httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_ROLE_ID, userInfoBean.getRoleId());
                        httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_PARTNER_ID, userInfoBean.getPartnerId());
                        httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_PARTNER_NAME, userInfoBean.getPartnerName());
                        httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_CONTACT_NAME, userInfoBean.getContactName());
                        httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_COUNTRY, userInfoBean.getCountry());
                        httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_EMAIL, userInfoBean.getContactEmail());
                        httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_PHONE_NO, userInfoBean.getPhoneNo());
                        httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_USERID, userInfoBean.getUserId());
                        //httpServletRequest.getSession(true).setAttribute(AppConstants.TPO_PARTNER_NAME,tpOnboardingBean.getPartnerName());
                        logUserAccess();
                        resultType = SUCCESS;
                    } else {
                        httpServletRequest.setAttribute(AppConstants.REQ_ERROR_INFO, "<span class=\"resultFailure\">You are currently not an active user.</span>");
                        resultType = "input";
                    }
                } else {
                    httpServletRequest.setAttribute(AppConstants.REQ_ERROR_INFO, "<span class=\"resultFailure\">Please Login with valid UserId and Password! </span>");
                    resultType = "input";
                }
            } else {
                httpServletRequest.setAttribute(AppConstants.REQ_ERROR_INFO, "<span class=\"resultFailure\">Please Login with valid UserId and Password! </span>");
                resultType = "input";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resultType = "error";
        }
        return resultType;
    }

    public void logUserAccess() throws Exception {
        try {
            if (getHttpServletRequest().getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID) != null) {
                String loginId = getHttpServletRequest().getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                String forwarded = httpServletRequest.getHeader("X-FORWARDED-FOR");
                String via = httpServletRequest.getHeader("VIA");
                String remote = httpServletRequest.getRemoteAddr();
                String agent = httpServletRequest.getHeader("User-Agent");
                String location = httpServletRequest.getLocalAddr();

                Timestamp accessedtime = DateUtility.getInstance().getCurrentDB2Timestamp();
                Connection connection = null;
                Statement stmt = null;
                ResultSet resultSet = null;
                boolean isInserted = false;
                String query = null;
                try {
                    connection = ConnectionProvider.getInstance().getConnection();
                    // query = "insert into LOGUSERACCESS(LoginId,X_FORWARDED_FOR1,VIA, REMOTE_ADDR,User_Agent,DateAccessed)"
                    query = "INSERT INTO MSCVP.LOGUSERACCESS (LOGINID, X_FORWARDED_FOR1, VIA, REMOTE_ADDR, USER_AGENT, DATEACCESSED) "
                            + " values('" + loginId + "','" + forwarded + "','" + via + "','" + remote + "','" + agent + "','" + accessedtime + "')";
                    stmt = connection.createStatement();
                    int x = stmt.executeUpdate(query);
                    stmt.close();
                    if (x > 0) {
                        isInserted = true;
                    }
                } catch (SQLException sql) {
                    sql.printStackTrace();
                    throw new ServiceLocatorException(sql);
                } finally {
                    try {
                        if (stmt != null) {
                            stmt.close();
                            stmt = null;
                        }
                        if (connection != null) {
                            connection.close();
                            connection = null;
                        }
                    } catch (SQLException sqle) {
                        throw new ServiceLocatorException(sqle);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            getHttpServletRequest().getSession(false).setAttribute("errorMessage", ex.toString());
            resultType = ERROR;
        }
    }

    /**
     * method is used to invalidate session
     */
    public String switchDB() throws Exception {
        HttpSession userSession = httpServletRequest.getSession(false);
        String db = userSession.getAttribute(AppConstants.SES_FIRST_DB).toString();
        if (db.startsWith("Ar")) {
            userSession.setAttribute(AppConstants.PROP_CURRENT_DS_NAME, Properties.getProperty(AppConstants.PROP_PROD_DS_NAME));
            userSession.setAttribute(AppConstants.SES_FIRST_DB, "Production Data");
        } else {
            userSession.setAttribute(AppConstants.PROP_CURRENT_DS_NAME, Properties.getProperty(AppConstants.PROP_ARCH_DS_NAME));
            userSession.setAttribute(AppConstants.SES_FIRST_DB, "Archive Data");
        }
        return "success";
    }

    public String doLogout() throws Exception {
        try {
            if (httpServletRequest.getSession(false) != null) {
                httpServletRequest.getSession(false).invalidate();
            }
            setResultType(SUCCESS);
        } catch (Exception ex) {
            httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_ERROR_INFO, ex.toString());
            setResultType(ERROR);
        }
        return getResultType();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(
     * javax.servlet.http.HttpServletRequest)
     */
    //@Override
    public void setServletRequest(HttpServletRequest reqObj) {
        this.setHttpServletRequest(reqObj);
    }

    /**
     * @param resultType the resultType to set
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * @return the resultType
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * @param httpServletRequest the httpServletRequest to set
     */
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * @return the httpServletRequest
     */
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    /**
     * @param loginId the loginId to set
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * @return the loginId
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
