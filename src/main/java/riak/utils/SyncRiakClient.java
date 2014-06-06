package riak.utils;

import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.raw.RawClient;
import com.basho.riak.client.raw.RiakResponse;
import com.basho.riak.client.raw.pbc.PBClientAdapter;
import com.basho.riak.pbc.RiakClient;

import java.io.IOException;


public class SyncRiakClient {
    private RawClient rawClient;

    public SyncRiakClient(String host) throws IOException {
        RiakClient pbcClient = new RiakClient(host);
        rawClient = new PBClientAdapter(pbcClient);
    }

    public String read(String bucketName, String key) throws IOException {
        RiakResponse fetched = rawClient.fetch(bucketName, key);
        IRiakObject result = null;

        if (fetched.hasValue()) {
            if (fetched.hasSiblings()) {
                //do what you must to resolve conflicts
            } else {
                result = fetched.getRiakObjects()[0];
            }
        }
        return result == null ? null : result.getValueAsString();
    }

    public void close() {
        rawClient.shutdown();
        rawClient = null;
    }

}
