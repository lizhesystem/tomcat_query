package com.ylsoft.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
            // 创建代理对象,增强getParameter方法
        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
                 // 如果request请求方法里包含getParameter
                if (method.getName().equals("getParameter")) {
                    // 使用method调用invoke方法用来获取getParameter请求的数据
                    String values = (String) method.invoke(req, args);
                    if (values != null) {
                        // 循环过滤文件的内容
                        for (String str : list) {
                            // 如果获取value里包含list里的字符串内容
                            if (values.contains(str)) {
                                // 如果包含这些敏感字符,把敏感字符替换为****
                                values = values.replaceAll(str, "***");
                            }
                        }
                    }
                    return values;
                }
//                if (method.getName().equals("getParameterMap")) {
//                    params = (Map<String, String[]>) method.invoke(req, args);
//
//                    if (params != null) {
//                        Set<String> strMap = params.keySet();
//                        for (String str : list) {
//                            for (String mapKey : strMap) {
//                                String[] values = params.get(mapKey);
//                                if (values[0].contains(str)) {
//                                    values[0].replaceAll(str,"***");
//                                    params.put(mapKey,values);
//                                }
//                            }
//                        }
//                    }
//
//                    return params;
//
//                }


                return method.invoke(req, args);
            }
        });

        // 放行，这个时候已经已经把这个方法请求后往后传输的值进行封装改造了(代理模式Proxy )
        chain.doFilter(proxy_req, resp);
    }
    private Map<String , String[]> params = new HashMap<String, String[]>();

    // 读取敏感词汇文件
    // 创建存储敏感词字符串数据
    private List<String> list = new ArrayList<String>();

    // 只读取一次最好放到init里。服务启动执行
    public void init(FilterConfig config) throws ServletException {


        try {
            ServletContext servletContext = config.getServletContext();
            String realPath = servletContext.getRealPath("/WEB-INF/classes/mgch.txt"); // 获取敏感词汇文件路径

//            BufferedReader bf = new BufferedReader(new FileReader(realPath));
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(realPath), "GBK"));
            String line = null;
            while ((line = bf.readLine()) != null) {
                System.out.println(bf.readLine());
                list.add(line);
            }
            bf.close();

            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
