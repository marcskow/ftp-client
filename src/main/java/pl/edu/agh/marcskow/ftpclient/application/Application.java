package pl.edu.agh.marcskow.ftpclient.application;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Application {
    private static BufferedReader in;
    private static PrintWriter out;
    private static OutputStream outP;
    private static Socket socket;

    public static void main(String[] args) {
        String message;

        try {
            Socket socket = new Socket("127.0.0.1", 4444);

            Scanner standardIn = new Scanner(System.in);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String command;
            Thread t = new Thread(new Listener());
            t.start();

            while(true){
                command = standardIn.nextLine();
                out.println(command);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static void handleResponse() throws IOException{
        System.out.println(in.readLine());
    }

    private static class Listener implements Runnable{
        int portInt;
        String ip;

        @Override
        public void run() {
            try {
                String response = "";
                while (true) {
                    response = in.readLine();
                    System.out.println(response);

                    if (response.split(" ")[0].equals("227")) {
                        String port = response.split(" ")[5];
                        ip = response.split(" ")[4];
                        portInt = Integer.parseInt(port);
                        System.out.println(portInt);
                        System.out.println(ip);
                    }

                    if (response.split(" ")[0].equals("150") && response.split(" ")[1].equals("FILE:")) {
                        //      System.out.println("Otrzymuje");
                        Socket socket = new Socket("127.0.0.1", portInt);
                        //     System.out.println("CO ? Xd");
                        InputStream inputStream = new FileInputStream("Zielony.txt");
                        //     System.out.println("jestem tutaj");
                        outP = socket.getOutputStream();
                        byte[] buf = new byte[8192];
                        int len = 0;
                        while ((len = inputStream.read(buf)) != -1) {
                            //          System.out.println("dziala tutaj");
                            outP.write(buf, 0, len);
                        }
                    }

                    if (response.split(" ")[0].equals("150") && response.split(" ")[1].equals("Opening")) {
                        System.out.println("Probuje na ip: " + ip + " port: " + portInt);
                        Socket socket = new Socket("127.0.0.1", portInt);
                        InputStream inputStream = socket.getInputStream();
                        OutputStream out = new FileOutputStream("JakisSiemisznyplik");
                        byte[] buf = new byte[8192];
                        int len = 0;
                        while ((len = inputStream.read(buf)) != -1) {
                            out.write(buf, 0, len);
                            System.out.println(len);
                        }

                        System.out.println("bylem tu");
                        socket.close();
                        out.close();
                        inputStream.close();
                    }
                }
            } catch (IOException e){

            }
        }
    }
}
