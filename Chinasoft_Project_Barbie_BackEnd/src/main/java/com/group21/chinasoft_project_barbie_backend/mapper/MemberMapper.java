package com.group21.chinasoft_project_barbie_backend.mapper;

import com.group21.chinasoft_project_barbie_backend.dto.MemberRegisterDTO;
import com.group21.chinasoft_project_barbie_backend.entity.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {
    @Select("select * from family_members_mobile where username = #{username}")
    Member getMemberByUsername(String username);

    @Insert("INSERT INTO family_members_mobile(username, password, resident_id, phone, email) VALUES(#{username}, #{password}, #{residentId}, #{phone}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "memberId")
    int register(MemberRegisterDTO memberRegisterDTO);

    @Insert("insert into staff_evaluation values (#{staffId},#{residentId},#{star},#{comment})")
    void evaluateStaff(int staffId, int residentId, Double star, String comment);

    @Select("select username from family_members_mobile where member_id = #{memberId}")
    String getUsernameById(int memberId);
}
