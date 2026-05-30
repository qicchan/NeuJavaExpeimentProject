package com.neuedu.workpart.dao;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.workpart.pojo.Bed;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BedDao {
    public static final File FILE_NAME = new File("data\\beds.json");
    private final ObjectMapper om = new ObjectMapper();

    /**
     * 添加床位（带自增ID）
     */
    public String addBed(Bed bed) throws IOException {
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

    /**
     * 查询所有床位
     */
    public List<Bed> findAll() {
        if (!FILE_NAME.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(FILE_NAME, new TypeReference<List<Bed>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 根据床位号查询床位（精确匹配）
     */
    public Bed findByBedNumber(String bedNumber) {
        List<Bed> bedList = findAll();
        for (Bed bed : bedList) {
            if (bed.getBedNumber().equals(bedNumber)) {
                return bed;
            }
        }
        return null;
    }

    /**
     * 根据房间号查询床位
     */
    public List<Bed> findByRoomNumber(String roomNumber) {
        List<Bed> bedList = findAll();
        List<Bed> result = new ArrayList<>();
        for (Bed bed : bedList) {
            if (bed.getRoomNumber().equals(roomNumber)) {
                result.add(bed);
            }
        }
        return result;
    }

    /**
     * 根据状态查询床位
     */
    public List<Bed> findByStatus(String status) {
        List<Bed> bedList = findAll();
        List<Bed> result = new ArrayList<>();
        for (Bed bed : bedList) {
            if (bed.getStatus().equals(status)) {
                result.add(bed);
            }
        }
        return result;
    }

    /**
     * 修改床位信息
     */
    public boolean updateBed(Bed newBed) {
        try {
            List<Bed> bedList = findAll();
            boolean isUpdate = false;

            for (int i = 0; i < bedList.size(); i++) {
                Bed bed = bedList.get(i);
                if (bed.getId().equals(newBed.getId())) {
                    bedList.set(i, newBed);
                    isUpdate = true;
                    break;
                }
            }

            if (isUpdate) {
                om.writeValue(FILE_NAME, bedList);
            }
            return isUpdate;
        } catch (IOException e) {
            throw new RuntimeException("修改床位异常", e);
        }
    }

    /**
     * 根据ID删除床位
     */
    public boolean deleteById(long id) {
        try {
            List<Bed> bedList = findAll();
            boolean isDelete = false;

            for (int i = 0; i < bedList.size(); i++) {
                Bed bed = bedList.get(i);
                if (bed.getId().equals(String.valueOf(id))) {
                    bedList.remove(i);
                    isDelete = true;
                    break;
                }
            }

            if (isDelete) {
                om.writeValue(FILE_NAME, bedList);
            }
            return isDelete;
        } catch (IOException e) {
            throw new RuntimeException("删除床位失败", e);
        }
    }

    /**
     * 根据床位号查询床位（Stream流式写法）
     */
    public Bed findByBedNumberStream(String bedNumber) {
        return findAll().stream()
                .filter(bed -> bed.getBedNumber().equals(bedNumber))
                .findFirst()
                .orElse(null);
    }
}