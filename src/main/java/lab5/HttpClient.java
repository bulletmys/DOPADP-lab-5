package lab5;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.japi.Pair;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.asynchttpclient.Dsl;


import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;


public class HttpClient {

    private ActorRef cacheActor;
    private Duration duration = Duration.ofSeconds(5);
    private Sink<Pair<String, Integer>, CompletionStage<Integer>> testSink =
            Flow
                    .<Pair<String, Integer>>create()
                    .mapConcat((request) -> Collections.nCopies(request.second(), request.first()))
                    .mapAsync(3, (request) -> {
                        long startTime = System.currentTimeMillis();

                        Dsl.asyncHttpClient()
                                .prepareGet(request)
                                .execute()
                                .toCompletableFuture()
                                .then
                    })

    HttpClient(ActorSystem system) {
        cacheActor = system.actorOf(CacheActor.props(), "cacheActor");
    }

    Flow<HttpRequest, HttpResponse, NotUsed> httpFlow(ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(request -> new Pair<String, Integer>(
                        request.getUri().query().getOrElse("testURL", ""),
                        Integer.parseInt(request.getUri().query().getOrElse("count", ""))))
                .mapAsync(3, (request) ->
                        Patterns.ask(cacheActor, request, duration)
                                .thenCompose((response) -> {
                                    if (response.getClass() == String.class) {
                                        Source.from(Collections.singletonList(request))
                                                .toMat(testSink, Keep.right()).run(materializer);
                                        return CompletableFuture.completedFuture(response);
                                    } else {
                                        return CompletableFuture.completedFuture(response);
                                    }
                                }))
                .map(value -> {
                    cacheActor.tell(value, ActorRef.noSender());
                    return HttpResponse.create().withEntity(
                            HttpEntities.create(
                                    ((TestResponse) value).getAverageTime() + " --- " + ((TestResponse) value).getHostName()
                            ));
                });
    }
}