package com.group21.chinasoft_project_barbie_backend.controller;

import com.group21.chinasoft_project_barbie_backend.dto.HardwareDataDTO;
import com.group21.chinasoft_project_barbie_backend.dto.ResidentInfoToFrontDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resident/info/status")
public class DataExchangeController {

    private HardwareDataDTO hardwareData;
    private ResidentInfoToFrontDTO residentInfo;


//    // 接收硬件发送的JSON数据并转换格式的方法
//    @PostMapping("/")
//    public String receiveAndTransformData(@RequestBody HardwareDataDTO hardwareData) {
//        ResidentInfo residentInfo = transformHardwareDataToResidentInfo(hardwareData);
//        residentDataMap.put(residentInfo.getId(), residentInfo);
//        return "Data received and converted successfully.";
//    }


    @GetMapping("/{id}")
    public ResidentInfoToFrontDTO provideTransformedDataToFrontEnd(@PathVariable String id) {
        this.hardwareData = new HardwareDataDTO(); //

        hardwareData.setId("0x60");   //
        hardwareData.setData(new HashMap<>());   //
        hardwareData.getData().put("xinlv", 80.0);  //
        hardwareData.getData().put("xueya", 115.0);   //

        //这里差一个从数据库中调取最新data 的方法及过程
        residentInfo = transformHardwareDataToResidentInfo(this.hardwareData);

        if (residentInfo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resident data not found for ID: " + id);
        }
        return residentInfo;
    }

    // 转换数据的内部逻辑
    private ResidentInfoToFrontDTO transformHardwareDataToResidentInfo(HardwareDataDTO hardwareData) {
        ResidentInfoToFrontDTO residentInfo = new ResidentInfoToFrontDTO();
        residentInfo.setCode(1); // 假设转换总是成功
        residentInfo.setData(new ArrayList<>());


        Map<String, Double> hardwareMap = hardwareData.getData();

        for (Map.Entry<String, Double> stringDoubleEntry : hardwareMap.entrySet()) {
            ResidentInfoToFrontDTO.DataItem item = new ResidentInfoToFrontDTO.DataItem
                    (stringDoubleEntry.getKey(), stringDoubleEntry.getValue());
            residentInfo.getData().add(item);
        }

        return residentInfo;
    }
}
