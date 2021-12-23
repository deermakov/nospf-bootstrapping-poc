package ru.nospf.fw.intconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.MessageConvertingTcpMessageMapper;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.MapJsonSerializer;
import org.springframework.integration.support.converter.MapMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import ru.nospf.adapter.tcp.MessageType;

@Configuration
@RequiredArgsConstructor
public class OutboundIntegrationConfig {

    @MessagingGateway(defaultRequestChannel = "toTcp")
    public interface ToTcpGateway {
        String send(@Payload String request,
            @Header("messageType") MessageType messageType,
            @Header("host") String host,
            @Header("port") int port);
    }

    @Bean
    @ServiceActivator(inputChannel = "toTcp")
    public MessageHandler tcpOutGate(MapJsonSerializer mapJsonSerializer,
        MessageConvertingTcpMessageMapper messageConvertingTcpMessageMapper) {
        TcpOutboundGateway gate = new TcpOutboundGateway() {
            @Override
            public void handleMessage(Message<?> message) {
                // Динамическое создание connection factory на хост:порт Peer'а
                String host = (String) message.getHeaders().get("host");
                Integer port = (Integer) message.getHeaders().get("port");
                TcpNetClientConnectionFactory cf = new TcpNetClientConnectionFactory(host, port);
                cf.setSerializer(mapJsonSerializer);
                cf.setDeserializer(mapJsonSerializer);
                cf.setMapper(messageConvertingTcpMessageMapper);
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
        gate.setConnectionFactory(
            new TcpNetClientConnectionFactory("aaa", 11111));// aaa:11111 - неиспользуемый адрес, нужен только для первоначальной инициализации
        return gate;
    }

    @Bean
    public MessageConvertingTcpMessageMapper messageConvertingTcpMessageMapper(MapMessageConverter mapMessageConverter) {
        return new MessageConvertingTcpMessageMapper(mapMessageConverter);
    }

    @Bean
    public MapMessageConverter mapMessageConverter() {
        MapMessageConverter result = new MapMessageConverter();
        // наши custom headers, которые будем передавать получателю вместе с телом сообщения
        result.setHeaderNames("messageType");
        return result;
    }

    // JSON serializer/deserialiser, используемый в передаче метаданных между пирами,
    //  т.е. весь такой обмен идем в JSON-формате
    @Bean
    public MapJsonSerializer mapJsonSerializer() {
        return new MapJsonSerializer();
    }
}
