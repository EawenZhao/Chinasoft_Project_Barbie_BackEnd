package com.group21.chinasoft_project_barbie_backend.config;

import com.group21.chinasoft_project_barbie_backend.mqtt.MqttAcceptClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    @Autowired
    private MqttAcceptClient mqttAcceptClient;

    @Conditional(MqttCondition.class)
    @Bean
    public MqttAcceptClient getMqttAcceptClient(){
        mqttAcceptClient.connect();
//        mqttAcceptClient.subscribe("env_pub", 10);
        return mqttAcceptClient;
    }

}

