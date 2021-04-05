package com.contract.service;



import com.contract.domain.Power;

import java.util.List;

public interface PowerService {
    List<Power> getTreePowerList(String adminId);

    List<Power> getPowerList(String adminId);
}
