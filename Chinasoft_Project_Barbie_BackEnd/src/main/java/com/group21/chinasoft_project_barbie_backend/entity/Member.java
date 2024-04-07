package com.group21.chinasoft_project_barbie_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    int MemberId;
    int ResidentId;
    String username;
    String password;
    String relation;
    String phone;
    String email;
    Timestamp createTime;
}
