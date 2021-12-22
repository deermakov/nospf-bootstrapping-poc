package ru.nospf.adapter.tcp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import java.text.MessageFormat;

/**
 * todo Document type InboundAdapter
 */
@MessageEndpoint
@Slf4j
public class InboundAdapter {

    @ServiceActivator(inputChannel = "input")
    public String receive(String request) {
        log.debug("Received request: {}", request);
        String response = MessageFormat.format("''{0}'' acknowledged", request);
        log.debug("Sending response: {}", response);
        return response; // as we're "gateway", we have to return anything to sender
    }
}

