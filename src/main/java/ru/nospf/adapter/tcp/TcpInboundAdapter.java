package ru.nospf.adapter.tcp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import ru.nospf.app.messageprocessor.MessageProcessorGateway;
import ru.nospf.domain.MessageType;

/**
 * todo Document type InboundAdapter
 */
@MessageEndpoint
@RequiredArgsConstructor
@Slf4j
public class TcpInboundAdapter {

    private final MessageProcessorGateway messageProcessor;

    @ServiceActivator(inputChannel = "input")
    public String receive(Message<String> request) {
        MessageType messageType = MessageType.valueOf(request.getHeaders().getOrDefault("messageType", MessageType.UNKNOWN).toString());
        String messagePayload = request.getPayload();
        log.debug("Received request: messageType = {}, payload = {}", messageType, messagePayload);
        String response = messageProcessor.process(messageType, messagePayload);
        log.debug("Sending response: {}", response);
        return response; // as we're "gateway", we have to return anything to sender
    }
}

