package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.BedDao;
import com.neuedu.workpart.pojo.Bed;

import java.io.IOException;
import java.util.List;

public class BedService {
    BedDao bedDao = new BedDao();

    public BedDao getBedDao() {
        return bedDao;
    }

    public void setBedDao(BedDao bedDao) {
        this.bedDao = bedDao;
    }

    /**
     * 添加床位
     *
     * @param bed 床位对象
     * @return 成功/失败的字符串
     */
    public String addBed(Bed bed) {
        System.out.println("传过来的要添加的床位是" + bed);
        System.out.println("稍候,将存到文件中");
        String result = null;
        try {
            result = bedDao.addBed(bed);
        } catch (IOException e) {
            result = "添加失败";
        }
        return result;
    }

    /**
     * 查询所有床位
     */
    public List<Bed> findAll() {
        List<Bed> list = bedDao.findAll();
        return list;
    }

    /**
     * 根据床位号查询床位
     */
    public Bed findByBedNumber(String inputBedNumber) {
        return bedDao.findByBedNumber(inputBedNumber);
    }

    /**
     * 根据房间号查询床位列表
     */
    public List<Bed> findByRoomNumber(String inputRoomNumber) {
        return bedDao.findByRoomNumber(inputRoomNumber);
    }

    /**
     * 根据状态查询床位列表
     */
    public List<Bed> findByStatus(String inputStatus) {
        return bedDao.findByStatus(inputStatus);
    }

    /**
     * 修改床位信息
     */
    public boolean updateBed(Bed bed) {
        return bedDao.updateBed(bed);
    }

    /**
     * 根据ID删除床位
     */
    public boolean deleteBed(long id) {
        return bedDao.deleteById(id);
    }
}