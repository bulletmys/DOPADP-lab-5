package lab5;

import akka.actor.AbstractActor;
import akka.actor.Props;

import java.util.HashMap;

public class CacheActor extends AbstractActor {

    private HashMap<String, Integer> hashRes = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TestRequest.class, mail -> {
                    if (hashRes.containsKey(mail.getHostName())) {
                        sender().tell(new TestResponse(mail.getHostName(), hashRes.get(mail.getHostName())), self());
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
