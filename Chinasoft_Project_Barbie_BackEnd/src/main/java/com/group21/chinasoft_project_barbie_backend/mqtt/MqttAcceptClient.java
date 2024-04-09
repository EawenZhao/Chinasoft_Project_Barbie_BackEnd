package com.group21.chinasoft_project_barbie_backend.mqtt;

import com.group21.chinasoft_project_barbie_backend.properties.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqttAcceptClient {
    @Autowired
    private MqttAcceptCallBack mqttAcceptCallBack;

    @Autowired
    private MqttProperties mqttProperties;

    private static MqttClient mqttClient;

    public static MqttClient getMqttClient(){
        return mqttClient;
    }

    public static void setMqttClient(MqttClient mqttClient){
        MqttAcceptClient.mqttClient = mqttClient;
    }

    /**
     * 客户端连接
     */
    public void connect(){

        MqttClient client;

        try{

            client = new MqttClient(mqttProperties.getHostUrl(), mqttProperties.getClientId(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());
            options.setConnectionTimeout(mqttProperties.getTimeout());
            options.setKeepAliveInterval(mqttProperties.getKeepAlive());
            options.setAutomaticReconnect(mqttProperties.getReconnect());
            options.setCleanSession(mqttProperties.getCleanSession());
            MqttAcceptClient.setMqttClient(client);

            try{
                client.setCallback(mqttAcceptCallBack);
                client.connect(options);
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 重连接
     */
    public void reconnection(){
        try{
            mqttClient.connect();
        }catch (MqttException ex){
            ex.printStackTrace();
        }
    }

    /**
     * 订阅某个主题
     *
     * @param topic 主题
     * @param qos 连接方式
     */
    public void subscribe(String topic, int qos){
        log.info("-----subscribe topic-----" + topic);
        try{
            mqttClient.subscribe(topic, qos);
        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }

    /**
     * 取消订阅
     */
    public void unsubscribe(String topic){
        log.info("-----unsubscribe topic-----" + topic);
        try{
            mqttClient.unsubscribe(topic);
        }catch(MqttException ex){
            ex.printStackTrace();
        }
    }


}
