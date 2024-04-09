package com.group21.chinasoft_project_barbie_backend.service;

import com.group21.chinasoft_project_barbie_backend.dto.ResidentInfoDTO;
import com.group21.chinasoft_project_barbie_backend.entity.ExceptionInfo;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import com.group21.chinasoft_project_barbie_backend.vo.ExceptionInfoVo;

import java.util.List;

public interface ResidentService {
    ResidentInfoDTO getResidentInfo(int residentId);

    List<ExceptionInfoVo> getAllExceptions(int residentId);
}
