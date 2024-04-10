package com.group21.chinasoft_project_barbie_backend.controller;

import com.group21.chinasoft_project_barbie_backend.Result.Result;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentInfoDTO;
import com.group21.chinasoft_project_barbie_backend.entity.HealthInfo;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import com.group21.chinasoft_project_barbie_backend.service.ResidentService;
import com.group21.chinasoft_project_barbie_backend.vo.ExceptionInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resident")
public class ResidentController {
    @Autowired
    ResidentService residentService;

    /**
     * 获取基本信息：老人年龄，姓名，家属性名
     * @param residentId
     * @return
     */
    @GetMapping("/info/{id}")
    public Result getResidentInfo(@PathVariable("id") int residentId){
        ResidentInfoDTO residentInfoDTO = residentService.getResidentInfo(residentId);
        return Result.success(residentInfoDTO);
    }

    /**
     * 获取所有历史信息
     * @param residentId
     * @return
     */
    @GetMapping("/exception/{id}")
    public Result getAllSigns(@PathVariable("id") int residentId){
        List<ExceptionInfoVo> list = residentService.getAllExceptions(residentId);
        return Result.success(list);
    }

    /**
     * 获取当前体征信息
     * @param residentId
     * @return
     */
    @GetMapping("/nowinfo/{id}")
    public Result getNowInfo(@PathVariable("id") int residentId){
        HealthInfo healthInfo = residentService.getNowInfo(residentId);
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
}
