package com.ylsoft.web.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class demoFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);

//        System.out.println("dofilter.......");
//
//        chain.doFilter(req,resp);


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
