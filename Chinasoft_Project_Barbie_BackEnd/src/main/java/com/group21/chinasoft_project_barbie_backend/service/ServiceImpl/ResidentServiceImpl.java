package com.group21.chinasoft_project_barbie_backend.service.ServiceImpl;

import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import com.group21.chinasoft_project_barbie_backend.exception.GetInfoException;
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

        ResidentInfo residentInfo = residentMapper.findResidentInfoById(residentId);
        if(residentInfo!=null){
            return residentInfo;
        }else {
            throw new GetInfoException("id不存在");
        }
    }

}
