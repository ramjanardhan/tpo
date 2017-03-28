/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.partner;

import com.mss.tpo.util.ServiceLocatorException;
import java.util.ArrayList;

/**
 *
 * @author Narendar
 */
public interface PartnerService {

    public PartnerBean getPartnerInfo(int PartnerId, String loginId) throws ServiceLocatorException;

    public String doAddPartnerUser(int partnerId, int roleId, String loginId, PartnerAction tpAction) throws ServiceLocatorException;

    public String updatePartnerInfo(PartnerAction tpAction, String loginId, int partnerId) throws ServiceLocatorException;

    public ArrayList<PartnerBean> tpoSearchPartners(String loginId, int roleId, String flag, PartnerAction tpAction);

}
