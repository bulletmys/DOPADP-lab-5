package lab5;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.Pair;

import java.util.HashMap;

public class CacheActor extends AbstractActor {

    private HashMap<String, Long> hashRes = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Pair.class, mail -> {
                    String key = (String) mail.first();
                    if (hashRes.containsKey(key)) {
                        sender().tell(new TestResponse(key, hashRes.get(key)), self());
                    } else {
                        sender().tell("NotFound", self());
                    }
                })
                .match(TestResponse.class, mail -> {
                    hashRes.put(mail.getHostName(), mail.getAverageTime());
                })
                .build();
    }

    public static Props props() {
        return Props.create(CacheActor.class);
    }

}
