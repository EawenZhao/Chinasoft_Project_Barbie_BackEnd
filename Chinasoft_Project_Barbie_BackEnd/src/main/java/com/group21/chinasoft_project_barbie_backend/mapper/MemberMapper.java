package com.group21.chinasoft_project_barbie_backend.mapper;

import com.group21.chinasoft_project_barbie_backend.entity.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {
    @Select("select * from family_members_mobile where username = #{username}")
    Member login(String username);

    @Insert("insert into staff_evaluation values (#{staffId},#{residentId},#{star},#{comment})")
    void evaluateStaff(int staffId, int residentId, Double star, String comment);

    @Select("select username from family_members_mobile where member_id = #{memberId}")
    String getUsernameById(int memberId);
}
