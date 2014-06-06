package riak.utils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by miguel on 06/06/14.
 */
public class JacksonAsyncRiakClient extends JacksonSyncRiakClient {

    private ExecutorService executorService;

    public JacksonAsyncRiakClient(String host, int asyncThreadPoolSize) throws IOException {
        super(host);
        executorService = Executors.newFixedThreadPool(asyncThreadPoolSize);
    }

    public <T> void read(final String bucketName, final String key, final Class<T> tClass, final RiakReadListener callback) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(read(bucketName, key, tClass));
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        });
    }

    public static interface RiakReadListener<T> {
        void onSuccess(T data);

        void onError(Exception e);
    }
}
