package pl.edu.agh.marcskow.ftpclient.protocol;

import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.marcskow.ftpclient.client.FtpSession;
import pl.edu.agh.marcskow.ftpclient.response.Response;

import java.io.*;
import java.net.Socket;

@Slf4j
public class LIST {
    private Response response;
    private FtpSession session;

    public LIST(FtpSession session, Response response){
        this.session = session;
        this.response = response;
    }

    public String[] execute() throws IOException {
        String ip = session.getPassiveIp();
        int port = session.getPassivePort();
        String[] listOfFiles = null;

        try(Socket socket = new Socket(ip,port);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
            listOfFiles = (String[])in.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            log.error("LIST failed ", e);
        }

        return listOfFiles;
    }
}
