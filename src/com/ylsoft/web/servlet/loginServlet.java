package com.ylsoft.web.servlet;

import com.ylsoft.domain.Admin;
import com.ylsoft.service.UserService;
import com.ylsoft.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // post请求设置编码
        request.setCharacterEncoding("utf-8");

        // 下面内容验证验证码
        String checkCode = request.getParameter("code");
        HttpSession session = request.getSession();

        String inpCode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if (!checkCode.equalsIgnoreCase(inpCode)) {
            request.setAttribute("codeErr", "验证码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;

        }

        // 如果验证码没问题,登录获取的参数封装对象
        Map<String, String[]> inpMap = request.getParameterMap();
        Admin ad = new Admin();
        try {
            BeanUtils.populate(ad, inpMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService us = new UserServiceImpl();

        // 调用UserServiceImpl对象里的方法查找用户名密码是否正确。
        Admin admin = us.SelectAdmin(ad.getUsername(), ad.getPassword());

        if (admin != null) {
            // 查找正确需要跳转页面，使用session共享数据，跳转到index.jsp 页面
            session.setAttribute("adminUser", admin);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            request.setAttribute("loginErr", "用户名或密码错误!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        this.doPost(request, response);
    }

}

