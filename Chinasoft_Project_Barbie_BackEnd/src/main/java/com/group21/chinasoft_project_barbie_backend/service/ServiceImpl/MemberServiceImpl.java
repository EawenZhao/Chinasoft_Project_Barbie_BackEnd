package com.group21.chinasoft_project_barbie_backend.service.ServiceImpl;

import com.group21.chinasoft_project_barbie_backend.entity.Member;
import com.group21.chinasoft_project_barbie_backend.exception.LoginFailException;
import com.group21.chinasoft_project_barbie_backend.exception.RegisterException;
import com.group21.chinasoft_project_barbie_backend.mapper.MemberMapper;
import com.group21.chinasoft_project_barbie_backend.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    //private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
    @Autowired
    MemberMapper memberMapper;
    @Override
    public Member login(String username, String password){
        Member member  = memberMapper.getMemberByUsername(username);
        if(member == null){
            throw new LoginFailException("用户不存在");
        }

        if (!member.getPassword().equals(password)) {
            throw new LoginFailException("密码错误");
        }
        return member;
    }

    @Override
    public int register(String username, String password, int residentId, String phone, String email) {
        //这里可能有一些其他的注册验证逻辑...

        try {
            int affectedRows = memberMapper.register(username, password, residentId, phone, email);
            if(affectedRows > 0){
                // 如果注册成功
                return affectedRows;
            }
        } catch (Exception e) {
            throw new RegisterException("注册失败，请检查服务器下面日志" + e.getMessage());
        }

        // 如果插入未成功或其他原因导致未能返回用户，抛出异常
        throw new RegisterException("注册失败");
    }
}
