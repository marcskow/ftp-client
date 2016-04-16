package pl.edu.agh.marcskow.ftpclient.client;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class Client {
    private FtpSession session;

    public Client(){
        session = new FtpSession();
    }

    public void connect(ServerProperties serverProperties){
        try {
            session.startSession(serverProperties);
        } catch (IOException e){
            log.error("Connection error", e);
        }

        Thread t = new Thread(() -> {

            while (session.isUp()){
                try {
                    session.handleResponse();
                }
                catch (IOException e){
                    log.error("Session error", e);
                }
            }
        });
        t.start();

        Scanner in = new Scanner(System.in);

        String request;
        while (true){
            request = in.nextLine();

            if(request.equals("QUIT INSTANTLY")){
                break;
            }
            else{
                try {
                    session.sendRequest(request);
                }
                catch (IOException e){
                    log.error("Request error ", e);
                }
            }
        }
    }


}
