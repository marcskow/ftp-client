package pl.edu.agh.marcskow.ftpclient.command;

/**
 * Created by intenso on 09.04.16.
 */
public class USER extends AbstractCommand {
    private String arg;

    public USER(String arg){
        this.arg = arg;
    }
}
