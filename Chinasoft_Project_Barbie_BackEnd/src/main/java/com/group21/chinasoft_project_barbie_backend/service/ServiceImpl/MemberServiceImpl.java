package com.group21.chinasoft_project_barbie_backend.service.ServiceImpl;

import com.group21.chinasoft_project_barbie_backend.context.BaseContext;
import com.group21.chinasoft_project_barbie_backend.dto.MemberRegisterDTO;
import com.group21.chinasoft_project_barbie_backend.dto.StaffEvaluateDTO;
import com.group21.chinasoft_project_barbie_backend.entity.Member;
import com.group21.chinasoft_project_barbie_backend.exception.LoginFailException;
import com.group21.chinasoft_project_barbie_backend.exception.RegisterException;
import com.group21.chinasoft_project_barbie_backend.mapper.MemberMapper;
import com.group21.chinasoft_project_barbie_backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberMapper memberMapper;

    @Override
    public Member login(String phone, String password) {
        Member member = memberMapper.getMemberByPhone(phone);
        if (member == null) {
            throw new LoginFailException("用户不存在");
        }

        if (!member.getPassword().equals(password)) {
            throw new LoginFailException("密码错误");
        }
        return member;
    }

    @Override
    public int register(MemberRegisterDTO memberRegisterDTO) {
        //这里可能有一些其他的注册验证逻辑...

        try {
            int affectedRows = memberMapper.register(memberRegisterDTO);
            if (affectedRows > 0) {
                // 如果注册成功
                return affectedRows;
            }
        } catch (Exception e) {
            throw new RegisterException("注册失败，请检查服务器下面日志     " + e.getMessage());
        }

        // 如果插入未成功或其他原因导致未能返回用户，抛出异常
        throw new RegisterException("注册失败");
    }

    @Override
    public void evaluate(StaffEvaluateDTO StaffEvaluateDTO) {
        int residentId = BaseContext.getCurrentId().intValue();
        int staffId = memberMapper.getStaffIdByResidentId(residentId);
        memberMapper.evaluateStaff(staffId, residentId, StaffEvaluateDTO.getStar(), StaffEvaluateDTO.getComment());
    }
}
