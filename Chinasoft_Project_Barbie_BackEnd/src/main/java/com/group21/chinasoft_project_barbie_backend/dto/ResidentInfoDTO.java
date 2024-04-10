package com.group21.chinasoft_project_barbie_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentInfoDTO {
    String firstName;
    String lastName;
    String username;
    int age;
}
