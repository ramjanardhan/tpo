/*
 * This Class Creates instances of all classes.
 */

package com.mss.tpo.util;

import com.mss.tpo.admin.AdminService;
import com.mss.tpo.admin.AdminServiceImpl;
import com.mss.tpo.ajax.AjaxHandlerService;
import com.mss.tpo.ajax.AjaxHandlerServiceImpl;
import com.mss.tpo.general.GeneralService;
import com.mss.tpo.general.GeneralServiceImpl;
import com.mss.tpo.partner.PartnerService;
import com.mss.tpo.partner.PartnerServiceImpl;
import com.mss.tpo.payload.PayloadService;
import com.mss.tpo.payload.PayloadServiceImpl;
import com.mss.tpo.ticket.TicketService;
import com.mss.tpo.ticket.TicketServiceImpl;
import com.mss.tpo.tpOnboarding.TpOnboardingService;
import com.mss.tpo.tpOnboarding.TpOnboardingServiceImpl;
import com.mss.tpo.user.UserService;
import com.mss.tpo.user.UserServiceImpl;

/**
 *
 * @author Narendar
 */
public class ServiceLocator {

    /**
     * Creates a new instance of ServiceLocator
     */
    private ServiceLocator() {
    }

    public static GeneralService getGeneralService() throws ServiceLocatorException {
        GeneralService generalService = new GeneralServiceImpl();
        return generalService;
    }

    public static AjaxHandlerService getAjaxHandlerService() throws ServiceLocatorException {
        AjaxHandlerService ajaxHandlerService = new AjaxHandlerServiceImpl();
        return ajaxHandlerService;
    }

    public static TpOnboardingService getTpOnboardingService() throws ServiceLocatorException {
        TpOnboardingService tpOnboardingService = new TpOnboardingServiceImpl();
        return tpOnboardingService;
    }
    
    public static PayloadService getPayloadService() throws ServiceLocatorException {
        PayloadService payloadService = new PayloadServiceImpl();
        return payloadService;
    }
    
    public static PartnerService getPartnerService() throws ServiceLocatorException {
        PartnerService partnerService = new PartnerServiceImpl();
        return partnerService;
    }
    
    public static AdminService getAdminService() throws ServiceLocatorException {
        AdminService adminService = new AdminServiceImpl();
        return adminService;
    }
    
    public static UserService getUserService() throws ServiceLocatorException {
        UserService userService = new UserServiceImpl();
        return userService;
    }
    
    public static TicketService getTicketService() throws ServiceLocatorException {
        TicketService ticketService = new TicketServiceImpl();
        return ticketService;
    }
}
