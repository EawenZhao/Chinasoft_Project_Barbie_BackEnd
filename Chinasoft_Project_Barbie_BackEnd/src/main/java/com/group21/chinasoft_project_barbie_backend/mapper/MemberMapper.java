package com.group21.chinasoft_project_barbie_backend.mapper;

import com.group21.chinasoft_project_barbie_backend.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    @Select("select * from family_members_mobile where username = #{username}")
    Member login(String username);
}
