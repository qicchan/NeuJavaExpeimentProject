package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.workpart.pojo.RequirementOut;
import com.neuedu.workpart.pojo.RequirementQuit;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 申请数据访问层
 * 负责RequirementOut和RequirementQuit对象的JSON文件持久化操作（增删改查）
 * 外出申请数据存储于 data/requirement_out.json
 * 退住申请数据存储于 data/requirement_quit.json
 */
public class RequirementDao {
    /** 外出申请JSON数据文件路径 */
    public static final File OUT_FILE = new File("data\\requirement_out.json");
    /** 退住申请JSON数据文件路径 */
    public static final File QUIT_FILE = new File("data\\requirement_quit.json");
    /** Jackson序列化/反序列化工具 */
    private final ObjectMapper om = new ObjectMapper();

    // ==================== 外出申请相关方法 ====================

    /**
     * 新增外出申请
     * @param requirementOut 外出申请对象（id由系统自动生成）
     * @return 操作结果提示
     */
    public String addOutRequirement(RequirementOut requirementOut) {
        try {
            List<RequirementOut> outList = findAllOut();
            requirementOut.setId(PersistentIdGenerator.getInstance().nextId());
            outList.add(requirementOut);
            om.writeValue(OUT_FILE, outList);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加外出申请失败", e);
        }
    }

    /**
     * 查询全部外出申请
     * @return 外出申请列表，文件不存在或读取异常时返回空列表
     */
    public List<RequirementOut> findAllOut() {
        if (!OUT_FILE.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(OUT_FILE, new TypeReference<List<RequirementOut>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 按客户姓名模糊查询外出申请
     * @param customerName 客户姓名（支持模糊匹配）
     * @return 匹配的外出申请列表
     */
    public List<RequirementOut> findByCustomerNameOut(String customerName) {
        List<RequirementOut> outList = findAllOut();
        List<RequirementOut> result = new ArrayList<>();
        for (RequirementOut out : outList) {
            if (out.getCustomerName() != null && out.getCustomerName().contains(customerName)) {
                result.add(out);
            }
        }
        return result;
    }

    /**
     * 按ID查询外出申请
     * @param id 申请ID
     * @return 匹配的外出申请对象，未找到返回null
     */
    public RequirementOut findOutById(Integer id) {
        List<RequirementOut> outList = findAllOut();
        for (RequirementOut out : outList) {
            if (out.getId().equals(id)) {
                return out;
            }
        }
        return null;
    }

    /**
     * 更新外出申请（用于登记回院时间）
     * @param requirementOut 包含更新信息的外出申请对象
     */
    public void updateOutRequirement(RequirementOut requirementOut) {
        List<RequirementOut> outList = findAllOut();
        for (int i = 0; i < outList.size(); i++) {
            if (outList.get(i).getId().equals(requirementOut.getId())) {
                outList.set(i, requirementOut);
                break;
            }
        }
        try {
            om.writeValue(OUT_FILE, outList);
        } catch (IOException e) {
            throw new RuntimeException("更新外出申请失败", e);
        }
    }

    // ==================== 退住申请相关方法 ====================

    /**
     * 新增退住申请
     * @param requirementQuit 退住申请对象（id由系统自动生成）
     * @return 操作结果提示
     */
    public String addQuitRequirement(RequirementQuit requirementQuit) {
        try {
            List<RequirementQuit> quitList = findAllQuit();
            requirementQuit.setId(PersistentIdGenerator.getInstance().nextId());
            quitList.add(requirementQuit);
            om.writeValue(QUIT_FILE, quitList);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加退住申请失败", e);
        }
    }

    /**
     * 查询全部退住申请
     * @return 退住申请列表，文件不存在或读取异常时返回空列表
     */
    public List<RequirementQuit> findAllQuit() {
        if (!QUIT_FILE.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(QUIT_FILE, new TypeReference<List<RequirementQuit>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 按客户姓名模糊查询退住申请
     * @param customerName 客户姓名（支持模糊匹配）
     * @return 匹配的退住申请列表
     */
    public List<RequirementQuit> findByCustomerNameQuit(String customerName) {
        List<RequirementQuit> quitList = findAllQuit();
        List<RequirementQuit> result = new ArrayList<>();
        for (RequirementQuit quit : quitList) {
            if (quit.getCustomerName() != null && quit.getCustomerName().contains(customerName)) {
                result.add(quit);
            }
        }
        return result;
    }

    /**
     * 按ID查询退住申请
     * @param id 申请ID
     * @return 匹配的退住申请对象，未找到返回null
     */
    public RequirementQuit findQuitById(Integer id) {
        List<RequirementQuit> quitList = findAllQuit();
        for (RequirementQuit quit : quitList) {
            if (quit.getId().equals(id)) {
                return quit;
            }
        }
        return null;
    }
}

