package com.neuedu.workpart.service;

import com.neuedu.workpart.pojo.TUser;

import java.util.List;

/**
 * 管理员用户业务逻辑服务层。
 * <p>内部委托给{@link UserService}，数据文件：data/admin_users.json</p>
 *
 * @author QICHAN
 */
public class TUserService {
    private static final String FILE_PATH = "data/admin_users.json";
    private final UserService delegate = new UserService(FILE_PATH);

    public String addUser(TUser user) {
        return delegate.addUser(user);
    }

    public List<TUser> findAll() {
        return delegate.findAll();
    }

    public TUser findByUserName(String inputUserName) {
        return delegate.findByUserName(inputUserName);
    }

    public boolean updateUser(TUser user) {
        return delegate.updateUser(user);
    }

    public boolean deleteUser(long id) {
        return delegate.deleteUser(id);
    }

    public boolean findUserByAll(int inputUserType, String inputUserName, String inputPassword) {
        return delegate.findUserByAll(inputUserType, inputUserName, inputPassword);
    }
}
