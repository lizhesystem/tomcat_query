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

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码（post请求）
        request.setCharacterEncoding("utf-8");
        // 获取客户输入的验证码
        String checkCode = request.getParameter("code");
        // 获取session对象
        HttpSession session = request.getSession();

        // 获取生成验证码的servlet生成的验证码,默认得到Object类型,向下转成String
        String inpCode = (String) session.getAttribute("CHECKCODE_SERVER");
        // 为了一个验证码重复用,获取到立即删除
        session.removeAttribute("CHECKCODE_SERVER");

        // 判断获取的验证码和服务器生成验证码是否一致
        if (checkCode.equalsIgnoreCase(inpCode)) {
            // 验证码没问题的话，把传过来的账号、邮箱、密码封装成map对象key,value类型
            Map<String, String[]> inpMap = request.getParameterMap();
            // 创建管理员对象
            Admin ad = new Admin();
            try {
                // 封装内容,把管理员对象里的数据和获取到的Map对象一一快速封装成对象。
                BeanUtils.populate(ad, inpMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            // 创建UserService对象,
            UserService Us = new UserServiceImpl();

            // 调用CreateAdmin方法--方法内使用insert插入数据,其实这里可以传入ad对象,到server里再去取值。
            Integer returnState = Us.CreateAdmin(ad.getUsername(), ad.getPassword(), ad.getEmail());
            // 去掉插入成功
            if (returnState != null) {

                //插入成功，把ad对象传到index页面，展示欢迎xx登录样式
                session.setAttribute("adminUser",ad);
                // 重定向把请求过来的数据转到其他站点（服务器资源）上去,带着ad对象。 重定向不能使用request对象来共享数据，所以用session对象共享数据。
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            } else {

                // 插入失败,转发当前页面使用requser共享数据。
                request.setAttribute("registerErr", "注册失败...");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }


        } else {
            // 验证码如果错误，转发当前页面使用requser共享数据。
            request.setAttribute("CodeErr", "验证码错误");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
