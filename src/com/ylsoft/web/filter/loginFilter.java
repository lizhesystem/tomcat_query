package com.ylsoft.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(value = "/*",dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.ASYNC})
public class loginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;  // 因为这个requset和servlet里的request是一个对象，强转
        String uri = request.getRequestURI(); // 获取请求路径

        if (uri.contains("/login.jsp") || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/fonts/") || uri.contains("/loginServlet") || uri.contains("/register.jsp") || uri.contains("/registerServlet") || uri.contains("/checkCodeServlet")) {
            // 放行
            chain.doFilter(req, resp);
        } else {
            Object adminUser = request.getSession().getAttribute("adminUser");
            // 如果session里有这个对象,放行
            if(adminUser != null){
                chain.doFilter(req, resp);
                System.out.println("执行我....");
            }else{
                // 如果没有登录跳转到登录界面,给提示
                request.setAttribute("loginErr","您尚未登录,请登录");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
