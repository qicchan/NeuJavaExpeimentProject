package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.RequirementDao;
import com.neuedu.workpart.pojo.RequirementOut;
import com.neuedu.workpart.pojo.RequirementQuit;

import java.util.List;

import static com.neuedu.workpart.view.swing.MainFrame.currentUser;

public class RequirementService {
    private final RequirementDao dao = new RequirementDao();

    public String createOutRequirement(String reason, String outTime, String expectReturnTime,
                                       String customerName, Integer customerId) {
        return dao.addOutRequirement(new RequirementOut(reason, outTime, expectReturnTime, customerName, customerId));
    }

    public List<RequirementOut> getAllOutRequirements() {
        return dao.findAllOut();
    }

    public List<RequirementOut> searchOutByCustomerName(String name) {
        return dao.findByCustomerNameOut(name);
    }

    public RequirementOut findOutById(Integer id) {
        return dao.findOutById(id);
    }

    public void updateOutRequirement(RequirementOut out) {
        dao.updateOutRequirement(out);
    }

    public String createQuitRequirement(int quitType, String reason, String quitTime,
                                        String customerName, Integer customerId) {
        return dao.addQuitRequirement(new RequirementQuit(quitType, reason, quitTime, customerName, customerId));
    }

    public List<RequirementQuit> getAllQuitRequirements() {
        return dao.findAllQuit();
    }

    public List<RequirementQuit> searchQuitByCustomerName(String name) {
        return dao.findByCustomerNameQuit(name);
    }

    public RequirementQuit findQuitById(Integer id) {
        return dao.findQuitById(id);
    }

    public void registerReturn(Integer id) {
        RequirementOut out = dao.findOutById(id);
        if (out != null) {
            out.registerReturn();
            dao.updateOutRequirement(out);
        } else {
            throw new RuntimeException("未找到该外出申请");
        }
    }

    public RequirementOut findLatestUnreturnedOutByCustomerId(Integer customerId) {
        RequirementOut latest = null;
        for (RequirementOut out : dao.findAllOut()) {
            if (out.getCustomerId().equals(customerId)
                    && out.checkApproveStatus()
                    && out.getActualReturnTime() == null) {
                if (latest == null || out.getId() > latest.getId()) {
                    latest = out;
                }
            }
        }
        return latest;
    }

    public void updateQuitRequirement(RequirementQuit quit) {
        dao.updateQuitRequirement(quit);
    }
}
