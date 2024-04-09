package com.group21.chinasoft_project_barbie_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionInfo {
    Timestamp exceptionStartTime;
    String exceptionInfo;
    Timestamp exceptionEndTime;
}
