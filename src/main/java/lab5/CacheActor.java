package lab5;

import akka.actor.AbstractActor;

public class CacheActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match()
    }
}
