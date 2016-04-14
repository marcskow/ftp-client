package pl.edu.agh.marcskow.ftpclient.client;

import java.io.IOException;

public interface Session {
    void startSession(ServerProperties properties) throws IOException;

    void handleResponse() throws IOException;

    void sendRequest(String message) throws IOException;

    boolean isUp();

    void closeConnection() throws IOException;

    void writeToStandardOutput(String message);
}
