package com.ylsoft.service;

import com.ylsoft.domain.Admin;
import com.ylsoft.domain.BeanPage;
import com.ylsoft.domain.User;

import java.util.List;
import java.util.Map;

// 用户管理的业务接口
public interface UserService {

    // 创建管理员--insert管理员
    public Integer CreateAdmin(String username, String password, String mail);

    // 管理员登录--查找管理员是否存在
    public Admin SelectAdmin(String username, String password);

    public List<User> findAll();

    void addUser(User user);

    void DelUser(String userid);

    User findUserById(String userId);

    void UpdateUser(User user);

    void delSelectUser(String[] uids);

    BeanPage findUserByPage(String currentPage, String rows, Map<String, String[]> inputPara);
}
