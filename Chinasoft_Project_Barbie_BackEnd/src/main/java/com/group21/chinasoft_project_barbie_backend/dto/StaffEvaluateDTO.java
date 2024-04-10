package com.group21.chinasoft_project_barbie_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffEvaluateDTO {
    @JsonProperty("staff_id")
    int staffId;
    @JsonProperty("resident_id")
    int residentId;
    Double star;
    String comment;
}
