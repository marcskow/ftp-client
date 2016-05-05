package pl.edu.agh.marcskow.ftpclient.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.marcskow.ftpclient.client.FtpSession;
import pl.edu.agh.marcskow.ftpclient.client.ServerProperties;
import pl.edu.agh.marcskow.ftpclient.layout.SimpleFileTreeItem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class ClientController extends AnchorPane implements Initializable {

    //Basic connection
    @FXML private TextField ipTextField;
    @FXML private TextField portTextField;
    @FXML private Button connectionButton;

    //Login controllers
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Button loginButton;

    //Request controller
    @FXML private TextField requestTextField;
    @FXML private Button requestButton;

    //Split area controller
    @FXML private AnchorPane splitFirst;
    @FXML private SplitPane split;
    @FXML private TextArea responseTextArea;
    @FXML private TreeView<File> tree;

    //Session
    private FtpSession session;
    private ServerProperties serverProperties;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localDirectoryInitializaiton();

        session = new FtpSession();
    }

    public void localDirectoryInitializaiton(){
        TreeView<File> fileView = new TreeView<>(
                new SimpleFileTreeItem(new File("/home/intenso/ftpClient")));

        split.getItems().removeAll();
        split.getItems().clear();
        split.getItems().add(new TreeView<>());
        split.getItems().add(fileView);
    }

    @FXML
    public void connect(){
        try {
            startSession();
        }
        catch (IOException e){
            log.error("Error in session starting ", e);
        }
    }

    public void startSession() throws IOException {
        String ip = ipTextField.getText();
        int port = Integer.parseInt(portTextField.getText());
        serverProperties = new ServerProperties(ip, port);

        session.startSession(serverProperties,responseTextArea, split);

        startListeningForResponse();
    }

    public void startListeningForResponse() {
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
    }

    @FXML
    public void sendRequest(){
        String message = requestTextField.getText();

        try {
            session.sendRequest(message);
        }
        catch (IOException e){
            log.error("Request error ", e);
        }
    }

    @FXML
    public void login(){
    }
}
