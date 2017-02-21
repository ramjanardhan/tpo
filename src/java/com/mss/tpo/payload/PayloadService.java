
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.payload;

import com.mss.tpo.util.ServiceLocatorException;

public interface PayloadService {

    public String doPayloadTransfer(PayloadAction payloadAction) throws ServiceLocatorException;

   }
