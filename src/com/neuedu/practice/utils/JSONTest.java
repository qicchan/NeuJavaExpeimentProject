package com.neuedu.tms.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.practice.pojo.TUser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONTest {
    public static void main(String[] args) throws IOException {
        ObjectMapper om = new ObjectMapper();

        // 1. 创建多个对象
        /*TUser u1 = new TUser();
        u1.setUserName("admin");
        u1.setPasasowrd("123456");

        TUser u2 = new TUser();
        u2.setUserName("zhangsan");
        u2.setPasasowrd("666666");

        // 2. 放入List集合
        List<TUser> userList = new ArrayList<>();

        userList.add(u1);
        userList.add(u2);

        // 3. 集合转JSON字符串
        String jsonStr = om.writeValueAsString(userList);
        System.out.println(jsonStr);

        // 4. 直接存入 D盘 users.json
        try {
            om.writeValue(new File("E:\\dongda2026\\users.json"), userList);
            System.out.println("多个用户数据存入成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // 读取文件转为 List<TUser>
//        List<TUser> list = om.readValue(new File("E:\\dongda2026\\users.json"),
//                om.getTypeFactory().constructCollectionType(List.class, TUser.class));
//        for (TUser user : list) {
//            System.out.println(user.getUserName() + "---" + user.getPasasowrd());
//        }
    }

}
