package riak.utils;

import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakLink;
import com.basho.riak.client.builders.RiakObjectBuilder;
import com.basho.riak.client.raw.RawClient;
import com.basho.riak.client.raw.RiakResponse;
import com.basho.riak.client.raw.StoreMeta;
import com.basho.riak.client.raw.pbc.PBClientAdapter;
import com.basho.riak.pbc.RiakClient;

import java.io.IOException;

/**
 * Created by miguel on 06/06/14.
 */
public class App2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        RiakClient pbcClient = new RiakClient("127.0.0.1");
// OR
// com.basho.riak.client.http.RiakClient httpClient = new
// com.basho.riak.client.http.RiakClient("http://127.0.0.1:8098/riak");
        RawClient rawClient = new PBClientAdapter(pbcClient);
// OR new HTTPClientAdapter(httpClient);

        IRiakObject riakObject = RiakObjectBuilder.newBuilder("root", "key1").withValue("value1").build();
        rawClient.store(riakObject, new StoreMeta.Builder().build());
        RiakResponse fetched = rawClient.fetch("root", "key1");
        IRiakObject result = null;

        if (fetched.hasValue()) {
            if (fetched.hasSiblings()) {
                //do what you must to resolve conflicts
            } else {
                result = fetched.getRiakObjects()[0];
            }
        }

        result.addLink(new RiakLink("otherBucket", "otherKey", "tag"));
        result.setValue("newValue");

        RiakResponse stored = rawClient.store(result, new StoreMeta.Builder().build());

        IRiakObject updated = null;

        if (stored.hasValue()) {
            if (stored.hasSiblings()) {
                //do what you must to resolve conflicts
            } else {
                updated = stored.getRiakObjects()[0];
            }
        }
        rawClient.shutdown();
    }
}
