/*
 * AjaxHandlerAction.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.mss.tpo.ajax;

import com.mss.tpo.tpOnboarding.TpOnboardingBean;
import com.mss.tpo.util.AppConstants;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.MailManager;
import com.mss.tpo.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author Narendar
 */
public class AjaxHandlerAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    /**
     * Creating a reference variable for HttpServletRequest.
     */
    private HttpServletRequest httpServletRequest;
    /**
     * Creating a reference variable for HttpServletResponse.
     */
    private HttpServletResponse httpServletResponse;
    private int id;
    private String responseString;
    private String name;
    private String lastName;
    private String endDate;
    private String direction;
    private String transferMode;
    private String protocol;
    private String partnerName;
    private String email;
    private String addpartnerName;
    private String addphoneNo;
    private String addaddress1;
    private String addcity;
    private String addstate;
    private String addcountry;
    private String addzipCode;
    private String transaction;
    private String envelopeDetails;
    private String userid;
    private String password;
    private String url;
    private String description;
    private String assignTo;
    private int roleId;
    private String flag;
    private int communicationId;
    private String ftp_method;
    private String ftp_conn_method;
    private String ftp_recv_protocol;
    private String ftp_resp_time;
    private String ftp_host;
    private String ftp_port;
    private String ftp_userId;
    private String ftp_pwd;
    private String ftp_directory;
    private String sftp_conn_method;
    private String sftp_auth_method;
    private String sftp_host_ip;
    private String sftp_remote_port;
    private String sftp_remote_userId;
    private String sftp_remote_pwd;
    private String sftp_method;
    private String sftp_directory;
    private List<TpOnboardingBean> tpoCommunicationsList;
    private List<String> tpoProtocolsHeadersList;

    public String forgotPassword() throws Exception {
        try {
            String response = ServiceLocator.getAjaxHandlerService().forgotPassword(getUserid(), getEmail());
            httpServletResponse.setContentType("text/html");
            if (response.equals("success")) {
                responseString = "Password has been sent to your mail Id";
                httpServletResponse.getWriter().write(responseString);
            } else {
                responseString = response;
                httpServletResponse.getWriter().write(responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String isExistedPartnerName() {
        //  if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
        try {
            responseString = ServiceLocator.getAjaxHandlerService().isExistedPartnerName(getName());
            httpServletResponse.setContentType("text/xml");
            httpServletResponse.getWriter().write(responseString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // }
        return null;
    }

    public String isExistedUserEmail() {
        //  if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
        try {
            responseString = ServiceLocator.getAjaxHandlerService().isExistedUserEmail(getEmail());
            httpServletResponse.setContentType("text/xml");
            httpServletResponse.getWriter().write(responseString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // }
        return null;
    }

    public String doAddTpoPartnerSelf() {
        try {
            setRoleId(0);
            responseString = ServiceLocator.getAjaxHandlerService().doAddTpoPartner(getRoleId(), "self", this);
            httpServletResponse.setContentType("text/xml");
            httpServletResponse.getWriter().write(responseString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String doAddTpoPartner() {
        String loginId = (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());
        if (loginId != null) {
            try {
                responseString = ServiceLocator.getAjaxHandlerService().doAddTpoPartner(getRoleId(), loginId, this);
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String updateEnvelope() {
        String loginId = (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());
        int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
        if (loginId != null) {
            try {
                responseString = ServiceLocator.getAjaxHandlerService().doUpdateEnvelope(getId(),getEnvelopeDetails(), loginId, partnerId, getTransaction(), getDirection());
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String getProtocolDetails() {
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                responseString = ServiceLocator.getAjaxHandlerService().getProtocolDetails(getTransferMode(), getProtocol(), partnerId);
                httpServletResponse.setContentType("text");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String sendPwdEmail() {
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                String email = DataSourceDataProvider.getInstance().getEmaiIdByloginId(loginId);
                String partnerName = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_NAME).toString();
                responseString = MailManager.getModePwdEmail(loginId, email, partnerName, this);
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String isExistedAS2PartnerProfileName() {
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                responseString = ServiceLocator.getAjaxHandlerService().isExistedAS2PartnerProfileName(getName(), getPartnerName());
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String acceptPartner() {
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                responseString = ServiceLocator.getAjaxHandlerService().doAcceptPartner(loginId, getAssignTo(), getId());
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String rejectPartner() {
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                responseString = ServiceLocator.getAjaxHandlerService().doRejectPartner(loginId, getId());
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String testconnectionstatus() {
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                responseString = ServiceLocator.getAjaxHandlerService().getTestConnecitonStatus(getCommunicationId(), getProtocol(), getPartnerName());
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public String activateUser() {
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                responseString = ServiceLocator.getAjaxHandlerService().doActivateUser(loginId, getUserid());
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String inActivateUser() {
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                responseString = ServiceLocator.getAjaxHandlerService().doInActivateUser(loginId, getUserid());
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String testConforHTTPandHTTPS() {

        String connectionstatus = "";
        try {
            connectionstatus = ServiceLocator.getAjaxHandlerService().getTestConforHTTPandHTTPS();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectionstatus;
    }

    public String testConforAS2() {

        String connectionstatus = "";
        try {
            connectionstatus = ServiceLocator.getAjaxHandlerService().getTestConforAS2();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectionstatus;
    }

    public String testConforFTPandFTPS() {

        String connectionstatus = "";
        try {
            connectionstatus = ServiceLocator.getAjaxHandlerService().getTestConforFTPandFTPS();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectionstatus;
    }

    public String testConforSFTP() {

        String connectionstatus = "";
        try {
            connectionstatus = ServiceLocator.getAjaxHandlerService().getTestConforSFTP();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectionstatus;
    }

    public String testConforSMTP() {

        String connectionstatus = "";
        try {
            connectionstatus = ServiceLocator.getAjaxHandlerService().getTestConforSMTP();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectionstatus;
    }

    public int getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(int communicationId) {
        this.communicationId = communicationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     *
     * This method is used to set the Servlet Request
     *
     * @param httpServletRequest
     */
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     *
     * This method is used to set the Servlet Response
     *
     * @param httpServletResponse
     */
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddaddress1() {
        return addaddress1;
    }

    public void setAddaddress1(String addaddress1) {
        this.addaddress1 = addaddress1;
    }

    public String getAddcity() {
        return addcity;
    }

    public void setAddcity(String addcity) {
        this.addcity = addcity;
    }

    public String getAddcountry() {
        return addcountry;
    }

    public void setAddcountry(String addcountry) {
        this.addcountry = addcountry;
    }

    public String getAddpartnerName() {
        return addpartnerName;
    }

    public void setAddpartnerName(String addpartnerName) {
        this.addpartnerName = addpartnerName;
    }

    public String getAddphoneNo() {
        return addphoneNo;
    }

    public void setAddphoneNo(String addphoneNo) {
        this.addphoneNo = addphoneNo;
    }

    public String getAddstate() {
        return addstate;
    }

    public void setAddstate(String addstate) {
        this.addstate = addstate;
    }

    public String getAddzipCode() {
        return addzipCode;
    }

    public void setAddzipCode(String addzipCode) {
        this.addzipCode = addzipCode;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getEnvelopeDetails() {
        return envelopeDetails;
    }

    public void setEnvelopeDetails(String envelopeDetails) {
        this.envelopeDetails = envelopeDetails;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public AjaxHandlerAction() {
    }

    public String getFtp_method() {
        return ftp_method;
    }

    public void setFtp_method(String ftp_method) {
        this.ftp_method = ftp_method;
    }

    public String getFtp_conn_method() {
        return ftp_conn_method;
    }

    public void setFtp_conn_method(String ftp_conn_method) {
        this.ftp_conn_method = ftp_conn_method;
    }

    public String getFtp_recv_protocol() {
        return ftp_recv_protocol;
    }

    public void setFtp_recv_protocol(String ftp_recv_protocol) {
        this.ftp_recv_protocol = ftp_recv_protocol;
    }

    public String getFtp_resp_time() {
        return ftp_resp_time;
    }

    public void setFtp_resp_time(String ftp_resp_time) {
        this.ftp_resp_time = ftp_resp_time;
    }

    public String getFtp_host() {
        return ftp_host;
    }

    public void setFtp_host(String ftp_host) {
        this.ftp_host = ftp_host;
    }

    public String getFtp_port() {
        return ftp_port;
    }

    public void setFtp_port(String ftp_port) {
        this.ftp_port = ftp_port;
    }

    public String getFtp_userId() {
        return ftp_userId;
    }

    public void setFtp_userId(String ftp_userId) {
        this.ftp_userId = ftp_userId;
    }

    public String getFtp_pwd() {
        return ftp_pwd;
    }

    public void setFtp_pwd(String ftp_pwd) {
        this.ftp_pwd = ftp_pwd;
    }

    public String getFtp_directory() {
        return ftp_directory;
    }

    public void setFtp_directory(String ftp_directory) {
        this.ftp_directory = ftp_directory;
    }

    public String getSftp_conn_method() {
        return sftp_conn_method;
    }

    public void setSftp_conn_method(String sftp_conn_method) {
        this.sftp_conn_method = sftp_conn_method;
    }

    public String getSftp_auth_method() {
        return sftp_auth_method;
    }

    public void setSftp_auth_method(String sftp_auth_method) {
        this.sftp_auth_method = sftp_auth_method;
    }

    public String getSftp_host_ip() {
        return sftp_host_ip;
    }

    public void setSftp_host_ip(String sftp_host_ip) {
        this.sftp_host_ip = sftp_host_ip;
    }

    public String getSftp_remote_port() {
        return sftp_remote_port;
    }

    public void setSftp_remote_port(String sftp_remote_port) {
        this.sftp_remote_port = sftp_remote_port;
    }

    public String getSftp_remote_userId() {
        return sftp_remote_userId;
    }

    public void setSftp_remote_userId(String sftp_remote_userId) {
        this.sftp_remote_userId = sftp_remote_userId;
    }

    public String getSftp_remote_pwd() {
        return sftp_remote_pwd;
    }

    public void setSftp_remote_pwd(String sftp_remote_pwd) {
        this.sftp_remote_pwd = sftp_remote_pwd;
    }

    public String getSftp_method() {
        return sftp_method;
    }

    public void setSftp_method(String sftp_method) {
        this.sftp_method = sftp_method;
    }

    public String getSftp_directory() {
        return sftp_directory;
    }

    public void setSftp_directory(String sftp_directory) {
        this.sftp_directory = sftp_directory;
    }

    public List<TpOnboardingBean> getTpoCommunicationsList() {
        return tpoCommunicationsList;
    }

    public void setTpoCommunicationsList(List<TpOnboardingBean> tpoCommunicationsList) {
        this.tpoCommunicationsList = tpoCommunicationsList;
    }

    public List<String> getTpoProtocolsHeadersList() {
        return tpoProtocolsHeadersList;
    }

    public void setTpoProtocolsHeadersList(List<String> tpoProtocolsHeadersList) {
        this.tpoProtocolsHeadersList = tpoProtocolsHeadersList;
    }

}
