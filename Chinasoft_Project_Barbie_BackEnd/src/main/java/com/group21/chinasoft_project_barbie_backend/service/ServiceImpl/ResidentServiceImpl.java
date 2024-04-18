package com.group21.chinasoft_project_barbie_backend.service.ServiceImpl;

import com.group21.chinasoft_project_barbie_backend.context.BaseContext;
import com.group21.chinasoft_project_barbie_backend.dto.AdmissionMonthDTO;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentAgeDTO;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentInfoDTO;
import com.group21.chinasoft_project_barbie_backend.dto.StaffEvaluateDTO;
import com.group21.chinasoft_project_barbie_backend.entity.ExceptionInfo;
import com.group21.chinasoft_project_barbie_backend.entity.HealthInfo;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import com.group21.chinasoft_project_barbie_backend.exception.GetInfoException;
import com.group21.chinasoft_project_barbie_backend.mapper.ResidentMapper;
import com.group21.chinasoft_project_barbie_backend.service.ResidentService;
import com.group21.chinasoft_project_barbie_backend.vo.ExceptionInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    ResidentMapper residentMapper;
    @Override
    public ResidentInfoDTO getResidentInfo() {

        ResidentInfo residentInfo = residentMapper.findResidentInfoById(BaseContext.getCurrentId().intValue());
        if(residentInfo!=null){
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            cal1.setTime(residentInfo.getDateOfBirth());
            cal2.setTime(calendar.getTime());
            int year1=cal1.get(Calendar.YEAR);
            int year2=cal2.get(Calendar.YEAR);
            return new ResidentInfoDTO(residentInfo.getFirstName(),residentInfo.getLastName(),residentInfo.getUsername(),year2-year1);
        }else {
            throw new GetInfoException("id不存在");
        }
    }

    @Override
    public List<ExceptionInfoVo> getAllExceptions() {
        List<ExceptionInfo> list = residentMapper.findAllExceptions(BaseContext.getCurrentId().intValue());
        List<ExceptionInfoVo> list2 = new ArrayList<>();
        //将list中的time序列化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ExceptionInfo exceptionInfo : list) {
            ExceptionInfoVo exceptionInfoVo = new ExceptionInfoVo();
            exceptionInfoVo.setExceptionStartTime(formatter.format(exceptionInfo.getExceptionStartTime()));
            exceptionInfoVo.setPhone(exceptionInfo.getPhone());
            exceptionInfoVo.setExceptionInfo(exceptionInfo.getExceptionInfo());

            if(exceptionInfo.getExceptionEndTime()!=null){
                exceptionInfoVo.setExceptionEndTime(formatter.format(exceptionInfo.getExceptionEndTime()));
                exceptionInfoVo.setIsCurrent(false);
            }else {
                exceptionInfoVo.setIsCurrent(true);
            }
            list2.add(exceptionInfoVo);
        }
        return list2;
    }

    @Override
    public HealthInfo getNowInfo() {
        return residentMapper.getNewestInfoById(BaseContext.getCurrentId().intValue());
    }

    @Override
    public void cancelException(int residentId) {
        residentMapper.updateExceptionEndtimeById(residentId);
    }

    @Override
    public int getResidentId(int memberId) {
        return residentMapper.getResidentIdByMemberId(memberId);
    }

    @Override
    public List<ResidentAgeDTO> getAllResidentInfo() {
        List<ResidentInfo> list = residentMapper.getAllResidentInfo();
        List<ResidentAgeDTO> list2 = new ArrayList<>();
        for (ResidentInfo residentInfo : list){
            if(residentInfo!=null) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                Calendar calendar = Calendar.getInstance();
                cal1.setTime(residentInfo.getDateOfBirth());
                cal2.setTime(calendar.getTime());
                int year1 = cal1.get(Calendar.YEAR);
                int year2 = cal2.get(Calendar.YEAR);
                int age = year2-year1;
                String ageMessage = null;
                if(age <60){
                    ageMessage = "60以下";
                }else if (age>60&&age<70){
                    ageMessage = "60-70";
                }else if (age>70&&age<80){
                    ageMessage = "70-80";
                } else if (age>80) {
                    ageMessage = "80以上";
                }
                list2.add(new ResidentAgeDTO(ageMessage));
            }
        }
        return list2;
    }

    @Override
    public List<StaffEvaluateDTO> getEvaluation() {
        List<StaffEvaluateDTO> list = residentMapper.getEvaluation();
        return list;
    }

    @Override
    public List<AdmissionMonthDTO> getAdmissionMonth() {
        List<AdmissionMonthDTO> list = residentMapper.getAdmissionMonth();
        return list;
    }

}
