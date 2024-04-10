package com.group21.chinasoft_project_barbie_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterDTO {
    private int memberId;
    private String username;
    private String password;
    private int residentId;
//    private String relation;
    private String phone;
    private String email;
}
