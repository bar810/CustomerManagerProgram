/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.*;
import static model.GlobalProperties._logger;
import static model.GlobalProperties.getProperty;
import static utils.Utils.getCurrentTimeStamp;

/**
 * @author bbrownsh
 * @since 12/25/2018
 */


public class Logger {
    public enum logType {
        DEBUG, WARNNING, ERROR;
    }

    public class Log {
        private String date;
        private logType type;
        private String message;


        public Log(String date ,logType type, String message) {
            this.date=date;
            this.type = type;
            this.message = message;
        }

        public void printToConsole(){
            System.out.println(date+" "+ type+ " "+ " "+message );
        }
        public String toString(){
            return date+" "+ type+ " "+ " "+message;
        }

    }

    List<Log> logs;

    public Logger() {
        this.logs = new ArrayList<Log>();
    }

    public void debug(String message){
        Log log=new Log(getCurrentTimeStamp(),logType.DEBUG,message);
        log.printToConsole();
        logs.add(log);
        CleanAndSaveLogIfNeeded(false);
    }

    public void warning(String message){
        Log log=new Log(getCurrentTimeStamp(),logType.WARNNING,message);
        log.printToConsole();
        logs.add(log);
        CleanAndSaveLogIfNeeded(false);
    }

    public void error(String message){
        Log log=new Log(getCurrentTimeStamp(),logType.ERROR,message);
        log.printToConsole();
        logs.add(log);
        CleanAndSaveLogIfNeeded(false);
    }

    public void CleanAndSaveLogIfNeeded(boolean force){
        if((logs.size()>Integer.parseInt(getProperty(LOG_MAX_SIZE)) || force)&& SAVE_LOG.toLowerCase().equals("true")){
            //save log
            String fileName=LOG_FILE_NAME_EXTENSION+"_"+getCurrentTimeStamp()+".txt";
            fileName=fileName.replaceAll("-","-");
            fileName=fileName.replaceAll(" ","_");
            fileName=fileName.replaceAll(":","-");
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
                for (Log log : logs)
                    pw.println(log.toString());
                pw.close();
                logs.clear();
            } catch (Exception ex) {
                _logger.error("Log save failed");
            }
        }
    }


}



