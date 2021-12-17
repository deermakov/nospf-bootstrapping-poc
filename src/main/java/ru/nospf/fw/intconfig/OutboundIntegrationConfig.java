package ru.nospf.fw.intconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.messaging.MessageHandler;

@Configuration
@RequiredArgsConstructor
public class OutboundIntegrationConfig {

    private final String ip = "localhost";
    private final int port = 56789;

    @MessagingGateway(defaultRequestChannel = "toTcp")
    public interface ToTcpGateway {
        String send(String request);
    }

    @Lookup
    public AbstractClientConnectionFactory lookupConnectionFactory() {
        return null;
    }

    @Bean
    @ServiceActivator(inputChannel = "toTcp")
    public MessageHandler tcpOutGate() {
        TcpOutboundGateway gate = new TcpOutboundGateway();
        gate.setConnectionFactory(lookupConnectionFactory());
        gate.setOutputChannelName("peerResponse");
        return gate;
    }

    @Transformer(inputChannel = "peerResponse")
    public String convertResult(byte[] bytes) {
        return new String(bytes);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public AbstractClientConnectionFactory clientCF() {
        return new TcpNetClientConnectionFactory(this.ip, this.port);
    }
}
