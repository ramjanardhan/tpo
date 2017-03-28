/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mss.tpo.user;

import com.mss.tpo.util.AppConstants;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.ServiceLocator;
import static com.opensymphony.xwork2.Action.LOGIN;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author Narendar
 */
public class UserAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{
      private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private UserBean userBean;
    private String resultType;
    private String loginId;
    private String password;
    private int id;
    private int roleId;
    private String country;
    private String status;
    private String contactName;
    private String regpartnerName;
    private String regcontactName;
    private String regcontactLName;
    private String regcontactEmail;
    private String regpassword;
    private String regcountry;
    private String regphoneNo;
    private String regaddress;
    private String regcity;
    private String regstate;
    private String regzipCode;
     private String partnerName;
    private String created_by;
    private Map partnerNameList;
    private Map myUsersList;
    private ArrayList<UserBean> tpoSearchUsersList;
    
    public String tpoRegister() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                setCreated_by(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());
                String resultMessage = ServiceLocator.getUserService().doTpoUserRegister(partnerId, roleId, loginId, this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }
    
    public String tpoUserAdd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoUsersList() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchUsersList);
                tpoSearchUsersList = ServiceLocator.getUserService().tpoSearchUsers(loginId, roleId, "initialFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchUsersList, tpoSearchUsersList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String searchUser() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchUsersList);
                tpoSearchUsersList = ServiceLocator.getUserService().tpoSearchUsers(loginId, roleId, "searchFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchUsersList, tpoSearchUsersList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }
    
    public String tpoResetMyPwd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                // setAdminUsersList(DataSourceDataProvider.getInstance().getAdminUsersList());
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doTpoResetMyPwd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                String resultMessage = ServiceLocator.getUserService().doResetMyPassword(roleId, loginId, getRegpassword());
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoResetUserPwd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setMyUsersList(DataSourceDataProvider.getInstance().getMyUsersList(loginId, roleId));
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doTpoResetUserPwd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {

            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setMyUsersList(DataSourceDataProvider.getInstance().getMyUsersList(loginId, roleId));
                String resultMessage = ServiceLocator.getUserService().doTpoResetUserPwd(loginId, getContactName(), getRegpassword());
                //String resultMessage = "";
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoResetPartnerPwd() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setPartnerNameList(DataSourceDataProvider.getInstance().getMyPartnersList(loginId, roleId));
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doTpoResetPartnerPwd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {

            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setPartnerNameList(DataSourceDataProvider.getInstance().getMyPartnersList(loginId, roleId));
                String resultMessage = ServiceLocator.getUserService().doTpoResetPartnerPwd(loginId, getPartnerName(), getRegpassword());
                //String resultMessage = "";
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }
    
     @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getRegpassword() {
        return regpassword;
    }

    public void setRegpassword(String regpassword) {
        this.regpassword = regpassword;
    }

    public Map getPartnerNameList() {
        return partnerNameList;
    }

    public void setPartnerNameList(Map partnerNameList) {
        this.partnerNameList = partnerNameList;
    }

    public Map getMyUsersList() {
        return myUsersList;
    }

    public void setMyUsersList(Map myUsersList) {
        this.myUsersList = myUsersList;
    }

    public String getRegcontactEmail() {
        return regcontactEmail;
    }

    public void setRegcontactEmail(String regcontactEmail) {
        this.regcontactEmail = regcontactEmail;
    }

    public String getRegpartnerName() {
        return regpartnerName;
    }

    public void setRegpartnerName(String regpartnerName) {
        this.regpartnerName = regpartnerName;
    }

    public String getRegcontactName() {
        return regcontactName;
    }

    public void setRegcontactName(String regcontactName) {
        this.regcontactName = regcontactName;
    }

    public String getRegcontactLName() {
        return regcontactLName;
    }

    public void setRegcontactLName(String regcontactLName) {
        this.regcontactLName = regcontactLName;
    }

    public String getRegcountry() {
        return regcountry;
    }

    public void setRegcountry(String regcountry) {
        this.regcountry = regcountry;
    }

    public String getRegphoneNo() {
        return regphoneNo;
    }

    public void setRegphoneNo(String regphoneNo) {
        this.regphoneNo = regphoneNo;
    }

    public String getRegaddress() {
        return regaddress;
    }

    public void setRegaddress(String regaddress) {
        this.regaddress = regaddress;
    }

    public String getRegcity() {
        return regcity;
    }

    public void setRegcity(String regcity) {
        this.regcity = regcity;
    }

    public String getRegstate() {
        return regstate;
    }

    public void setRegstate(String regstate) {
        this.regstate = regstate;
    }

    public String getRegzipCode() {
        return regzipCode;
    }

    public void setRegzipCode(String regzipCode) {
        this.regzipCode = regzipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
