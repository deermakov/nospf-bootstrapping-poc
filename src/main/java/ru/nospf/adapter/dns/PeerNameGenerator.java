package ru.nospf.adapter.dns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.text.MessageFormat;

/**
 * todo Document type PeerNameGenerator
 */
@Slf4j
public class PeerNameGenerator {

    private static final String DNS_NODE_NAME_PATTERN = "{0}_{1,number,#}_{2,number,#}";

    public static String getPeerName(String networkId, Integer nodeBucket, Integer port) {
        String plainText = MessageFormat.format(DNS_NODE_NAME_PATTERN, networkId, nodeBucket, port);
        log.debug("plainText = {}", plainText);
        return DigestUtils.md5DigestAsHex(plainText.getBytes());
    }
}
