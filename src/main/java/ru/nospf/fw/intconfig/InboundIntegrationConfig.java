package ru.nospf.fw.intconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.MessageConvertingTcpMessageMapper;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.MapJsonSerializer;
import ru.nospf.fw.appconfig.ApplicationConfig;

@Configuration
@RequiredArgsConstructor
public class InboundIntegrationConfig {

    private final ApplicationConfig applicationConfig;

    @Bean
    public TcpInboundGateway tcpInGate(AbstractServerConnectionFactory serverCF) {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(serverCF);
        inGate.setRequestChannelName("input");
        return inGate;
    }

    @Bean
    public AbstractServerConnectionFactory serverCF(MapJsonSerializer mapJsonSerializer,
                                                    MessageConvertingTcpMessageMapper messageConvertingTcpMessageMapper) {
        AbstractServerConnectionFactory result = new TcpNetServerConnectionFactory(applicationConfig.getNodeInfo().getPort());
        result.setSerializer(mapJsonSerializer);
        result.setDeserializer(mapJsonSerializer);
        result.setMapper(messageConvertingTcpMessageMapper);
        return result;
    }
}
