package com.group21.chinasoft_project_barbie_backend.service;

import com.group21.chinasoft_project_barbie_backend.dto.MemberRegisterDTO;
import com.group21.chinasoft_project_barbie_backend.dto.RetrievePasswordDTO;
import com.group21.chinasoft_project_barbie_backend.dto.StaffEvaluateDTO;
import com.group21.chinasoft_project_barbie_backend.entity.Member;

public interface MemberService {
    Member login(String phone, String password);

    int register(MemberRegisterDTO memberRegisterDTO);

    void evaluate(StaffEvaluateDTO staffEvaluateDTO);

    void retrievePassword(RetrievePasswordDTO retrievePasswordDTO);
}
