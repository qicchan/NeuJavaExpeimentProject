package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.BedDao;
import com.neuedu.workpart.pojo.Bed;

import java.util.List;

public class BedService {
    private final BedDao bedDao = new BedDao();

    public String addBed(Bed bed) {
        return bedDao.addBed(bed);
    }

    public List<Bed> findAll() {
        return bedDao.findAll();
    }

    public Bed findByBedNumber(String bedNumber) {
        return bedDao.findByBedNumber(bedNumber);
    }

    public List<Bed> findByRoomNumber(String roomNumber) {
        return bedDao.findByRoomNumber(roomNumber);
    }

    public List<Bed> findByStatus(String status) {
        return bedDao.findByStatus(status);
    }

    public boolean updateBed(Bed bed) {
        return bedDao.updateBed(bed);
    }

    public boolean deleteBed(long id) {
        return bedDao.deleteById(id);
    }
}
