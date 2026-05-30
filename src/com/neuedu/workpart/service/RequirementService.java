package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.RequirementDao;
import com.neuedu.workpart.pojo.RequirementOut;
import com.neuedu.workpart.pojo.RequirementQuit;

import java.util.List;

/**
 * 申请业务逻辑层
 * 处理外出申请和退住申请的业务逻辑
 */
public class RequirementService {
    /** 申请数据访问层对象 */
    private RequirementDao requirementDao = new RequirementDao();

    // ==================== 外出申请相关业务方法 ====================

    /**
     * 创建外出申请
     * @param reason 外出事由
     * @param outTime 外出时间
     * @param expectReturnTime 预计回院时间
     * @param customerName 客户姓名
     * @param customerId 客户ID
     * @return 操作结果提示
     */
    public String createOutRequirement(String reason, String outTime, String expectReturnTime,
                                       String customerName, Integer customerId) {
        RequirementOut requirementOut = new RequirementOut(reason, outTime, expectReturnTime,
                customerName, customerId);
        return requirementDao.addOutRequirement(requirementOut);
    }

    /**
     * 查询所有外出申请
     * @return 外出申请列表
     */
    public List<RequirementOut> getAllOutRequirements() {
        return requirementDao.findAllOut();
    }

    /**
     * 按客户姓名模糊查询外出申请
     * @param customerName 客户姓名（支持模糊匹配）
     * @return 匹配的外出申请列表
     */
    public List<RequirementOut> searchOutByCustomerName(String customerName) {
        return requirementDao.findByCustomerNameOut(customerName);
    }

    /**
     * 登记回院时间
     * @param id 外出申请ID
     */
    public void registerReturn(Integer id) {
        RequirementOut requirementOut = requirementDao.findOutById(id);
        if (requirementOut != null) {
            requirementOut.registerReturn();
            requirementDao.updateOutRequirement(requirementOut);
        } else {
            throw new RuntimeException("未找到该外出申请");
        }
    }

    // ==================== 退住申请相关业务方法 ====================

    /**
     * 创建退住申请
     * @param quitType 退住类型
     * @param reason 退住原因
     * @param quitTime 退住时间
     * @param customerName 客户姓名
     * @param customerId 客户ID
     * @return 操作结果提示
     */
    public String createQuitRequirement(String quitType, String reason, String quitTime,
                                        String customerName, Integer customerId) {
        RequirementQuit requirementQuit = new RequirementQuit(quitType, reason, quitTime,
                customerName, customerId);
        return requirementDao.addQuitRequirement(requirementQuit);
    }

    /**
     * 查询所有退住申请
     * @return 退住申请列表
     */
    public List<RequirementQuit> getAllQuitRequirements() {
        return requirementDao.findAllQuit();
    }

    /**
     * 按客户姓名模糊查询退住申请
     * @param customerName 客户姓名（支持模糊匹配）
     * @return 匹配的退住申请列表
     */
    public List<RequirementQuit> searchQuitByCustomerName(String customerName) {
        return requirementDao.findByCustomerNameQuit(customerName);
    }
}
