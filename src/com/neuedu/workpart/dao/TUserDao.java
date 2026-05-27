package com.neuedu.workpart.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.utils.PersistentIdGenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;


public class TUserDao {
    // 数据文件路径，相对路径
    public static final File FILE_NAME = new File("data\\admin_users.json");
    private final ObjectMapper om = new ObjectMapper();

    /**
     * 添加用户（带自增ID，不会覆盖文件，支持多个用户）
     */
    public String addUser(TUser user) throws IOException {
        try {
            // 1. 先读取文件中已有的所有用户
            List<TUser> userList = findAll();
            // 2. 设置自增ID（重启不丢失）
            user.setId(PersistentIdGenerator.getInstance().nextId());
            // 4. 添加新用户到集合
            userList.add(user);
            // 5. 把整个集合重新写入文件（覆盖，因为是全量更新）
            om.writeValue(FILE_NAME, userList);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加用户失败", e);
        }
    }

    /**
     * 查询所有用户（已补全 ✅）
     */
    public List<TUser> findAll() {
        // 文件不存在 → 返回空集合
        if (!FILE_NAME.exists()) {
            return new ArrayList<>();
        }
        try {
            // 读取文件 JSON 转 List<TUser>
            return om.readValue(FILE_NAME, new TypeReference<List<TUser>>() {});
        } catch (IOException e) {
            // 文件为空/格式错误 → 返回空集合
            return new ArrayList<>();
        }
    }
    /**
     * 根据用户名查询用户（精确匹配）
     */
    public TUser findByUserName(String userName) {
        List<TUser> userList = findAll();

        // 遍历所有用户，找到用户名相同的
        for (TUser user : userList) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }

        // 没找到返回 null
        return null;
    }
    public boolean findByUserPassWord(TUser tUser,String passWord) {
            if (tUser.getPassWord().equals(passWord)) {
                return tUser.getPassWord().equals(passWord);
            }
        // 没找到返回 null
        return false;
    }
    public boolean findByUserType(TUser tUser,int Type) {
            if (tUser.getUserType() == Type) {
                return tUser.getUserType() == Type;
            }
        // 没找到返回错误
        return false;
    }

    /**
     * 普通循环方式修改用户，不使用stream
     * @param newUser 待修改的用户对象，携带id标识
     * @return true修改成功，false用户不存在
     */
    public boolean updateUser(TUser newUser) {
        try {
            List<TUser> userList = findAll();
            boolean isUpdate = false;

            // 普通for循环遍历查找
            for (int i = 0; i < userList.size(); i++) {
                TUser user = userList.get(i);
                if (user.getId().equals(newUser.getId())) {
                    userList.set(i, newUser);
                    isUpdate = true;
                    break;
                }
            }

            // 找到用户则写入文件保存修改
            if (isUpdate) {
                om.writeValue(FILE_NAME, userList);
            }
            return isUpdate;
        } catch (IOException e) {
            throw new RuntimeException("修改用户异常", e);
        }
    }

    /**
     * 根据ID删除用户
     * @param id 要删除的用户ID
     * @return true删除成功 false用户不存在
     */
    public boolean deleteById(long id) {
        try {
            List<TUser> userList = findAll();
            boolean isDelete = false;

            // 普通for循环查找并删除
            for (int i = 0; i < userList.size(); i++) {
                TUser user = userList.get(i);
                if (user.getId() == id) {
                    userList.remove(i);
                    isDelete = true;
                    break;
                }
            }

            // 如果删除成功，重新写入文件
            if (isDelete) {
                om.writeValue(FILE_NAME, userList);
            }
            return isDelete;
        } catch (IOException e) {
            throw new RuntimeException("删除用户失败", e);
        }
    }
    /**
     * 根据用户名查询用户（Stream 流式写法）
     */
    public TUser findByUserNameStream(String userName) {
        return findAll().stream()
                // 过滤：用户名相等
                .filter(user -> user.getUserName().equals(userName))
                // 取第一个
                .findFirst()
                // 没有找到返回 null
                .orElse(null);
    }
}