package ru.nospf.adapter.tcp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;

import java.text.MessageFormat;

/**
 * todo Document type InboundAdapter
 */
@MessageEndpoint
@Slf4j
public class InboundAdapter {
    @Transformer(inputChannel = "fromTcp", outputChannel = "input")
    public String convert(byte[] bytes) {
        return new String(bytes);
    }

    @ServiceActivator(inputChannel = "input")
    public String receive(String in) {
        log.debug("Received request: {}", in);
        String response = MessageFormat.format("''{0}'' acknowledged", in);
        log.debug("Sending response: {}", response);
        return response; // as we're "gateway", we have to return anything to sender
    }
}

