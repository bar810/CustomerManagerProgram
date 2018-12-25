/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

package utils;

import java.util.ArrayList;
import java.util.List;

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

        public void ToString(){
            System.out.println(date+" "+ type+ " "+ " "+message );
        }
    }

    List<Log> logs;

    public Logger() {
        this.logs = new ArrayList<Log>();
    }

    public void debug(String message){
        Log log=new Log(getCurrentTimeStamp(),logType.DEBUG,message);
        log.ToString();
        logs.add(log);
        CleanLog();
    }

    public void warning(String message){
        Log log=new Log(getCurrentTimeStamp(),logType.WARNNING,message);
        log.ToString();
        logs.add(log);
        CleanLog();
    }

    public void error(String message){
        Log log=new Log(getCurrentTimeStamp(),logType.ERROR,message);
        log.ToString();
        logs.add(log);
        CleanLog();
    }

    public void CleanLog(){
        //TODO :: after I can check here if to clean the log- save and create new.
    }


}



