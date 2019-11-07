package com.ylsoft.dao.impl;

import com.ylsoft.dao.UserDao;
import com.ylsoft.domain.Admin;
import com.ylsoft.domain.User;
import com.ylsoft.utils.JDBCutils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCutils.getDataSource());

    @Override
    public Integer CreateAdmin(String username, String password, String mail) {

        try {
            String sql = "INSERT into admin VALUES (null,?,?,?)";
            return template.update(sql, username, password, mail);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Admin SelectAdmin(String username, String password) {

        try {
            String sql = "select * from admin where username = ? and password = ?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<Admin>(Admin.class),
                    username, password
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        try {
            String sql = "select * from account";
            return template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void addUser(User user) {
        String sql = "insert into account values(null,?,?,?,?,?,?)";
        template.update(sql, user.getName(), user.getGendet(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void delUser(int id) {
        String sql = "delete from account where id = ?";
        template.update(sql, id);
    }

    @Override
    public User findUserById(int id) {
        String sql = "select * from account where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
    }

    @Override
    public void UpdateUser(User user) {
        String sql = "update account set name = ?,gendet = ?,age = ?,address = ?,qq = ? ,email = ? where id = ?";
        template.update(sql, user.getName(), user.getGendet(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    /**
     * 查询总页数的SQL
     *
     * @param inputPara：获取的参数里面包含页码的信息，可以为空，代表查所有。可以有值，根据值like查找匹配的数据
     * @return 根据传入的参数或者不传参总页码
     */
    @Override
    public int findtotalCount(Map<String, String[]> inputPara) {
        // 定义模糊查找SQL where 1=1 后面拼接的时候跟and比较方便。
        String sql = "select count(*) from  account where 1 = 1";

        // 定义一个字符串拼接对象，用来追加后面的SQL拼接
        StringBuilder sb = new StringBuilder(sql);
        // 处理传入的参数值
        Set<String> keys = inputPara.keySet();
        // 创建一个List集合用来放参数，后面?的参数
        List<Object> params = new ArrayList<>();
        for (String key : keys) {
            // 查询总数分2种情况：不带条件和带条件的，不带条件的走的默认参数,生成的SQL是全部的，如果有页码可rows参数的，直接退出循环，生成SQL
            //                  带条件的是条件查询，post请求，生成的SQL需要带like的模糊查找的SQL。
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }
            String values = inputPara.get(key)[0]; // 每个values数组参数值,都是一个参数,所以获取第0个即可。
            // 判断获取的所有的参数不为空，拼接sql,把参数添加到集合中。
            if (values != null && !"".equals(values)) {
                sb.append(" and " + key + " like ? ");
                params.add("%" + values + "%");
            }
        }
        // sb是个对象,需要转成sql才能穿给下面的查询sql的对象。
        sql = sb.toString();
//        System.out.println(sql);
//        System.out.println(params);
        // 第一个参数sql，后面的参数为转成字符串的数组一一对应。
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    /**
     *
     * @param start:开市页码
     * @param rows ：显示行数
     * @param inputPara ： 传入查询参数
     * @return
     */
    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> inputPara) {
//        String sql = "select * from account where limit ?,?";
        // 定义sql
        String sql = "select * from account where 1 = 1";

        // 这些和上面类似获取参数拼接SQL
        Set<String> keys = inputPara.keySet();
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();

        for (String key : keys) {
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }

            String values = inputPara.get(key)[0]; // 每个参数值,都是一个参数
            if (values != null && !"".equals(values)) {
                sb.append(" and " + key + " like ? ");
                params.add("%" + values + "%");
            }
        }
        // 区别在于这里使用条件晒选
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(rows);
        sql = sb.toString();
//        System.out.println(sql);
//        System.out.println(params);

        return template.query(sql, new BeanPropertyRowMapper<User>(User.class), params.toArray());
    }

}
