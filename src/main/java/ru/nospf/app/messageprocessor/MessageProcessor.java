package ru.nospf.app.messageprocessor;

import ru.nospf.domain.MessageType;

/**
 * todo Document type MessageProcessor
 */
public interface MessageProcessor {
    String process(String messagePayload);
}
