package riak.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by miguel on 06/06/14.
 */
public class JacksonSyncRiakClient extends SyncRiakClient {

    private ObjectMapper objectMapper;

    public JacksonSyncRiakClient(String host) throws IOException {
        super(host);
        objectMapper = new ObjectMapper();
    }

    public <T> T read(String bucketName, String key, Class<T> tClass) throws IOException {
        return objectMapper.readValue(super.read(bucketName, key), tClass);
    }

}
