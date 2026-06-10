package com.neuedu.workpart.dao;

import com.neuedu.workpart.pojo.TUser;

import java.io.IOException;
import java.util.List;

/**
 * 护工用户数据访问对象（DAO）。
 * <p>内部委托给{@link UserDao}，数据文件：data/manager_users.json</p>
 *
 * @author QICHAN
 */
public class MUserDao {
    private static final String FILE_PATH = "data/manager_users.json";
    private final UserDao delegate = new UserDao(FILE_PATH);

    public String addUser(TUser user) throws IOException {
        return delegate.addUser(user);
    }

    public List<TUser> findAll() {
        return delegate.findAll();
    }

    public TUser findByUserName(String userName) {
        return delegate.findByUserName(userName);
    }

    public boolean findByUserPassWord(TUser tUser, String passWord) {
        return delegate.findByUserPassWord(tUser, passWord);
    }

    public boolean findByUserType(TUser tUser, int type) {
        return delegate.findByUserType(tUser, type);
    }

    public boolean updateUser(TUser newUser) {
        return delegate.updateUser(newUser);
    }

    public boolean deleteById(long id) {
        return delegate.deleteById(id);
    }
}
