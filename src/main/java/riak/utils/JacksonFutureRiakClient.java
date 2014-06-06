package riak.utils;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by miguel on 06/06/14.
 */
public class JacksonFutureRiakClient extends JacksonSyncRiakClient {

    private ExecutorService executorService;

    public JacksonFutureRiakClient(String host, int asyncThreadPoolSize) throws IOException {
        super(host);
        executorService = Executors.newFixedThreadPool(asyncThreadPoolSize);
    }

    public <T> Future<T> readFuture(final String bucketName, final String key, final Class<T> tClass) {
        return executorService.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return read(bucketName, key, tClass);

            }
        });
    }

    @Override
    public void close() {
        super.close();
        executorService.shutdown();
    }
}
