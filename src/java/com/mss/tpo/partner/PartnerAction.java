/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.partner;

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
public class PartnerAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private PartnerBean partnerBean;
    private String resultType;
    private String loginId;
    private String password;
    private int id;
    private int roleId;
    private String partnerName;
    private String contactName;
    private String contactLastName;
    private String contactEmail;
    private String phoneNo;
    private String address1;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String status;
    private Map partnerRolesList;
    private Map adminUsersList;
    private String created_by;
    private int partnerRole;
    private ArrayList<PartnerBean> tpoSearchPartnersList;

    public String tpoPartnerUserAdd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                setRoleId(roleId);
                setPartnerRolesList(DataSourceDataProvider.getInstance().getPartnerRolesList(roleId));
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doAddPartnerUser() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String resultMessage = ServiceLocator.getPartnerService().doAddPartnerUser(partnerId, roleId, loginId, this);
                setRoleId(roleId);
                setPartnerRolesList(DataSourceDataProvider.getInstance().getPartnerRolesList(roleId));
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoPartnerAdd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID).toString());
                setRoleId(roleId);
                if (roleId == 1) {
                    setAdminUsersList(DataSourceDataProvider.getInstance().getAdminUsersList());
                }
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoPartnerInfo() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setPartnerBean(ServiceLocator.getPartnerService().getPartnerInfo(partnerId, loginId.trim().toLowerCase()));
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String updatePartnerInfo() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setCreated_by(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());
                String resultMessage = ServiceLocator.getPartnerService().updatePartnerInfo(this, loginId, partnerId);
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                setPartnerBean(ServiceLocator.getPartnerService().getPartnerInfo(partnerId, loginId.trim().toLowerCase()));
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoPartnersList() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                if (roleId == 1) {
                    setAdminUsersList(DataSourceDataProvider.getInstance().getAdminUsersList());
                }
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchPartnersList);
                tpoSearchPartnersList = ServiceLocator.getPartnerService().tpoSearchPartners(loginId, roleId, "initialFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchPartnersList, tpoSearchPartnersList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String searchPartner() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                if (roleId == 1) {
                    setAdminUsersList(DataSourceDataProvider.getInstance().getAdminUsersList());
                } else if (roleId == 2) {
                }
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchPartnersList);
                tpoSearchPartnersList = ServiceLocator.getPartnerService().tpoSearchPartners(loginId, roleId, "searchFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchPartnersList, tpoSearchPartnersList);
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

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Map getPartnerRolesList() {
        return partnerRolesList;
    }

    public void setPartnerRolesList(Map partnerRolesList) {
        this.partnerRolesList = partnerRolesList;
    }

    public Map getAdminUsersList() {
        return adminUsersList;
    }

    public void setAdminUsersList(Map adminUsersList) {
        this.adminUsersList = adminUsersList;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public int getPartnerRole() {
        return partnerRole;
    }

    public void setPartnerRole(int partnerRole) {
        this.partnerRole = partnerRole;
    }

    public PartnerBean getPartnerBean() {
        return partnerBean;
    }

    public void setPartnerBean(PartnerBean partnerBean) {
        this.partnerBean = partnerBean;
    }

}
