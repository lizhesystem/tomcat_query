package com.ylsoft.dao;

import com.ylsoft.domain.Admin;
import com.ylsoft.domain.User;

import java.util.List;
import java.util.Map;

// 用户操作的DAO接口
public interface UserDao {
    public Integer CreateAdmin(String username, String password, String mail);

    public Admin SelectAdmin(String username, String password);

    public List<User> findAll();

    // 添加用户
    void addUser(User user);

    // 删除用户
    void delUser(int id);

    // 修改用户之前的查找用户
    User findUserById(int i);

    void UpdateUser(User user);

    int findtotalCount(Map<String, String[]> inputPara);

    List<User> findByPage(int start, int rows, Map<String, String[]> inputPara);
}
