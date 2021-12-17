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

    private final OutboundIntegrationConfig.ToTcpGateway gateway;

    public void send(String s) {
        log.debug("Sending request: {}", s);
        String ss = gateway.toTcp(s);
        log.debug("Received response: {}", ss);
    }
}
