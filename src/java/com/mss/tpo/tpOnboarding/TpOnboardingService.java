/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.tpOnboarding;

import com.mss.tpo.util.ServiceLocatorException;
import java.util.ArrayList;

/**
 *
 * @author Narendar
 */
public interface TpOnboardingService {

   public ArrayList<TpOnboardingBean> tpoSearchProfile(String loginId, int roleId, int partnerId, String flag, TpOnboardingAction tpAction);

    public String addTpoProfile(int partnerId, String PartnerName, String loginId, String Email, TpOnboardingAction tpAction) throws ServiceLocatorException;

    public TpOnboardingAction tpogetProfile(int Id, String commonprotocol, TpOnboardingAction tpOnboardingAction);

    public String tpoUpdateProfile(int partnerId, int communicationId, String commonprotocol, String Email, TpOnboardingAction tpOnboardingAction);

    public String getDeleteProfile(int communicationId, String commnProtocol, int PartnerId, String transferMode) throws ServiceLocatorException;

    public String getDeleteEnvelope(int id, String transaction, String direction) throws ServiceLocatorException;

    public ArrayList<TpOnboardingBean> tpoSearchEnvelope(String loginId, int partnerId, String flag, TpOnboardingAction tpAction);

    public String addTpoEnvelope(int partnerId, TpOnboardingAction tpAction) throws ServiceLocatorException;

    public TpOnboardingBean tpoEditEnvelope(int id, int partnerId, String transaction, String direction) throws ServiceLocatorException;

    public String as2FileDownload(int communicationId, TpOnboardingAction tpAction) throws ServiceLocatorException;
}
