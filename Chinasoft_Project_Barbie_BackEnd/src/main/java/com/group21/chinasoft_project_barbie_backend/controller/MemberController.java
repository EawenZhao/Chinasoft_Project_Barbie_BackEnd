package com.group21.chinasoft_project_barbie_backend.controller;

import com.group21.chinasoft_project_barbie_backend.Result.Result;
import com.group21.chinasoft_project_barbie_backend.dto.MemberLoginDTO;
import com.group21.chinasoft_project_barbie_backend.dto.StaffEvaluateDTO;
import com.group21.chinasoft_project_barbie_backend.entity.Member;
import com.group21.chinasoft_project_barbie_backend.properties.JwtProperties;
import com.group21.chinasoft_project_barbie_backend.service.MemberService;
import com.group21.chinasoft_project_barbie_backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    JwtProperties jwtProperties;
    @Autowired
    MemberService memberService;

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

}
