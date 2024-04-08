package com.group21.chinasoft_project_barbie_backend.dto;

import lombok.Data;


@Data
public class MemberRegisterDTO {
    private String username;
    private String password;
    private int residentId;
//    private String relation;
    private String phone;
    private String email;
}
