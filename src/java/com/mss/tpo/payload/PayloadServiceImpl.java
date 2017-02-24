/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.payload;

import com.mss.tpo.util.ConnectionProvider;
import com.mss.tpo.util.DateUtility;
import com.mss.tpo.util.ServiceLocatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private ArrayList<PayloadBean> payloadSearchList;

    public ArrayList<PayloadBean> payloadSearch(String loginId, int partnerId) {
        StringBuffer queryString = new StringBuffer();
        System.out.println("partnerID====" + partnerId);
        //queryString.append("SELECT ID, COMMUNICATION_ID, TRANSACTION, DIRECTION, LAST_TEST_STATUS, LAST_TEST_DATE, CURRENT_TEST_STATUS, CURRENT_TEST_DATE FROM MSCVP.TPO_PAYLOAD WHERE PARTNER_ID =" + partnerId );
        queryString.append(" SELECT * FROM MSCVP.TPO_PAYLOAD WHERE PARTNER_ID =" + partnerId);

        try {
            Connection con = ConnectionProvider.getInstance().getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(queryString.toString());
            payloadSearchList = new ArrayList<PayloadBean>();
            while (rs.next()) {
                PayloadBean payloadBean = new PayloadBean();
                payloadBean.setId(rs.getInt("ID"));
                payloadBean.setCorrelationID(rs.getInt("COMMUNICATION_ID"));
                payloadBean.setTransaction(rs.getInt("TRANSACTION"));
                payloadBean.setDirection(rs.getString("DIRECTION"));
                payloadBean.setLastTestStatus(rs.getString("LAST_TEST_STATUS"));
                payloadBean.setLastTestDate(rs.getTimestamp("LAST_TEST_DATE"));
                payloadBean.setCurrentTestStatus(rs.getString("CURRENT_TEST_STATUS"));
                payloadBean.setCurrentTestDate(rs.getTimestamp("CURRENT_TEST_DATE"));
                System.out.println("rs.getInt(\"TRANSACTION\")==" + rs.getInt("TRANSACTION"));
                System.out.println("rs.getInt(COMMUNICATION_ID)===" + rs.getInt("COMMUNICATION_ID"));
                payloadSearchList.add(payloadBean);
                System.out.println("payloadSearchList======" + payloadSearchList.toString());
            }
        } catch (Exception ex) {
            Logger.getLogger(PayloadServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payloadSearchList;
    }

    public String doPayloadUpload(int partnerId, String loginId, String filePath, PayloadAction payloadAction) throws ServiceLocatorException {
        int isPayloadInserted = 0;
        Timestamp curdate = DateUtility.getInstance().getCurrentDB2Timestamp();
        try {
            connection = ConnectionProvider.getInstance().getConnection();
            statement = connection.createStatement();

            String payloadQuery = "INSERT INTO MSCVP.TPO_PAYLOAD (PARTNER_ID, TRANSACTION, "
                    + "DOC_TYPE, DIRECTION, PATH, STATUS, COMMUNICATION_ID,CREATED_BY,CREATED_TS) VALUES(?,?,?,?,?,?,?,?,?)";
                 preparedStatement = connection.prepareStatement(payloadQuery);
            preparedStatement.setInt(1, partnerId);
            preparedStatement.setString(2, payloadAction.getTransaction());
            preparedStatement.setString(3, payloadAction.getDocType());
            preparedStatement.setString(4, payloadAction.getDirection());
            preparedStatement.setString(5, filePath);
            preparedStatement.setString(6, "UPLOADED");
            preparedStatement.setInt(7, 100);
            preparedStatement.setString(8, loginId);
            preparedStatement.setTimestamp(9, curdate);
           
            isPayloadInserted = isPayloadInserted + preparedStatement.executeUpdate();
            if (isPayloadInserted > 0) {
                responseString = "<font color='green'>Payload uploaded successfully.</font>";
            } else {
                responseString = "<font color='green'>Payload uploaded failed.</font>";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            responseString = "<font color='red'>Please try again!</font>";
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                    callableStatement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException se) {
                throw new ServiceLocatorException(se);
            }
        }
        return responseString;
    }

}
