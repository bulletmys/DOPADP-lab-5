package lab5;

import akka.NotUsed;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import javafx.util.Pair;


public class HttpClient {

    Flow<HttpRequest, HttpResponse, NotUsed> httpFlow(ActorMaterializer materializer) {
        return Flow.of(HttpRequest.class)
                .map(request -> new Pair<String, Integer>(request.))
//                .mapAsync()
    }
}
