
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.payload;

import com.mss.tpo.util.ServiceLocatorException;
import java.util.ArrayList;

public interface PayloadService {

    public ArrayList<PayloadBean> payloadSearch(String loginId, int partnerId);
     public String doPayloadUpload(int partnerId, String loginId, String filePath,PayloadAction payloadAction) throws ServiceLocatorException;

   }
