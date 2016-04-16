package pl.edu.agh.marcskow.ftpclient.application;

import pl.edu.agh.marcskow.ftpclient.client.Client;
import pl.edu.agh.marcskow.ftpclient.client.ServerProperties;


public class ConsoleApplication {

    public static void main(String[] args) {
        Client client = new Client();
        client.connect(new ServerProperties("127.0.0.1",4444));
    }
}
