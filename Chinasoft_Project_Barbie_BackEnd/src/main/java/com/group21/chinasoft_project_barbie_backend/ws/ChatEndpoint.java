package com.group21.chinasoft_project_barbie_backend.ws;

import com.group21.chinasoft_project_barbie_backend.context.BaseContext;
import com.group21.chinasoft_project_barbie_backend.mapper.MemberMapper;
import com.group21.chinasoft_project_barbie_backend.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
@Component
public class ChatEndpoint {
    @Autowired
    MemberMapper memberMapper;

    private static final Map<String,Session> users = new ConcurrentHashMap<>();

    /**
     * 建立ws连接后被调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        String username = memberMapper.getUsernameById(BaseContext.getCurrentId().intValue());
        //将session保存
        users.put(username,session);
        //将用户广播给对应的医生
        MessageUtils.getMessage(true,null,)
    }

    //每当出现新用户，就将其广播给对应的医生
    public void broadcastMedicalStaff(String message){
        try{
            Set<Map.Entry<String,Session>> entries = users.entrySet();
            for(Map.Entry<String,Session> entry:entries){
                Session session = entry.getValue();
                session.getAsyncRemote().sendText(message);
            }
        }catch (Exception e){
            //
        }
    }

    @OnMessage
    public void onMessage(String message){

    }

    @OnClose
    public void OnClose(Session session){

    }

    public void sendOneMessage(String message){

    }

}
