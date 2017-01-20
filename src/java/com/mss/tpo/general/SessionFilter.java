package com.mss.tpo.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

    private ArrayList<String> urlList;
    private static Logger logger = Logger.getLogger(SessionFilter.class.getName());

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getServletPath();
        url = request.getRequestURI();
        HttpSession session = request.getSession(false);
        if (session == null && !url.trim().equals("/tpo/")) {
            response.sendRedirect("/tpo");
        }
        if (session != null) {
            String user = (String) session.getAttribute("userName");
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig config) throws ServletException {
        String urls = config.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
        urlList = new ArrayList<String>();
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
        }
        urlList.size();
    }
}