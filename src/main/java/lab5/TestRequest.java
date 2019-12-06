package lab5;


import akka.japi.Pair;

public class TestRequest {
    private Pair<String, Integer> req;

    TestRequest(Pair<String, Integer> pair) {
        this.req = pair;
    }

    public Pair<String, Integer> getReq() {
        return req;
    }

    public void setReq(Pair<String, Integer> req) {
        this.req = req;
    }
}
