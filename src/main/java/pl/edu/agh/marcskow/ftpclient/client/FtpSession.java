package pl.edu.agh.marcskow.ftpclient.client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.hibernate.annotations.SourceType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class FtpSession implements Session {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isUp;

    public FtpSession(){

    }

    @Override
    public void startSession(ServerProperties properties) throws IOException {
        int port = properties.getPort();
        String ip = properties.getIp();

        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        isUp = true;
    }

    @Override
    public void handleResponse() throws IOException {
        String response = in.readLine();

        writeToStandardOutput(response);


    }

    @Override
    public void writeToStandardOutput(String message) {
        System.out.println(message);
    }

    @Override
    public void sendRequest(String message) throws IOException {
        out.println(message);
    }

    @Override
    public boolean isUp() {
        return isUp;
    }

    @Override
    public void closeConnection() throws IOException {
        socket.close();
        out.close();
        in.close();
        isUp = false;
    }
}
