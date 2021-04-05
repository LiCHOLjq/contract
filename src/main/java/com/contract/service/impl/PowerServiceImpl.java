package com.contract.service.impl;


import com.contract.domain.Admin;
import com.contract.domain.Power;
import com.contract.mapper.AdminMapper;
import com.contract.mapper.PowerMapper;
import com.contract.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    private PowerMapper powerMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Power> getTreePowerList(String adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);

        List<Power> allPowerList = powerMapper.selectByAdminRole(admin.getAdminRole());
        List<Power> fatherPowerList = new ArrayList<>();
        for(Power power : allPowerList){
            if(power.getPowerLevel().equals(1)){
                power.setChildrenPowerList(new ArrayList<>());
                fatherPowerList.add(power);
            }
        }
        for(Power power : fatherPowerList){
            allPowerList.remove(power);
        }
        for(Power powerChild : allPowerList){
            for(Power powerFather : fatherPowerList){
                if(powerFather.getPowerId().equals(powerChild.getPowerFather())){
                    powerFather.getChildrenPowerList().add(powerChild);
                }
            }
        }
        return fatherPowerList;
    }

    @Override
    public List<Power> getPowerList(String adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        return powerMapper.selectByAdminRole(admin.getAdminRole());
    }
}
