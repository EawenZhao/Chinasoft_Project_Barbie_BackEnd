package com.group21.chinasoft_project_barbie_backend.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group21.chinasoft_project_barbie_backend.dto.HardwareDataDTO;
import com.group21.chinasoft_project_barbie_backend.mapper.HardwareInfoMapper;
import com.group21.chinasoft_project_barbie_backend.mapper.ResidentMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Slf4j
@Component
public class MqttAcceptCallBack implements MqttCallbackExtended {

    private static final double HR_MIN = 50.0;
    private static final double HR_MAX = 130.0;
    private static final double BO_MIN = 95.0;
    private static final double BO_MAX = 100.0;
    private static final double BT_MIN = 36.5;
    private static final double BT_MAX = 37.3;
    @Autowired
    private MqttAcceptClient mqttAcceptClient;

    @Autowired
    HardwareInfoMapper hardwareInfoMapper;
    @Autowired
    ResidentMapper residentMapper;

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

   /* {
        "id": "0x05",
            "data": {
        "spo2": "",
                "heart": ""
        }
    }

    {
        "id": "0x04",
            "data": {
        "temperature": "",
        }
    }*/
    /**
     * 客户端收到信息触发
     * @param topic
     * @param mqttMessage
     * @throws Exception
     */

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String message = new String(mqttMessage.getPayload());
        HardwareDataDTO hardwareDataDTO = objectMapper.readValue(message, HardwareDataDTO.class);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        int residentId = residentMapper.getResidentIdByDeviceId(hardwareDataDTO.getDevice_id());

        switch (hardwareDataDTO.getId()){
            case 4:
                double temperature = Double.parseDouble(hardwareDataDTO.getData().getTemperature());
                if (temperature < BT_MIN || temperature > BT_MAX){
                    residentMapper.insertException(residentId,formatter.format(date), "体温异常",null);
                }
                hardwareInfoMapper.insertTemperature(residentId,temperature);
                break;
            case 5:
                double heartRate = Double.parseDouble(hardwareDataDTO.getData().getHeart());
                double bloodOxygen = Double.parseDouble(hardwareDataDTO.getData().getSpo2());
                if (heartRate < HR_MIN || heartRate > HR_MAX){
                    residentMapper.insertException(residentId,formatter.format(date), "心率异常",null);
                }else if (bloodOxygen < BO_MIN || bloodOxygen > BO_MAX){
                    residentMapper.insertException(residentId,formatter.format(date), "血氧异常",null);
                }
                hardwareInfoMapper.insertHeartAndOxygen(residentId,heartRate,bloodOxygen);
                break;
            default:
                break;
        }
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
