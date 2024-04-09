package com.group21.chinasoft_project_barbie_backend.controller;

import com.group21.chinasoft_project_barbie_backend.dto.HardwareDataDTO;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentInfoToFrontDTO;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resident/info/status")
public class FormatConversionAndDataExchangeController {

    private HardwareDataDTO hardwareData;
    private ResidentInfoToFrontDTO residentInfo;

//    // 接收硬件发送的JSON数据并转换格式的方法
//    @PostMapping("/")
//    public String receiveAndTransformData(@RequestBody HardwareDataDTO hardwareData) {
//        ResidentInfo residentInfo = transformHardwareDataToResidentInfo(hardwareData);
//        residentDataMap.put(residentInfo.getId(), residentInfo);
//        return "Data received and converted successfully.";
//    }


    // 从内存中的Map中提供格式化后的数据给前端的方法
    @GetMapping("/{id}")
    public ResidentInfoToFrontDTO provideTransformedDataToFrontEnd(@PathVariable String id) {

        residentInfo = transformHardwareDataToResidentInfo(this.hardwareData);

        if (residentInfo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resident data not found for ID: " + id);
        }
        return residentInfo;
    }

    // 转换数据的内部逻辑
    private ResidentInfoToFrontDTO transformHardwareDataToResidentInfo(HardwareDataDTO hardwareData) {
        ResidentInfoToFrontDTO residentInfo = new ResidentInfoToFrontDTO();
        residentInfo.setId(hardwareData.getId());
        residentInfo.setCode(1); // 假设转换总是成功

        List<ResidentInfoToFrontDTO.DataItem> dataItems = hardwareData.getData().entrySet().stream()
                .map(entry -> new ResidentInfoToFrontDTO.DataItem(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        residentInfo.setData(dataItems);

        return residentInfo;
    }
}
