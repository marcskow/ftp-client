package pl.edu.agh.marcskow.ftpclient.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Signalizer extends Thread{
    private FtpSession session;

    public Signalizer(FtpSession session){
        this.session = session;
    }

    public void run(){
        try {
            while (session.isUp()) {
                session.sendRequest("NOOP");
                try {
                    Thread.sleep(30000);
                }
                catch (InterruptedException e){
                    log.info("Signalizer interrupted ", e);
                }
            }
        }
        catch (IOException e){
            log.error("Error in NOOP loop ", e);

        }
    }
}
