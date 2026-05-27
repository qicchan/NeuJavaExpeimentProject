//package com.neuedu.tms.utils;
//
//import com.neuedu.tms.pojo.TUser;
//
//public class TestMain {
//    public static void main(String[] args) {
//        // 1. 添加
//        /*TUser u1 = new TUser();
//        u1.setUserName("admin");
//        u1.setPasasowrd("123456");
//        UserJsonUtil.addUser(u1);
//
//        TUser u2 = new TUser();
//        u2.setUserName("张三");
//        u2.setPasasowrd("666666");
//        UserJsonUtil.addUser(u2);*/
//
//        // 2. 查询所有
//        System.out.println("全部用户：" + UserJsonUtil.findAll());
//
//        // 3. 单个查询
//        System.out.println("查询admin：" + UserJsonUtil.findUserByName("admin"));
//
//        // 4. 修改
//        UserJsonUtil.updateUser("admin", "888888");
//        System.out.println("修改后：" + UserJsonUtil.findUserByName("admin"));
//
//        // 5. 删除
//        UserJsonUtil.deleteUser("张三");
//        System.out.println("删除后全部：" + UserJsonUtil.findAll());
//    }
//}