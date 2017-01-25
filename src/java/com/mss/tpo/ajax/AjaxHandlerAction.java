/*
 * AjaxHandlerAction.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.mss.tpo.ajax;

import com.mss.tpo.util.AppConstants;
import com.mss.tpo.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

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
                responseString = ServiceLocator.getAjaxHandlerService().doUpdateEnvelope(getEnvelopeDetails(), loginId, partnerId, getTransaction(), getDirection());
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
                responseString = ServiceLocator.getAjaxHandlerService().getProtocolDetails(getTransferMode(), getProtocol());
                httpServletResponse.setContentType("text");
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
                System.out.println("action class");
                responseString = ServiceLocator.getAjaxHandlerService().getTestConnecitonStatus(getCommunicationId(), getProtocol(), getPartnerName());
                httpServletResponse.setContentType("text/xml");
                httpServletResponse.getWriter().write(responseString);
            } catch (Exception e) {
                e.printStackTrace();
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

}
