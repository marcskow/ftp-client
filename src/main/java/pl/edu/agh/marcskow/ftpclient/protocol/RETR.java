package pl.edu.agh.marcskow.ftpclient.protocol;

import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.marcskow.ftpclient.client.FtpSession;
import pl.edu.agh.marcskow.ftpclient.response.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


@Slf4j
public class RETR extends PassiveCommand {
    private Response response;
    private FtpSession session;
    private String file;

    public RETR(FtpSession session, Response response, String file){
        this.session = session;
        this.response = response;
        this.file = file;
    }

    @Override
    public void execute(){
        String ip = session.getPassiveIp();
        int port = session.getPassivePort();

        Thread t = new Thread(() -> {
            try(Socket socket = new Socket(ip,port);
                InputStream in = socket.getInputStream();
                OutputStream out = new FileOutputStream(file)){
                byte[] buf = new byte[8192];
                int len = 0;
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
            }
            catch (IOException e){
                log.error("RETR failed ", e);
            }
        });
        t.start();
    }
}
