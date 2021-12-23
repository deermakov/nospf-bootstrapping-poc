package ru.nospf.app.messageprocessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nospf.fw.appconfig.ApplicationConfig;

/**
 * todo Document type HandshakeMessageProcessor
 */
@Component("HANDSHAKE")
@RequiredArgsConstructor
public class HandshakeMessageProcessor implements MessageProcessor {
    private final ApplicationConfig applicationConfig;

    @Override
    public String process(String messagePayload) {
        return applicationConfig.getNodeInfo().getPublicKey();
    }
}
