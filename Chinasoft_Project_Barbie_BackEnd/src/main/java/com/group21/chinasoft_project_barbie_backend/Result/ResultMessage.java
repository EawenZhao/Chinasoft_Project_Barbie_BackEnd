package com.group21.chinasoft_project_barbie_backend.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMessage {
    Boolean isSystemMessage;
    String fromName;
    Object message;
}
