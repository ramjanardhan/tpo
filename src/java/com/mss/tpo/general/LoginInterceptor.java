package com.mss.tpo.general;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.StrutsStatics;

/**
 *
 * @author Narendar
 */
public class LoginInterceptor extends AbstractInterceptor implements StrutsStatics {

    /**
     * Creates a new instance of LoginInterceptor
     */
    public LoginInterceptor() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext context = actionInvocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "sessionExpire";
        }
        return actionInvocation.invoke();
    }
}
