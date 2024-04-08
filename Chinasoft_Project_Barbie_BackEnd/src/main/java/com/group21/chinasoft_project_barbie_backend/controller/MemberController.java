package com.group21.chinasoft_project_barbie_backend.controller;

import com.group21.chinasoft_project_barbie_backend.Result.Result;
import com.group21.chinasoft_project_barbie_backend.context.BaseContext;
import com.group21.chinasoft_project_barbie_backend.dto.MemberLoginDTO;
import com.group21.chinasoft_project_barbie_backend.dto.StaffEvaluateDTO;
import com.group21.chinasoft_project_barbie_backend.entity.Member;
import com.group21.chinasoft_project_barbie_backend.mapper.MemberMapper;
import com.group21.chinasoft_project_barbie_backend.properties.JwtProperties;
import com.group21.chinasoft_project_barbie_backend.service.MemberService;
import com.group21.chinasoft_project_barbie_backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    MemberService memberService;

    @Autowired
    MemberMapper memberMapper;

    @PostMapping("/login")
    public Result login(@RequestBody MemberLoginDTO memberLoginDTO){
        Member member = memberService.login(memberLoginDTO.getUsername(),memberLoginDTO.getPassword());

        //登陆成功生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        return Result.success(token);
    }

    @PostMapping("evaluate")
    public Result evaluate(@RequestBody StaffEvaluateDTO staffEvaluateDTO){
        memberService.evaluate(staffEvaluateDTO);
        return Result.success();
    }

    @GetMapping("/test")
    public String test(){
        return memberMapper.getUsernameById(BaseContext.getCurrentId().intValue());
    }

}
