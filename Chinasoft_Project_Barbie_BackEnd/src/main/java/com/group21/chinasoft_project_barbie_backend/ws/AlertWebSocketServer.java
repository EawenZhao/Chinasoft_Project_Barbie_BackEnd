package com.group21.chinasoft_project_barbie_backend.ws;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group21.chinasoft_project_barbie_backend.config.WebSocketServerEndpointConfigurator;
import com.group21.chinasoft_project_barbie_backend.mapper.ResidentMapper;
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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/alert/{id}",configurator = WebSocketServerEndpointConfigurator.class)
@Component
@Slf4j
public class AlertWebSocketServer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, LocalDateTime> sessionStartTimes = new ConcurrentHashMap<>();
    private static final double HR_MIN = 60.0;
    private static final double HR_MAX = 100.0;
    private static final double BO_MIN = 95.0;
    private static final double BO_MAX = 100.0;
    private static final double BT_MIN = 36.5;
    private static final double BT_MAX = 37.3;

    private Date date1,date2;
    private String alert1;

    @Autowired
    ResidentMapper residentMapper;



    public String checkAndNotify(double heartRate, double bloodOxygen, double bodyTemperature) {
        String alertMessage = null;

        if (heartRate < HR_MIN || heartRate > HR_MAX) {
            alertMessage = "心率异常";
        } else if (bloodOxygen < BO_MIN || bloodOxygen > BO_MAX) {
            alertMessage = "血氧异常";
        } else if (bodyTemperature < BT_MIN || bodyTemperature > BT_MAX) {
            alertMessage = "体温异常";
        }
        return alertMessage;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        session.getUserProperties().put("residentId", id);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        Signs a = objectMapper.readValue(message,Signs.class);
        Map<String,Object> map=new HashMap<>();
        String alert = checkAndNotify(a.getHeartRate(), a.getBloodOxygen(), a.getBodyTemperature());
        if(alert !=null && a.getExceptionMessage()==null) {
            alert1=alert;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date1 = new Date(System.currentTimeMillis());
            sessionStartTimes.put(session.getId(), LocalDateTime.now());
            map.put("alert", alert);
            map.put("date", formatter.format(date1));
            String json = JSON.toJSONString(map);
            sendAlert(session, json);
        } else if (a.getHeartRate()==0 && a.getExceptionMessage().equals("异常结束")) {
            LocalDateTime startTime = sessionStartTimes.get(session.getId());
            if (startTime != null) {
                String residentId = (String) session.getUserProperties().get("residentId");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date2 = new Date(System.currentTimeMillis());
                // 插入数据库
                residentMapper.insertException(Integer.parseInt(residentId),formatter.format(date1), alert1,formatter.format(date2));
                // 清理记录

                map.put("date", formatter.format(date2));
                map.put("exceptionMessage", "异常结束");
                String json = JSON.toJSONString(map);
                sendAlert(session,json);
                sessionStartTimes.remove(session.getId());

            }
        }
    }

    private void sendAlert(Session session, String message) throws Exception {
        session.getBasicRemote().sendText(message);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    // 假设的ChatMessage类
    public static class Signs {
        private double heartRate;
        private double bloodOxygen;
        private double bodyTemperature;
        private String exceptionMessage;
    }
}
