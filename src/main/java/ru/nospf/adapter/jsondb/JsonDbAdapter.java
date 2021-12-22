package ru.nospf.adapter.jsondb;

import io.jsondb.JsonDBTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nospf.app.Database;
import ru.nospf.domain.Peer;

/**
 * todo Document type JsonDbAdapter
 */
@Component
@RequiredArgsConstructor
public class JsonDbAdapter implements Database {
    private final JsonDBTemplate jsonDBTemplate;

    @Override
    public void save(Peer peer){
        if (!jsonDBTemplate.collectionExists(Peer.class)){
            jsonDBTemplate.createCollection(Peer.class);
        }

        if (jsonDBTemplate.findById(peer.getIp(), Peer.class) != null){
            jsonDBTemplate.save(peer, Peer.class);
        } else {
            jsonDBTemplate.insert(peer);
        }


    }
}
