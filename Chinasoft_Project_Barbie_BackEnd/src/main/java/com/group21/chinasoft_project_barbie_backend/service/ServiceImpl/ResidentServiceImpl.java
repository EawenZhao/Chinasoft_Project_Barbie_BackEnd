package com.group21.chinasoft_project_barbie_backend.service.ServiceImpl;

import com.group21.chinasoft_project_barbie_backend.dto.ResidentInfoDTO;
import com.group21.chinasoft_project_barbie_backend.entity.ExceptionInfo;
import com.group21.chinasoft_project_barbie_backend.entity.ResidentInfo;
import com.group21.chinasoft_project_barbie_backend.exception.GetInfoException;
import com.group21.chinasoft_project_barbie_backend.mapper.ResidentMapper;
import com.group21.chinasoft_project_barbie_backend.service.ResidentService;
import com.group21.chinasoft_project_barbie_backend.vo.ExceptionInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    ResidentMapper residentMapper;
    @Override
    public ResidentInfoDTO getResidentInfo(int residentId) {

        ResidentInfo residentInfo = residentMapper.findResidentInfoById(residentId);
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
    public List<ExceptionInfoVo> getAllExceptions(int residentId) {
        List<ExceptionInfo> list = residentMapper.findAllExceptions(residentId);
        List<ExceptionInfoVo> list2 = new ArrayList<>();
        //将list中的time序列化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ExceptionInfo exceptionInfo : list) {
            list2.add(new ExceptionInfoVo(formatter.format(exceptionInfo.getExceptionStartTime()),exceptionInfo.getExceptionInfo(), formatter.format(exceptionInfo.getExceptionEndTime())));
        }
        return list2;
    }

}
