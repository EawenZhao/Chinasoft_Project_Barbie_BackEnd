package com.group21.chinasoft_project_barbie_backend.service.ServiceImpl;

import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import com.group21.chinasoft_project_barbie_backend.mapper.ResidentMapper;
import com.group21.chinasoft_project_barbie_backend.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    ResidentMapper residentMapper;

    @Override
    public ResidentInfo getResidentInfo(int residentId) {

        return residentMapper.findResidentInfoById(residentId);
    }
}
