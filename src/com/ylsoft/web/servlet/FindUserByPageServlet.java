package com.ylsoft.web.servlet;

import com.ylsoft.domain.BeanPage;
import com.ylsoft.service.UserService;
import com.ylsoft.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 有post请求设置编码
        request.setCharacterEncoding("utf-8");

        String currentPage = request.getParameter("currentPage"); // 获取当前是第几页
        String rows = request.getParameter("rows"); // 获取每页几条数据

        // 因为条件查询或查所有的时候不会传这个数据，给他设置默认值。
        if (currentPage == null || "".equals(currentPage)) {
            currentPage = "1";
        }

        // 因为条件查询或者查所有的时候不会传这个数据，给他设置默认值。
        if (rows == null || "".equals(rows)) {
            rows = "5";
        }

        // 把传入的参数封装成map对象
        Map<String, String[]>  inputPara= request.getParameterMap();

        UserService us = new UserServiceImpl();

        // 调用查找page方法，得到的结果就是一个分页对象。
        BeanPage bp = us.findUserByPage(currentPage, rows,inputPara);

        request.setAttribute("inputPara",inputPara);
        request.setAttribute("bp", bp);
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
