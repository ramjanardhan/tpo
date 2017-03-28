/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.admin;

import com.mss.tpo.util.ServiceLocatorException;
import java.util.ArrayList;

/**
 *
 * @author Narendar
 */
public interface AdminService {

    public ArrayList<AdminBean> tpoAdminSearchProfile(String loginId, int roleId, int partnerId, String flag, AdminAction adminAction);

    public String addAdminProfile(int partnerId, String PartnerName, String loginId, String Email, AdminAction adminAction) throws ServiceLocatorException;

    public AdminAction tpogetAdminProfile(int Id, String commonprotocol, AdminAction adminAction);

    public String tpoUpdateAdminProfile(int partnerId, int communicationId, String commonprotocol, String Email, AdminAction adminAction);

    public String getDeleteAdminProfile(int communicationId, String commnProtocol, int partnerId, String transferMode) throws ServiceLocatorException;

    public ArrayList<AdminBean> getFTPManageCommunicationsList(int roleId, String loginId, int partnerId, String managecommunication);

    public ArrayList<AdminBean> getSFTPManageCommunicationsList(int roleId, String loginId, int partnerId, String managecommunication);

    public ArrayList<AdminBean> getHTTPManageCommunicationsList(int roleId, String loginId, int partnerId, String managecommunication);

    public String doTpoManageCommunicationAdd(AdminAction adminAction) throws ServiceLocatorException;

    public String doTpoManageCommunicationRemove(String communicationId) throws ServiceLocatorException;
}
