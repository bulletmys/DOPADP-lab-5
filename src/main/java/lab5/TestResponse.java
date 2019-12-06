package lab5;

public class TestResponse {
    private String hostName;
    private long averageTime;

    TestResponse(String hostName, int averageTime) {
        this.hostName = hostName;
        this.averageTime = averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public String getHostName() {
        return hostName;
    }
}
