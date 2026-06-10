package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neuedu.workpart.pojo.Bed;
import com.neuedu.workpart.utils.JsonUtil;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BedDao {
    public static final File FILE_NAME = new File("data/beds.json");
    private static final com.fasterxml.jackson.databind.ObjectMapper om = JsonUtil.INSTANCE;

    public String addBed(Bed bed) {
        try {
            List<Bed> bedList = findAll();
            bed.setId(String.valueOf(PersistentIdGenerator.getInstance().nextId()));
            bedList.add(bed);
            om.writeValue(FILE_NAME, bedList);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加床位失败", e);
        }
    }

    public List<Bed> findAll() {
        if (!FILE_NAME.exists()) return new ArrayList<>();
        try {
            return om.readValue(FILE_NAME, new TypeReference<List<Bed>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Bed findByBedNumber(String bedNumber) {
        for (Bed bed : findAll()) {
            if (bed.getBedNumber().equals(bedNumber)) return bed;
        }
        return null;
    }

    public List<Bed> findByRoomNumber(String roomNumber) {
        List<Bed> result = new ArrayList<>();
        for (Bed bed : findAll()) {
            if (bed.getRoomNumber().equals(roomNumber)) result.add(bed);
        }
        return result;
    }

    public List<Bed> findByStatus(String status) {
        List<Bed> result = new ArrayList<>();
        for (Bed bed : findAll()) {
            if (bed.getStatus().equals(status)) result.add(bed);
        }
        return result;
    }

    public boolean updateBed(Bed newBed) {
        try {
            List<Bed> bedList = findAll();
            for (int i = 0; i < bedList.size(); i++) {
                if (bedList.get(i).getId().equals(newBed.getId())) {
                    bedList.set(i, newBed);
                    om.writeValue(FILE_NAME, bedList);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("修改床位异常", e);
        }
    }

    public boolean deleteById(long id) {
        try {
            List<Bed> bedList = findAll();
            for (int i = 0; i < bedList.size(); i++) {
                if (bedList.get(i).getId().equals(String.valueOf(id))) {
                    bedList.remove(i);
                    om.writeValue(FILE_NAME, bedList);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("删除床位失败", e);
        }
    }
}
