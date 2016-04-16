package pl.edu.agh.marcskow.ftpclient.client;

import com.sun.xml.internal.ws.message.stream.StreamAttachment;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SourceType;
import pl.edu.agh.marcskow.ftpclient.protocol.LIST;
import pl.edu.agh.marcskow.ftpclient.protocol.PASV;
import pl.edu.agh.marcskow.ftpclient.protocol.RETR;
import pl.edu.agh.marcskow.ftpclient.protocol.STOR;
import pl.edu.agh.marcskow.ftpclient.response.Parser;
import pl.edu.agh.marcskow.ftpclient.response.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Getter
@Setter
public class FtpSession implements Session {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isUp;
    private int passivePort;
    private String passiveIp;
    private String rootFolder = "/home/intenso/ftpClient/";
    private Signalizer signalizer;
    private TextArea textArea;
    // TODO: 14.04.16 How to do this better ?
    private String lastRequest;
    private SplitPane splitPane;

    public FtpSession(){

    }

    public void startSession(ServerProperties properties, TextArea textArea, SplitPane splitPane) throws IOException {
        startSession(properties);
        this.textArea = textArea;
        this.splitPane = splitPane;
    }

    @Override
    public void startSession(ServerProperties properties) throws IOException {
        int port = properties.getPort();
        String ip = properties.getIp();

        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        isUp = true;

        signalizer = new Signalizer(this);
        signalizer.start();
    }

    @Override
    public void handleResponse() throws IOException {
        if(socket.isClosed() || !socket.isConnected()){
            isUp = false;
            return;
        }
        String responseStr = in.readLine();
        writeToStandardOutput(responseStr);

        Response response = Parser.parse(responseStr);
        int code = response.getIntCode();

        if(code == 227){
            new PASV(this,response).execute();
        } else if(code == 150 && lastRequest.split(" ")[0].equals("STOR")){
            if(lastRequest.split(" ").length > 0) {
                new STOR(this, response, lastRequest.split(" ")[1]).execute();
            }
        } else if(code == 150 && lastRequest.split(" ")[0].equals("RETR")){
            if(lastRequest.split(" ").length > 0) {
                new RETR(this, response, lastRequest.split(" ")[1]).execute();
            }
        } else if(code == 150 && lastRequest.split(" ")[0].equals("LIST")){
            if(lastRequest.split(" ").length > 0) {
                String[] list = new LIST(this, response).execute();
            }
        }
    }

    @Override
    public void writeToStandardOutput(String message) {
        //System.out.println(message);
        writeToTextArea(message);
    }

    public void writeToTextArea(String message){
        textArea.appendText(message + "\n");
    }

    @Override
    public synchronized void sendRequest(String message) throws IOException {
        lastRequest = message;
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

    @Override
    public void setPassivePort(int port) {
        passivePort = port;
    }

    @Override
    public void setPassiveIp(String ip) {
        passiveIp = ip;
    }
}
