package com.group21.chinasoft_project_barbie_backend.controller;

import com.group21.chinasoft_project_barbie_backend.Result.Result;
import com.group21.chinasoft_project_barbie_backend.context.BaseContext;
import com.group21.chinasoft_project_barbie_backend.dto.AdmissionMonthDTO;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentAgeDTO;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentInfoDTO;
import com.group21.chinasoft_project_barbie_backend.dto.StaffEvaluateDTO;
import com.group21.chinasoft_project_barbie_backend.entity.HealthInfo;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import com.group21.chinasoft_project_barbie_backend.mapper.ResidentMapper;
import com.group21.chinasoft_project_barbie_backend.service.ResidentService;
import com.group21.chinasoft_project_barbie_backend.vo.ExceptionInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/resident")
public class ResidentController {
    @Autowired
    ResidentService residentService;

    @Autowired
    ResidentMapper residentMapper;

    /**
     * 获取基本信息：老人年龄，姓名，家属性名
     * @return
     */
    @GetMapping("/info")
    public Result getResidentInfo(){
        ResidentInfoDTO residentInfoDTO = residentService.getResidentInfo();
        return Result.success(residentInfoDTO);
    }

    /**
     * 获取所有历史信息
     * @return
     */
    @GetMapping("/exception")
    public Result getAllSigns(){
        List<ExceptionInfoVo> list = residentService.getAllExceptions();
        return Result.success(list);
    }

    /**
     * 获取当前体征信息
     * @return
     */
    @GetMapping("/nowinfo")
    public Result getNowInfo(){
        System.out.println(BaseContext.getCurrentId());
        HealthInfo healthInfo = residentService.getNowInfo();
        return Result.success(healthInfo);
    }

    /**
     * 取消报警接口
     * @param residentId
     * @return
     */
    @GetMapping("/cancel/{id}")
    public Result cancelException(@PathVariable("id") int residentId){
        residentService.cancelException(residentId);
        return Result.success();
    }

    //todo
    @GetMapping("/getAllEvaluation")
    public Result getEvaluation(){
        List<StaffEvaluateDTO> list = residentService.getEvaluation();
        return Result.success(list);
    }

    @GetMapping("/getAllResidentInfo")
    public Result getAllResidentInfo(){
        List<ResidentAgeDTO> list2 = residentService.getAllResidentInfo();
        return Result.success(list2);
    }

    @GetMapping("/getAdmissionMonth")
    public Result getAdmissionMonth(){
        List<AdmissionMonthDTO> list = residentService.getAdmissionMonth();
        return Result.success(list);
    }
}
