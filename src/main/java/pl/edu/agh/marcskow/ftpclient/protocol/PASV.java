package pl.edu.agh.marcskow.ftpclient.protocol;

import pl.edu.agh.marcskow.ftpclient.client.FtpSession;
import pl.edu.agh.marcskow.ftpclient.response.Response;


public class PASV extends ActiveCommand {
    private Response response;
    private FtpSession session;

    public PASV(FtpSession session, Response response){
        this.session = session;
        this.response = response;
    }

    @Override
    public void execute() {
        int port = Integer.parseInt(response.getElement(4));
        String ip = response.getElement(3);

        session.setPassivePort(port);
        session.setPassiveIp(ip);
    }
}
