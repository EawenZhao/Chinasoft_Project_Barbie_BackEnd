package com.group21.chinasoft_project_barbie_backend.controller;

import com.group21.chinasoft_project_barbie_backend.Result.Result;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import com.group21.chinasoft_project_barbie_backend.entity.VitalSigns;
import com.group21.chinasoft_project_barbie_backend.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resident")
public class ResidentController {
    @Autowired
    ResidentService residentService;

    @GetMapping("/info/{id}")
    public Result getResidentInfo(@PathVariable("id") int residentId){
        ResidentInfo residentInfo = residentService.getResidentInfo(residentId);
        return Result.success(residentInfo);
    }

}
