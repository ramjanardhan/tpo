package com.mss.tpo.general;

import java.sql.Timestamp;

public class UserInfoBean {

    private int userId;
    private int roleId;
    private String loginId;
    private Timestamp lastLoginTS;
    private Timestamp lastLogoutTS;
    private String partnerName;
    private int partnerId;
    private String pwd;
    private String contactName;
    private String contactEmail;
    private String phoneNo;
    private String country;
    private String active;
    private String loginAccess;

    /**
     * @return the loginId
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * @param loginId the loginId to set
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * @return the lastLogoutTS
     */
    public Timestamp getLastLogoutTS() {
        return lastLogoutTS;
    }

    /**
     * @param lastLogoutTS the lastLogoutTS to set
     */
    public void setLastLogoutTS(Timestamp lastLogoutTS) {
        this.lastLogoutTS = lastLogoutTS;
    }

    /**
     * @param lastLoginTS the lastLoginTS to set
     */
    public void setLastLoginTS(Timestamp lastLoginTS) {
        this.lastLoginTS = lastLoginTS;
    }

    /**
     * @return the lastLoginTS
     */
    public Timestamp getLastLoginTS() {
        return lastLoginTS;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getLoginAccess() {
        return loginAccess;
    }

    public void setLoginAccess(String loginAccess) {
        this.loginAccess = loginAccess;
    }

}
