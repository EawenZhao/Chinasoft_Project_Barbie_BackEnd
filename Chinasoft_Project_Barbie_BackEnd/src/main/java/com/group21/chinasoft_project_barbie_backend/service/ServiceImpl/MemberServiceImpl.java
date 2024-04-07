package com.group21.chinasoft_project_barbie_backend.service.ServiceImpl;

import com.group21.chinasoft_project_barbie_backend.entity.Member;
import com.group21.chinasoft_project_barbie_backend.exception.LoginFailException;
import com.group21.chinasoft_project_barbie_backend.mapper.MemberMapper;
import com.group21.chinasoft_project_barbie_backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberMapper memberMapper;
    @Override
    public Member login(String username, String password){
        Member member  = memberMapper.login(username);
        String passwd = member.getPassword();
        if(member == null){
            throw new LoginFailException("用户不存在");
        } else if (!passwd.equals(password)) {
            throw new LoginFailException("登陆失败");
        }
        return member;
    }
}
