/*
 * AjaxHandlerService.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.mss.tpo.ajax;

import com.mss.tpo.util.ServiceLocatorException;

public interface AjaxHandlerService {

    /**
     * Creates a new instance of AjaxHandlerService
     */
    public String forgotPassword(String userid, String regEmail) throws ServiceLocatorException;

    public String isExistedPartnerName(String partnerName) throws ServiceLocatorException;

    public String isExistedUserEmail(String email) throws ServiceLocatorException;

    public String doAddTpoPartner(int roleId, String loginId, AjaxHandlerAction ajaxHandlerAction) throws ServiceLocatorException;

    public String doUpdateEnvelope(String envelopeDetials, String loginId, int partnerId, String transaction, String direction) throws ServiceLocatorException;

    public String getProtocolDetails(String transferMode, String protocol) throws ServiceLocatorException;

    public String isExistedAS2PartnerProfileName(String name, String partnerName) throws ServiceLocatorException;

    public String doAcceptPartner(String loginId, String assignTo, int partnerId) throws ServiceLocatorException;

    public String doRejectPartner(String loginId, int partnerId) throws ServiceLocatorException;

    public String getTestConnecitonStatus(int communicationId, String protocol, String partnerName) throws SecurityException;

    public String getTestConforHTTPandHTTPS(/*String s1, String s2, String s3, String s4, String s5*/) throws ServiceLocatorException;

    public String getTestConforFTPandFTPS(/*String s1, String s2, String s3, String s4, String s5*/) throws ServiceLocatorException;

    public String getTestConforAS2(/*String s1, String s2, String s3, String s4, String s5*/) throws ServiceLocatorException;

    public String getTestConforSMTP(/*String s1, String s2, String s3, String s4, String s5*/) throws ServiceLocatorException;

    public String getTestConforSFTP(/*String s1, String s2, String s3, String s4, String s5*/) throws ServiceLocatorException;

}
