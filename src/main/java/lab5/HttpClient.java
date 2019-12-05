package lab5;

import akka.NotUsed;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import javafx.util.Pair;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {

    Flow<HttpRequest, HttpResponse, NotUsed> httpFlow(ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(k -> new Pair<String, Integer>(k.))
//                .mapAsync()
    }
}
