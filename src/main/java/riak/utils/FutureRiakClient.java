package riak.utils;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by miguel on 06/06/14.
 */
public class FutureRiakClient extends SyncRiakClient {

    private ExecutorService executorService;

    public FutureRiakClient(String host, int asyncThreadPoolSize) throws IOException {
        super(host);
        executorService = Executors.newFixedThreadPool(asyncThreadPoolSize);
    }

    public Future<String> readFuture(final String bucketName, final String key) {
        return executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return read(bucketName, key);

            }
        });
    }

    @Override
    public void close(){
        super.close();
        executorService.shutdown();
    }
}
