package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.UserDao;
import com.neuedu.workpart.pojo.TUser;

import java.io.IOException;
import java.util.List;

/**
 * 通用用户业务逻辑服务层。
 * <p>通过构造方法传入文件路径，支持管理员和护工两种用户类型。</p>
 *
 * @author QICHAN
 */
public class UserService {
    private final UserDao userDao;

    public UserService(String filePath) {
        this.userDao = new UserDao(filePath);
    }

    public String addUser(TUser user) {
        if (userDao.findByUserName(user.getUserName()) != null) {
            return "用户名已存在";
        }
        try {
            return userDao.addUser(user);
        } catch (IOException e) {
            return "添加失败";
        }
    }

    public List<TUser> findAll() {
        return userDao.findAll();
    }

    public TUser findByUserName(String inputUserName) {
        return userDao.findByUserName(inputUserName);
    }

    public boolean updateUser(TUser user) {
        return userDao.updateUser(user);
    }

    public boolean deleteUser(long id) {
        return userDao.deleteById(id);
    }

    public boolean findUserByAll(int inputUserType, String inputUserName, String inputPassword) {
        TUser user = userDao.findByUserName(inputUserName);
        return userDao.findByUserPassWord(user, inputPassword)
                && userDao.findByUserType(user, inputUserType);
    }
}
