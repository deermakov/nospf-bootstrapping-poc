package ru.nospf.domain;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import lombok.Builder;
import lombok.Data;

/**
 * todo Document type Peer
 */
@Data
@Builder
@Document(collection = "peers", schemaVersion = "1.0")
public class Peer {

    @Id
    private String publicKey;

    private String ip;
    private Integer port;
    private boolean malicious;
}
