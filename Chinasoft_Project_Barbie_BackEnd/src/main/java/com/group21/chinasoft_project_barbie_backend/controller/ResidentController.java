package com.group21.chinasoft_project_barbie_backend.controller;

import com.group21.chinasoft_project_barbie_backend.Result.Result;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentInfoDTO;
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

    @GetMapping("/info/{id}")
    public Result getResidentInfo(@PathVariable("id") int residentId){
        ResidentInfoDTO residentInfoDTO = residentService.getResidentInfo(residentId);
        return Result.success(residentInfoDTO);
    }

    @GetMapping("/exception/{id}")
    public Result getAllSigns(@PathVariable("id") int residentId){
        List<ExceptionInfoVo> list = residentService.getAllExceptions(residentId);
        return Result.success(list);
    }
}
