/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.tpOnboarding;

import com.mss.tpo.util.ServiceLocatorException;
import java.util.ArrayList;

public interface TpOnboardingService {

    public TpOnboardingBean getPartnerInfo(int PartnerId, String loginId) throws ServiceLocatorException;

    public String doTpoUserRegister(int partnerId, int roleId, String loginId, TpOnboardingAction tpAction) throws ServiceLocatorException;

    public String doAddPartnerUser(int partnerId, int roleId, String loginId, TpOnboardingAction tpAction) throws ServiceLocatorException;

    public String updatePartnerInfo(TpOnboardingAction tpAction, String loginId, int partnerId) throws ServiceLocatorException;

    public ArrayList<TpOnboardingBean> tpoSearchPartners(String loginId, int roleId, String flag, TpOnboardingAction tpAction);

    public ArrayList<TpOnboardingBean> tpoSearchProfile(String loginId, int partnerId, String flag, TpOnboardingAction tpAction);

    public String addTpoProfile(int partnerId, String PartnerName, String loginId, String Email, TpOnboardingAction tpAction) throws ServiceLocatorException;

    public TpOnboardingAction tpogetProfile(int Id, String commonprotocol, TpOnboardingAction tpOnboardingAction);

    public String tpoUpdateProfile(int partnerId, int Id, String commonprotocol, String Email, TpOnboardingAction tpOnboardingAction);

    public String getDeleteProfile(int communicationId, String commnProtocol, int PartnerId, String transferMode) throws ServiceLocatorException;

    public String getDeleteEnvelope(int id, String transaction, String direction) throws ServiceLocatorException;

    public ArrayList<TpOnboardingBean> tpoSearchEnvelope(String loginId, int partnerId, String flag, TpOnboardingAction tpAction);

    public String addTpoEnvelope(int partnerId, TpOnboardingAction tpAction) throws ServiceLocatorException;

    public TpOnboardingBean tpoEditEnvelope(int partnerId, String transaction, String direction) throws ServiceLocatorException;

    public String doResetMyPassword(int roleId, String loginId, String pwd) throws ServiceLocatorException;

    public String doTpoResetUserPwd(String loginId, String contactName, String pwd) throws ServiceLocatorException;

    public String doTpoResetPartnerPwd(String loginId, String partnreName, String pwd) throws ServiceLocatorException;
}
