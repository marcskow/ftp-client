package pl.edu.agh.marcskow.ftpclient.client;

import lombok.Data;

@Data
public class ServerProperties {
    private final String ip;
    private final int port;

    public ServerProperties(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
}
