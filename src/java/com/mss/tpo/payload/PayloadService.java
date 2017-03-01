
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.payload;

import com.mss.tpo.tpOnboarding.TpOnboardingBean;
import com.mss.tpo.util.ServiceLocatorException;
import java.util.ArrayList;

public interface PayloadService {

    public ArrayList<PayloadBean> payloadSearch(String loginId, int roleId, int partnerId, String flag, PayloadAction payloadAction);

    public String doPayloadUpload(int partnerId, String partnerName, String loginId, String filePath, PayloadAction payloadAction) throws ServiceLocatorException;

    public ArrayList<TpOnboardingBean> getFTPCommunicationsList(String loginId, int roleId, int partnerId, String protocol) throws ServiceLocatorException;

    public ArrayList<TpOnboardingBean> getSFTPCommunicationsList(String loginId, int roleId, int partnerId, String protocol) throws ServiceLocatorException;

    public ArrayList<TpOnboardingBean> getHTTPCommunicationsList(String loginId, int roleId, int partnerId, String protocol) throws ServiceLocatorException;

    public ArrayList<TpOnboardingBean> getSMTPCommunicationsList(String loginId, int roleId, int partnerId, String protocol) throws ServiceLocatorException;

    public ArrayList<TpOnboardingBean> getAS2CommunicationsList(String loginId, int roleId, int partnerId, String protocol) throws ServiceLocatorException;
}
