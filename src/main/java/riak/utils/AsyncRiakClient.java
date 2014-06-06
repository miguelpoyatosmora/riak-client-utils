package riak.utils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by miguel on 06/06/14.
 */
public class AsyncRiakClient extends SyncRiakClient {

    private ExecutorService executorService;

    public AsyncRiakClient(String host, int asyncThreadPoolSize) throws IOException {
        super(host);
        executorService = Executors.newFixedThreadPool(asyncThreadPoolSize);
    }

    public void read(final String bucketName, final String key, final RiakReadListener callback) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(read(bucketName, key));
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        });
    }

    public static interface RiakReadListener {
        void onSuccess(String data);

        void onError(Exception e);
    }
}
