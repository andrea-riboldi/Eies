package it.micronixnetwork.pipe.gui.server;

import java.net.*;
import java.io.*;

public class PipeServerThread extends Thread {
    private Socket socket = null;
    
    private final PipeServer server;

    public PipeServerThread(Socket socket,PipeServer server) {
        super("PipeServer");
        this.socket = socket;
        this.server=server;
    }
    
    public void run() {

        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            PipeProtocol kkp = new PipeProtocol(server);
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
