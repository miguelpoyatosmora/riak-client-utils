package riak.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by miguel on 06/06/14.
 */
public class JsonNodeAsyncRiakClient  extends JsonNodeSyncRiakClient{
    private ExecutorService executorService;

    public JsonNodeAsyncRiakClient(String host, int asyncThreadPoolSize) throws IOException {
        super(host);
        executorService = Executors.newFixedThreadPool(asyncThreadPoolSize);
    }

    public void read(final String bucketName, final String key, final RiakReadListener<JsonNode> callback) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(readJsonNode(bucketName, key));
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        });
    }

    public static interface RiakReadListener<JsonNode> {
        void onSuccess(JsonNode data);

        void onError(Exception e);
    }
}
