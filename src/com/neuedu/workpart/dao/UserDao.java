package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.utils.JsonUtil;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用用户数据访问对象。
 * <p>通过构造方法传入文件路径，支持管理员和护工两种用户类型的数据操作。</p>
 *
 * @author QICHAN
 */
public class UserDao {
    private final File filePath;

    public UserDao(String filePath) {
        this.filePath = new File(filePath);
    }

    public String addUser(TUser user) throws IOException {
        try {
            List<TUser> userList = findAll();
            user.setId(PersistentIdGenerator.getInstance().nextId());
            userList.add(user);
            JsonUtil.INSTANCE.writeValue(filePath, userList);
            return "添加成功";
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public List<TUser> findAll() {
        if (!filePath.exists()) {
            return new ArrayList<>();
        }
        try {
            return JsonUtil.INSTANCE.readValue(filePath, new TypeReference<List<TUser>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public TUser findByUserName(String userName) {
        for (TUser user : findAll()) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public boolean findByUserPassWord(TUser tUser, String passWord) {
        return tUser != null && tUser.getPassword().equals(passWord);
    }

    public boolean findByUserType(TUser tUser, int type) {
        return tUser != null && tUser.getUserType() == type;
    }

    public boolean updateUser(TUser newUser) {
        try {
            List<TUser> userList = findAll();
            boolean isUpdate = false;
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getId().equals(newUser.getId())) {
                    userList.set(i, newUser);
                    isUpdate = true;
                    break;
                }
            }
            if (isUpdate) {
                JsonUtil.INSTANCE.writeValue(filePath, userList);
            }
            return isUpdate;
        } catch (IOException e) {
            throw new RuntimeException("修改用户异常", e);
        }
    }

    public boolean deleteById(long id) {
        try {
            List<TUser> userList = findAll();
            boolean isDelete = false;
            for (int i = 0; i < userList.size(); i++) {
                TUser user = userList.get(i);
                if (user.getId() != null && user.getId().equals(id)) {
                    userList.remove(i);
                    isDelete = true;
                    break;
                }
            }
            if (isDelete) {
                JsonUtil.INSTANCE.writeValue(filePath, userList);
            }
            return isDelete;
        } catch (IOException e) {
            throw new RuntimeException("删除用户失败", e);
        }
    }
}
