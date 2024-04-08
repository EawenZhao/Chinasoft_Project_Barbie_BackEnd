package com.group21.chinasoft_project_barbie_backend.utils;

import com.alibaba.fastjson.JSON;
import com.group21.chinasoft_project_barbie_backend.Result.ResultMessage;

public class MessageUtils {
    public static String getMessage(Boolean isSystemMessage,String fromName,Object message){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setMessage(message);
        resultMessage.setIsSystemMessage(isSystemMessage);
        if(fromName!=null){
            resultMessage.setFromName(fromName);
        }
        return JSON.toJSONString(resultMessage);
    }
}
