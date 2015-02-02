package it.micronixnetwork.pipe.gui.server;

import it.micronixnetwork.pipe.PipeRunner;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class PipeProtocol {

    final static String STOP = "stop";

    final CommandLineParser parser;
    
    final PipeServer server;

    private HashMap<String, Options> commands;

    public PipeProtocol(PipeServer server) {
        this.server=server;
        this.parser = new BasicParser();
        Option help = new Option("help", "print command help");
        Options options = new Options();
        //Registrazione comandi
        commands = new HashMap<String, Options>();
        options.addOption(help);
        commands.put(STOP, options);
    }

    public String processInput(String input) {
        PipeRunner runner=server.getRunner();
        if(runner==null){
            return "No running pipe";
        }
        String theOutput = "Wait command!";
        if (input != null && input.trim().length()>0) {
            theOutput = "Command not found";
            int index=input.indexOf(' ');
            String cmd=null;
            String[] to_parse=null;
            if(index>0){
                cmd = input.substring(0,index).trim();
                to_parse = input.substring(index).trim().split(" ");
            }else{
                cmd = input.trim();
                to_parse = new String[]{};
            }
            CommandLine line;
            try {
                if (cmd.equals(STOP)) {
                    Options opts = commands.get(STOP);
                    line = parser.parse(opts, to_parse);
                    if (line.getOptions().length == 0) {
                        if(runner!=null){
                            server.getRunner().stop();
                            theOutput = "Stop command processed";
                        }
                    } else {
                        if (line.hasOption("help")) {
                            theOutput = "Use stop to terminate any work in rogress or stop -help for this message";
                        }
                        
                    }
                }
            } catch (ParseException ex) {
                theOutput = "Option not found use -help for info";
            }
        }
        return theOutput;
    }

    public static void main(String[] args) {

        Option help = new Option("help", "print this message");
        Option logfile = OptionBuilder.withArgName("file")
                .hasArg()
                .withDescription("use given file for log")
                .create("logfile");

        Options options = new Options();

        options.addOption(help);

        options.addOption(logfile);

        CommandLineParser parser = new BasicParser();

        String input = "stop -logfile prova";

        try {
            CommandLine line = parser.parse(options, input.split(" "));
            System.out.println(line.getArgs());
            if (line.hasOption("logfile")) {

                System.out.println(line.getOptionValue("logfile"));
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }

}
