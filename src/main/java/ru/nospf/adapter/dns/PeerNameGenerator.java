package ru.nospf.adapter.dns;

import org.springframework.util.DigestUtils;

import java.text.MessageFormat;

/**
 * todo Document type PeerNameGenerator
 */
public class PeerNameGenerator {

    private static final String DNS_NODE_NAME_PATTERN = "{0}_{1}_{2}";

    public static String getPeerName(String networkId, Integer nodeBucket, Integer port) {
        String plainText = MessageFormat.format(DNS_NODE_NAME_PATTERN, networkId, nodeBucket, port);
        return DigestUtils.md5DigestAsHex(plainText.getBytes());
    }
}
