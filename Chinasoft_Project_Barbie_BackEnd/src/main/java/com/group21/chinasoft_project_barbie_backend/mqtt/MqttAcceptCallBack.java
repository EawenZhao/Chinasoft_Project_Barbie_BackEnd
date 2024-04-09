package com.group21.chinasoft_project_barbie_backend.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group21.chinasoft_project_barbie_backend.dto.HardwareData1DTO;
import com.group21.chinasoft_project_barbie_backend.dto.HardwareDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class MqttAcceptCallBack implements MqttCallbackExtended {
    @Autowired
    private MqttAcceptClient mqttAcceptClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
    * 连接emqx 服务器后触发
    *
    * @param b
    * @param s
    */
   @Override
   public void connectComplete(boolean b, String s) {
       System.out.println("s: " + s);
       log.info("[mqtt consumer] connected");
       mqttAcceptClient.subscribe("brec_pub",0);
   }

   /**
    * 客户端断开后触发
    * @param throwable
    */
   @Override
   public void connectionLost(Throwable throwable) {
       log.info("[mqtt-consumer] connect lost, reconnect");
       if (MqttAcceptClient.getMqttClient() == null || !MqttAcceptClient.getMqttClient().isConnected()){
           log.info("[mqtt-consumer]: mqtt reconnect");
            mqttAcceptClient.reconnection();
        }
    }

    /**
     * 客户端收到信息触发
     * @param topic
     * @param mqttMessage
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("[mqtt-consumer] receive topic: " + topic);
        log.info("[mqtt-consumer] receive Qos: " + mqttMessage.getQos());
        String message = new String(mqttMessage.getPayload());
        log.info("[mqtt-consumer] receive message: " + message);
        HardwareData1DTO hardwareData1DTO = objectMapper.readValue(message,HardwareData1DTO.class);
        System.out.println(hardwareData1DTO + hardwareData1DTO.getId() +" "+ hardwareData1DTO.getData().getSpo2() +" "+ hardwareData1DTO.getData().getHeart());

    }

    /**
     * 发布消息成功
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        String[] topics = iMqttDeliveryToken.getTopics();
        for (String topic : topics){
            log.info("mqtt-consumer: " + topic + "send messgage succeed");
        }try{
            MqttMessage message = iMqttDeliveryToken.getMessage();
            byte[] payload = message.getPayload();
            String s = new String(payload, StandardCharsets.UTF_8);
            log.info("mqtt-consumer message: " + s);
        }catch (MqttException ex){
            ex.printStackTrace();
        }
    }
}
