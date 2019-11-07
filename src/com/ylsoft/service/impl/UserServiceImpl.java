package com.ylsoft.service.impl;

import com.ylsoft.dao.UserDao;
import com.ylsoft.dao.impl.UserDaoImpl;
import com.ylsoft.domain.Admin;
import com.ylsoft.domain.BeanPage;
import com.ylsoft.domain.User;
import com.ylsoft.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    @Override
    public Integer CreateAdmin(String username, String password, String mail) {
        return dao.CreateAdmin(username, password, mail);
    }

    @Override
    public Admin SelectAdmin(String username, String password) {
        return dao.SelectAdmin(username, password);
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    @Override
    public void DelUser(String userid) {
        dao.delUser(Integer.parseInt(userid));
    }

    @Override
    public User findUserById(String userId) {
        return dao.findUserById(Integer.parseInt(userId));
    }

    @Override
    public void UpdateUser(User user) {
        dao.UpdateUser(user);
    }

    @Override
    public void delSelectUser(String[] uids) {
        for (String uid : uids) {
            dao.delUser(Integer.parseInt(uid));
        }
    }

    /** 设置分页的server
     * @param _currentPage :当前页码是第几页
     * @param _rows :每页显示的条数
     * @param inputPara
     * @return
     */
    @Override
    public BeanPage findUserByPage(String _currentPage, String _rows, Map<String, String[]> inputPara) {
        // 先把传入的参数转换成int,后面用到
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        // 如果参数是0 改为1，因为没有0页
        if(currentPage <=0) {
            currentPage = 1;
        }

        // 创建页码对象
        /**
         *private int totalCount;        // 总共多少条数据
         private int totalPage;          // 总共多少页
         private List list;              // 每页显示的数据集合
         private int rows;               // 每页显示多少条数据
         private int currentPage;        // 当前是选中是第几页
         */
        BeanPage bg = new BeanPage();


        // 获取根据总数据量是多少条，不管是查所有还是条件查询
        int totalCount = dao.findtotalCount(inputPara);
        bg.setTotalCount(totalCount);

        // 根据总数计算需要多少页
        int totalPage = (totalCount % rows) == 0 ? (totalCount / rows) : (totalCount / rows) + 1;
        bg.setTotalPage(totalPage);     // 总页码

//        if(currentPage != totalPage){
//            currentPage = totalPage;
//        }

        bg.setCurrentPage(currentPage); // 当前页码是第几页
        bg.setRows(rows);               // 每页显示的条数


        // 下面3段代码，获取每页的数据：需要从表里查，从哪条开始查---5条，使用里limit条件查询。
        // start是根据当前页算出来的，比如当前是第一页，查询出来的数据，1-5条数据展示给客户端，如果是第二页2-5条数据展示给客户端。
        /**
         * 1: 0 5
         * 2: 5 5
         * 3: 10 5
         *公式就是 （当前页 - 1）* rows(5)
         * 每一次查询分页，都要请求
         */
        int start = (currentPage - 1) * rows;   // 计算开始
        List<User> list =  dao.findByPage(start,rows,inputPara);
        bg.setList(list);               // 每页的数据

        return bg;
    }

}
