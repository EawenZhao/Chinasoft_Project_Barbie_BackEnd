package com.group21.chinasoft_project_barbie_backend.config;

import com.group21.chinasoft_project_barbie_backend.utils.ApplicationContextProvider;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpointConfig;

@Component
public class WebSocketServerEndpointConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) {
        return ApplicationContextProvider.getBean(clazz);
    }
}
