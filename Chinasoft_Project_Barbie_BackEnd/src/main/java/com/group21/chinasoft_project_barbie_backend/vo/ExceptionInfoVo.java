package com.group21.chinasoft_project_barbie_backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionInfoVo {
    String  exceptionStartTime;
    String exceptionInfo;
    String exceptionEndTime;
    Boolean isCurrent;
}
