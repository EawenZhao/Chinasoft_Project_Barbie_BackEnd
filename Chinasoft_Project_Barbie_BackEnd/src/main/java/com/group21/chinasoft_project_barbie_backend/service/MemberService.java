package com.group21.chinasoft_project_barbie_backend.service;

import com.group21.chinasoft_project_barbie_backend.entity.Member;

public interface MemberService {
    Member login(String userName, String password);


    int register(String username, String password, int residentId, String phone, String email);
}
