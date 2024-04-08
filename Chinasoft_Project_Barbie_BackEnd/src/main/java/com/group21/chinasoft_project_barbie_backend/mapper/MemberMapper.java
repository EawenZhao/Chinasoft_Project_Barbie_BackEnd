package com.group21.chinasoft_project_barbie_backend.mapper;

import com.group21.chinasoft_project_barbie_backend.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {
    @Select("select * from family_members_mobile where username = #{username}")
    Member getMemberByUsername(String username);

    @Insert("INSERT INTO family_members_mobile(username, password, resident_id, phone, email) VALUES(#{username}, #{password}, #{residentId}, #{phone}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "memberId")
    int register(String username, String password, int residentId, String phone, String email);
}
