/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.user;

import com.mss.tpo.util.ServiceLocatorException;
import java.util.ArrayList;

/**
 *
 * @author Narendar
 */
public interface UserService {

    public String doTpoUserRegister(int partnerId, int roleId, String loginId, UserAction userAction) throws ServiceLocatorException;

    public String doResetMyPassword(int roleId, String loginId, String pwd) throws ServiceLocatorException;

    public String doTpoResetUserPwd(String loginId, String contactName, String pwd) throws ServiceLocatorException;

    public String doTpoResetPartnerPwd(String loginId, String partnreName, String pwd) throws ServiceLocatorException;

    public ArrayList<UserBean> tpoSearchUsers(String loginId, int roleId, String flag, UserAction userAction);
}
