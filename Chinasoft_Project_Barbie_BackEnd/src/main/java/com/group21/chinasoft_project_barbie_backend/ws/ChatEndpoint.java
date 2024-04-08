package com.group21.chinasoft_project_barbie_backend.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group21.chinasoft_project_barbie_backend.context.BaseContext;
import com.group21.chinasoft_project_barbie_backend.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value="/chat/{role}/{id}")
@Slf4j
public class ChatEndpoint {

    // 假设已经定义了医生和用户的Session存储逻辑
     private static final Map<String, Session> doctorSessions = new ConcurrentHashMap<>();
     private static final Map<String, UserSessionInfo> userSessions = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static MemberMapper memberMapper;

    @Autowired
    public void setMemberMapper(MemberMapper mapper) {
        ChatEndpoint.memberMapper = mapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("role") String role) {
        String id = String.valueOf(BaseContext.getCurrentId());
        if ("doctor".equals(role)) {
            // 新医生连接，将其Session保存到doctorSessions映射中
            doctorSessions.put(id, session);
            log.info("Doctor with ID " + id + " has connected.");
        } else if ("user".equals(role)) {
            // 新用户连接，将其Session保存到userSessions映射中
            String doctorId = findDoctorIdForUser(id);
            userSessions.put(id, new UserSessionInfo(session, doctorId));
            log.info("User with ID " + id + " has connected to Doctor with ID " + doctorId);
        }
    }

    private String findDoctorIdForUser(String userId) {
        // 使用memberMapper查找用户对应的医生ID
        return memberMapper.findDoctorIdByUserId(Integer.parseInt(userId));
    }

    /**
     * 前端传输格式：
     * {
     *   "targetUserId": "123",
     *   "message": "Hello, how are you?"
     * }
     * @param message
     * @param role
     * @throws Exception
     */
    @OnMessage
    public void onMessage(String message, @PathParam("role") String role) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
        String senderId = String.valueOf(BaseContext.getCurrentId());

        if ("user".equals(role)) {
            // 用户发送消息给医生
            UserSessionInfo userInfo = userSessions.get(senderId);
            if (userInfo != null) {
                Session doctorSession = doctorSessions.get(userInfo.getDoctorId());
                if (doctorSession != null && doctorSession.isOpen()) {
                    doctorSession.getAsyncRemote().sendText(chatMessage.getMessage());
                }
            }
        } else if ("doctor".equals(role)) {
            // 医生发送消息给指定的用户
            String targetUserId = chatMessage.getTargetUserId(); // 目标用户ID
            UserSessionInfo targetUserInfo = userSessions.get(targetUserId);
            if (targetUserInfo != null) {
                Session userSession = targetUserInfo.getSession();
                if (userSession != null && userSession.isOpen()) {
                    userSession.getAsyncRemote().sendText(chatMessage.getMessage());
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    // 假设的ChatMessage类
    public static class ChatMessage {
        private String targetUserId;
        private String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UserSessionInfo {
        private Session session;
        private String doctorId;
    }
}
