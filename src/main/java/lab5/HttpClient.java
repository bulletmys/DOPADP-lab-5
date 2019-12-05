package lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.japi.Pair;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;


import java.time.Duration;
import java.util.concurrent.CompletableFuture;


public class HttpClient {

    private ActorRef cacheActor;
    private Duration duration = Duration.ofSeconds(5);

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
                .mapAsync(3, (request) ->
                        Patterns.ask(cacheActor, request, duration)
                                .thenCompose((response) -> {
                                    if (response.getClass() == String.class) {
                                        //считаем время и записываем его
                                    } else {
                                        CompletableFuture.completedFuture()
                                    }
                                })
    });
}
}