package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.BedUsageDetailDao;
import com.neuedu.workpart.pojo.BedUsageDetail;

import java.util.List;

public class BedUsageDetailService {
    private final BedUsageDetailDao dao = new BedUsageDetailDao();

    public String addDetail(BedUsageDetail detail) {
        return dao.addDetail(detail);
    }

    public List<BedUsageDetail> findAll() {
        return dao.findAll();
    }

    public BedUsageDetail findById(String id) {
        return dao.findById(id);
    }

    public BedUsageDetail findActiveByCustomerId(String customerId) {
        return dao.findByCustomerIdAndStatus(customerId, "正在使用");
    }

    public boolean updateDetail(BedUsageDetail detail) {
        return dao.updateDetail(detail);
    }

    public BedUsageDetail findByBuildingRoomBed(String building, String roomNumber, String bedNumber) {
        return dao.findByBuildingRoomBed(building, roomNumber, bedNumber);
    }

    public List<BedUsageDetail> multiConditionSearch(String name, String date, String status) {
        return dao.multiConditionSearch(name, date, status);
    }
}
