package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.workpart.pojo.BedUsageDetail;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BedUsageDetailDao {
    public static final File FILE_NAME = new File("data\\bed_usage_details.json");
    private final ObjectMapper om = new ObjectMapper();

    public String addDetail(BedUsageDetail detail) throws IOException {
        List<BedUsageDetail> list = findAll();
        detail.setId(String.valueOf(PersistentIdGenerator.getInstance().nextId()));
        list.add(detail);
        om.writeValue(FILE_NAME, list);
        return "添加成功";
    }

    public List<BedUsageDetail> findAll() {
        if (!FILE_NAME.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(FILE_NAME, new TypeReference<List<BedUsageDetail>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public BedUsageDetail findById(String id) {
        return findAll().stream()
                .filter(d -> d.getId().equals(id))
                .findFirst().orElse(null);
    }

    public BedUsageDetail findByCustomerIdAndStatus(String customerId, String status) {
        return findAll().stream()
                .filter(d -> d.getCustomerId().equals(customerId) && d.getStatus().equals(status))
                .findFirst().orElse(null);
    }

    public boolean updateDetail(BedUsageDetail newDetail) {
        try {
            List<BedUsageDetail> list = findAll();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(newDetail.getId())) {
                    list.set(i, newDetail);
                    om.writeValue(FILE_NAME, list);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("修改床位使用详情异常", e);
        }
    }

    public List<BedUsageDetail> multiConditionSearch(String name, String date, String status) {
        List<BedUsageDetail> all = findAll();
        return all.stream()
                .filter(d -> {
                    if (name != null && !name.isEmpty()) {
                        if (d.getCustomerName() == null || !d.getCustomerName().contains(name)) {
                            return false;
                        }
                    }
                    if (date != null && !date.isEmpty()) {
                        if (d.getStartTime() == null || !d.getStartTime().equals(date)) {
                            return false;
                        }
                    }
                    if (status != null && !status.isEmpty()) {
                        if (d.getStatus() == null || !d.getStatus().equals(status)) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
