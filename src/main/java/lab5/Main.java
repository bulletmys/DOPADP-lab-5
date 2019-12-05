package lab5;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        final ActorMaterializer materializer =
                ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = <
//        вызов
//        метода которому передаем Http, ActorSystem и ActorMaterializer
        >;
        final CompletionStage<Ser
        verBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress
                RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate()); // and shutdown
        when done
    }
}
