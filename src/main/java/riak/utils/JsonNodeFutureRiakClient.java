package riak.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by miguel on 06/06/14.
 */
public class JsonNodeFutureRiakClient extends JsonNodeSyncRiakClient {

    private ExecutorService executorService;

    public JsonNodeFutureRiakClient(String host, int asyncThreadPoolSize) throws IOException {
        super(host);
        executorService = Executors.newFixedThreadPool(asyncThreadPoolSize);
    }

    public Future<JsonNode> readFuture(final String bucketName, final String key) {
        return executorService.submit(new Callable<JsonNode>() {
            @Override
            public JsonNode call() throws Exception {
                return readJsonNode(bucketName, key);
            }
        });
    }

    @Override
    public void close() {
        super.close();
        executorService.shutdown();
    }
}