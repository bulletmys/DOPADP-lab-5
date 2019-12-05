package lab5;

public class TestRequest {
    private String hostName;

    TestRequest(String hostName) {
        this.hostName = hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostName() {
        return hostName;
    }
}
