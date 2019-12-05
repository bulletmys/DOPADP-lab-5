package lab5;

import akka.NotUsed;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClient {

    Flow<HttpRequest, HttpResponse, NotUsed> httpFlow(ActorMaterializer materializer) {
        return Flow
    }
}
