package riak.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by miguel on 06/06/14.
 */
public class JsonNodeSyncRiakClient  extends SyncRiakClient {

    private ObjectMapper objectMapper;

    public JsonNodeSyncRiakClient(String host) throws IOException {
        super(host);
        objectMapper = new ObjectMapper();
    }

    public JsonNode readJsonNode(String bucketName, String key) throws IOException {
        return objectMapper.readTree(super.read(bucketName, key));
    }

}