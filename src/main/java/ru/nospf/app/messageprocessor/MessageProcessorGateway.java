package ru.nospf.app.messageprocessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nospf.domain.MessageType;

import java.util.Map;
import java.util.Optional;

/**
 * todo Document type MessageProcessor
 */
@Component
@RequiredArgsConstructor
public class MessageProcessorGateway {
    private final Map<String, MessageProcessor> messageProcessors;

    public String process(MessageType messageType, String messagePayload) {
        MessageProcessor messageProcessor = messageProcessors.get(messageType.toString());
        return Optional.ofNullable(messageProcessor).map(messageProcessor1 -> messageProcessor1.process(messagePayload)).orElse(null);
    }
}
