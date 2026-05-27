//package com.neuedu.tms.utils;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.neuedu.tms.pojo.TUser;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class UserJsonUtil {
//    // 文件路径
//    private static final String FILE_PATH = "E:\\dongda2026\\users.json";
//    private static final ObjectMapper OM = new ObjectMapper();
//
//    // 初始化文件，不存在则创建空集合
//    static {
//        File file = new File(FILE_PATH);
//        if (!file.exists()) {
//            try {
//                OM.writeValue(file, new ArrayList<TUser>());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 读取全部用户
//     */
//    public static List<TUser> findAll() {
//        try {
//            return OM.readValue(new File(FILE_PATH), new TypeReference<List<TUser>>() {});
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//
//    /**
//     * 添加用户
//     */
//    public static void addUser(TUser user) {
//        List<TUser> list = findAll();
//        list.add(user);
//        saveData(list);
//    }
//
//    /**
//     * 根据用户名修改密码
//     */
//    public static boolean updateUser(String username, String newPwd) {
//        List<TUser> list = findAll();
//        boolean flag = false;
//        for (TUser u : list) {
//            if (username.equals(u.getUserName())) {
//                u.setPasasowrd(newPwd);
//                flag = true;
//                break;
//            }
//        }
//        if (flag) {
//            saveData(list);
//        }
//        return flag;
//    }
//
//    /**
//     * 根据用户名删除用户
//     */
//    public static boolean deleteUser(String username) {
//        List<TUser> list = findAll();
//        boolean remove = list.removeIf(u -> username.equals(u.getUserName()));
//        if (remove) {
//            saveData(list);
//        }
//        return remove;
//    }
//
//    /**
//     * 根据用户名查询单个用户
//     */
//    public static TUser findUserByName(String username) {
//        List<TUser> list = findAll();
//        Optional<TUser> optional = list.stream()
//                .filter(u -> username.equals(u.getUserName()))
//                .findFirst();
//        return optional.orElse(null);
//    }
//
//    /**
//     * 保存集合到JSON文件
//     */
//    private static void saveData(List<TUser> list) {
//        try {
//            OM.writeValue(new File(FILE_PATH), list);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//
