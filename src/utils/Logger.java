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
        private logType TYPE;
        private String message;


        public Log(String date ,logType TYPE, String message) {
            this.date=date;
            this.TYPE = TYPE;
            this.message = message;
        }
    }

    List<Log> logs;

    public Logger() {
        this.logs = new ArrayList<Log>();
    }

    public void debug(String message){
        String date = getCurrentTimeStamp();
        logs.add(new Log(date,logType.DEBUG,message));
        System.out.println(date+" "+ logType.DEBUG+ " "+ " "+message );
        CleanLog();
    }

    public void warning(String message){
        String date = getCurrentTimeStamp();
        logs.add(new Log(date,logType.WARNNING,message));
        System.out.println(date+" "+ logType.WARNNING+ " "+ " "+message );
        CleanLog();
    }

    public void error(String message){
        String date = getCurrentTimeStamp();
        logs.add(new Log(date,logType.ERROR,message));
        System.out.println(date+" "+ logType.ERROR+ " "+ " "+message );
        CleanLog();
    }

    public void CleanLog(){
        //TODO :: after I can check here if to clean the log- save and create new.
    }


}



