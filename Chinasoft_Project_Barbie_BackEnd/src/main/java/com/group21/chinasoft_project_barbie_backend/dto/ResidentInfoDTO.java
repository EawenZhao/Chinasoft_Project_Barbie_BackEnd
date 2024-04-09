package com.group21.chinasoft_project_barbie_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class ResidentInfoDTO {
    String firstName;
    String lastName;
    String username;
    int age;
}
