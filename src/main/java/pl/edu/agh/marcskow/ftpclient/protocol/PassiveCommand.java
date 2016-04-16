package pl.edu.agh.marcskow.ftpclient.protocol;


import pl.edu.agh.marcskow.ftpclient.response.Response;

import java.io.IOException;

public abstract class PassiveCommand {
    public abstract void execute() throws IOException;
}
