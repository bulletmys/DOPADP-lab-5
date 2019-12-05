package lab5;

//import akka.NotUsed;
//import akka.actor.ActorRef;
//import akka.actor.ActorSystem;
//import akka.http.javadsl.model.HttpRequest;
//import akka.http.javadsl.model.HttpResponse;
//import akka.pattern.Patterns;
//import akka.stream.ActorMaterializer;
//import akka.stream.javadsl.Flow;
//import javafx.util.Pair;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.*;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;


public class HttpClient {

    private ActorRef cacheActor;

    HttpClient(ActorSystem system) {
        cacheActor = system.actorOf(CacheActor.props(), "cacheActor");
    }

    Flow<HttpRequest, HttpResponse, NotUsed> httpFlow(ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(request -> {
                    return new Pair<String, Integer>(
                            request.getUri().query().getOrElse("testURL", ""),
                            Integer.parseInt(request.getUri().query().getOrElse("count", "")));
                })
                .mapAsync(3, (request) -> {
                    Patterns.ask(cacheActor, request, 5000)
                            .thenCompose()
                });
    }
}