package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static model.GlobalProperties._logger;
import static model.GlobalProperties.getProperty;
import static utils.Constants.*;
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
        if((logs.size()>Integer.parseInt(getProperty(LOG_MAX_SIZE)) || force)&& getProperty(SAVE_LOG).toLowerCase().equals("true")){
            //check and if need create Folder For The Log

            File theDir = new File(getProperty(LOG_LOCATION));
                // if the directory does not exist, create it
            if (!theDir.exists()) {
                try{
                    theDir.mkdir();
                    _logger.debug("folder for log file created");
                }
                catch(SecurityException se){
                    //handle it
                    _logger.error("Cannot create a folder for log file");
                }
            }

            //save log
            String fileName=LOG_FILE_NAME_EXTENSION+"_"+getCurrentTimeStamp()+".txt";
            fileName=fileName.replaceAll("-","-");
            fileName=fileName.replaceAll(" ","_");
            fileName=fileName.replaceAll(":","-");
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(theDir+"\\"+fileName));
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



