package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.MUserDao;
import com.neuedu.workpart.pojo.TUser;

import java.io.IOException;
import java.util.List;

public class MUserService {
    MUserDao userDao= new MUserDao();

    public MUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(MUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     *
     *
     * @param user 用户
     * @return 成功/失败的字符串
     */
    public String addUser(TUser user){
        System.out.println("传过来的要添加的用户是"+user);
        System.out.println("稍候,将存到文件中");
        //1.验证用户名，密码，用户类型是否合法
        //2。验证用户名是否重复
        //3.如何验证通过，调用dao层进行存储
        String result= null;
        try {
            result = userDao.addUser(user);
        } catch (IOException e) {
            result="添加失败";

        }
        //返回存储结果
        return result;
    }

    public List<TUser> findAll() {
        List<TUser> list=userDao.findAll();
        return list;
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
        return ((this.userDao.findByUserPassWord(userDao.findByUserName(inputUserName),inputPassword))) &&
                (userDao.findByUserType(userDao.findByUserName(inputUserName),inputUserType));
    }

}
