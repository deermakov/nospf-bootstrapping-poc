package ru.nospf.adapter.tcp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nospf.fw.intconfig.OutboundIntegrationConfig;

/**
 * todo Document type OutboundAdapter
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OutboundAdapter {

    public final OutboundIntegrationConfig.ToTcpGateway gateway;

    public void send(String request) {
        log.debug("Sending request: {}", request);
        String response = gateway.send(request);
        log.debug("Received response: {}", response);
    }
}
