package com.neuedu.practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.practice.pojo.TUser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONStudy01 {
    public static void main(String[] args) throws IOException {
            //把单个管理员存到文件里
            //1.实例化一个对象，类型为TUser
            //TUser是个类，admin 是TUser的实例对象
            TUser admin01 = new TUser();
            admin01.setUserName("admin01");
            admin01.setPassWord("123456");
            admin01.setId(1);
            //利用有参构造器创建对象
            TUser admin02 = new TUser(1, "admin02", "123456");
            //2.把对象转为JSON字符串
            //实例化一个json转化的工具类
            ObjectMapper om = new ObjectMapper();
            //调用
            String str = om.writeValueAsString(admin01);
            System.out.println(str);
            //3.调用Java文件操作相关方法存到文件 待完成
            File file01 = new File("E:\\java21\\javacode\\JavaExperiment26\\data\\users.txt");
            FileWriter fw = new FileWriter(file01,true);
            fw.write(str);
            fw.close();
            //1.先从文件中把字符串取出来

            //2.把字符串转为java对象格式
            TUser userRead = om.readValue(str, TUser.class);
            System.out.println(userRead.toString());

    }
}
