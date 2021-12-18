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
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@RequiredArgsConstructor
public class OutboundIntegrationConfig {

    @MessagingGateway(defaultRequestChannel = "toTcp")
    public interface ToTcpGateway {
        String send(@Payload String request, @Header("host") String host, @Header("port") int port);
    }

    @Bean
    @ServiceActivator(inputChannel = "toTcp")
    public MessageHandler tcpOutGate() {
        TcpOutboundGateway gate = new TcpOutboundGateway(){
            @Override
            public void handleMessage(Message<?> message) {
                // Динамическое создание connection factory на хост:порт Peer'а
                String host = (String)message.getHeaders().get("host");
                Integer port = (Integer)message.getHeaders().get("port");
                TcpNetClientConnectionFactory cf = new TcpNetClientConnectionFactory(host, port);
                cf.start();
                this.setConnectionFactory(cf);

                try {
                    // Обработка сообщения
                    super.handleMessage(message);
                } finally {
                    // Освобождение ресурсов
                    cf.stop();
                }
            }
        };
        gate.setConnectionFactory(new TcpNetClientConnectionFactory("aaa", 11111));// 11111 - неиспользуемый порт, нужен только для первоначальной инициализации
        gate.setOutputChannelName("peerResponse");
        return gate;
    }

    @Transformer(inputChannel = "peerResponse")
    public String convertResult(byte[] bytes) {
        return new String(bytes);
    }
}
