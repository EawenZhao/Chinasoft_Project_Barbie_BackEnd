package com.group21.chinasoft_project_barbie_backend.config;

import com.group21.chinasoft_project_barbie_backend.utils.ApplicationContextProvider;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpointConfig;

//todo 用于ws 不知道有没有用
@Component
public class WebSocketServerEndpointConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) {
        return ApplicationContextProvider.getBean(clazz);
    }
}
