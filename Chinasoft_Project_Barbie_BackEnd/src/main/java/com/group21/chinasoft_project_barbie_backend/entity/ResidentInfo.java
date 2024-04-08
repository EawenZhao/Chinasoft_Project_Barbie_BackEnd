package com.group21.chinasoft_project_barbie_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidentInfo {
    String firstName;
    String lastName;
    String username;
    int age;

}
