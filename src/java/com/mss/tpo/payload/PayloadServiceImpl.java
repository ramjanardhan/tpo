/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mss.tpo.payload;


import com.mss.tpo.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Narendar
 */
public class PayloadServiceImpl implements PayloadService {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    String responseString = null;
    private HttpServletRequest httpServletRequest;

    public String doPayloadTransfer(PayloadAction payloadAction) throws ServiceLocatorException {
       responseString = "string";
        return responseString;
    }

}