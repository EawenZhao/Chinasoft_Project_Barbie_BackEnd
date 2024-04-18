package com.group21.chinasoft_project_barbie_backend.service;

import com.group21.chinasoft_project_barbie_backend.dto.AdmissionMonthDTO;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentAgeDTO;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentInfoDTO;
import com.group21.chinasoft_project_barbie_backend.dto.StaffEvaluateDTO;
import com.group21.chinasoft_project_barbie_backend.entity.ExceptionInfo;
import com.group21.chinasoft_project_barbie_backend.entity.HealthInfo;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import com.group21.chinasoft_project_barbie_backend.vo.ExceptionInfoVo;

import java.util.List;

public interface ResidentService {
    ResidentInfoDTO getResidentInfo();

    List<ExceptionInfoVo> getAllExceptions();

    HealthInfo getNowInfo();

    void cancelException(int residentId);

    int getResidentId(int memberId);

    List<ResidentAgeDTO> getAllResidentInfo();

    List<StaffEvaluateDTO> getEvaluation();

    List<AdmissionMonthDTO> getAdmissionMonth();
}
