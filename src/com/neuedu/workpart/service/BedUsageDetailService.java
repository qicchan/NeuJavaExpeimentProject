package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.BedUsageDetailDao;
import com.neuedu.workpart.pojo.BedUsageDetail;

import java.io.IOException;
import java.util.List;

public class BedUsageDetailService {
    BedUsageDetailDao dao = new BedUsageDetailDao();

    public String addDetail(BedUsageDetail detail) {
        try {
            return dao.addDetail(detail);
        } catch (IOException e) {
            return "添加失败";
        }
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

    public List<BedUsageDetail> multiConditionSearch(String name, String date, String status) {
        return dao.multiConditionSearch(name, date, status);
    }
}
