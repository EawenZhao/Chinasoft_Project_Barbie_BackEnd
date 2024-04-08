package com.group21.chinasoft_project_barbie_backend.controller;

import com.group21.chinasoft_project_barbie_backend.Result.Result;
import com.group21.chinasoft_project_barbie_backend.dto.MemberLoginDTO;
import com.group21.chinasoft_project_barbie_backend.dto.MemberRegisterDTO;
import com.group21.chinasoft_project_barbie_backend.dto.StaffEvaluateDTO;
import com.group21.chinasoft_project_barbie_backend.entity.Member;
import com.group21.chinasoft_project_barbie_backend.exception.RegisterException;
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

    @PostMapping("/register")
    public Result register(@RequestBody MemberRegisterDTO memberRegisterDTO) {
        try {
            // 调用Service层的注册方法
            int affectedRows = memberService.register(
                    memberRegisterDTO.getUsername(),
                    memberRegisterDTO.getPassword(),
                    memberRegisterDTO.getResidentId(),
                    memberRegisterDTO.getPhone(),
                    memberRegisterDTO.getEmail());

            // 根据affectedRows判断注册是否成功
            if(affectedRows > 0) {
                // 如果注册成功，返回成功响应
                return Result.success();
            } else {
                // 如果没有影响任何行，认为是注册失败
                return Result.error("注册失败");
            }
        } catch (RegisterException e) {
            // 捕获注册过程中可能出现的异常，并返回错误信息
            return Result.error(e.getMessage());
        } catch (Exception e) {
            // 对于其他未知异常，返回通用错误信息
            return Result.error("服务器内部错误，请稍后再试");
        }
    }

    @PostMapping("evaluate")
    public Result evaluate(@RequestBody StaffEvaluateDTO staffEvaluateDTO){
        memberService.evaluate(staffEvaluateDTO);
        return Result.success();
    }

}
