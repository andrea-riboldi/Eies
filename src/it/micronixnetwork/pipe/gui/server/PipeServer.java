package it.micronixnetwork.pipe.gui.server;

import it.micronixnetwork.pipe.PipeRunner;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class PipeServer implements Runnable{

    Integer port;

    PipeRunner runner;

    public PipeServer() {
    }

    public void run() {

        int portNumber = 4444;

        if (port != null) {
            portNumber = port;
        }

        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new PipeServerThread(serverSocket.accept(),this).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
    
    public void start() {
        System.out.println("Start server control");
        Thread t = new Thread(this, "Control Server");
        t.start();
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public PipeRunner getRunner() {
        return runner;
    }

    public void setRunner(PipeRunner runner) {
        this.runner = runner;
    }

}
