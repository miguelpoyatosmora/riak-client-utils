package riak.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        FutureRiakClient futureRiakClient = new FutureRiakClient("127.0.0.1", 10);
        List<Future<String>> futures = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++) {
            futures.add(futureRiakClient.readFuture("root", "key1"));
        }
        System.out.println(futures.get(1).isDone() ? "done " + futures.get(1).get() : "not done");
        Thread.sleep(3000);
        System.out.println(futures.get(1).get());
        futureRiakClient.close();

//        AsyncRiakClient asyncRiakClient = new AsyncRiakClient("127.0.0.1", 10);
//        syncAsync(asyncRiakClient);
    }

    private static void syncAsync(AsyncRiakClient asyncRiakClient) throws IOException, InterruptedException {
        while (true) {
            asyncRiakClient.read("root", "key1", new AsyncRiakClient.RiakReadListener() {
                @Override
                public void onSuccess(String data) {
                    System.out.println("onSuccess" + data);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("onError" + e.getMessage());
                }
            });
            System.out.println(asyncRiakClient.read("root", "key1"));
            Thread.sleep(3000);

        }
    }
}


