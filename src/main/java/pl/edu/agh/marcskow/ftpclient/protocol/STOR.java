package pl.edu.agh.marcskow.ftpclient.protocol;

import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.marcskow.ftpclient.client.FtpSession;
import pl.edu.agh.marcskow.ftpclient.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class STOR extends PassiveCommand{
    private Response response;
    private FtpSession session;
    private String file;

    public STOR(FtpSession session, Response response, String file){
        this.session = session;
        this.response = response;
        this.file = file;
    }

    @Override
    public void execute(){
        String ip = session.getPassiveIp();
        int port = session.getPassivePort();

        Thread t = new Thread(() -> {
            try(
                Socket socket = new Socket(ip,port);
                InputStream inputStream = new FileInputStream(session.getRootFolder() + file);
                OutputStream out = socket.getOutputStream()){
                byte[] buf = new byte[8192];
                int len = 0;
                while ((len = inputStream.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
            }
            catch (IOException e){
                log.error("STOR failed ", e);
            }
        });
        t.start();
    }
}
