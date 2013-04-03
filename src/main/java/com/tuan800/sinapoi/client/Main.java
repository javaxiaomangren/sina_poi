package com.tuan800.sinapoi.client;

import com.tuan800.sinapoi.internal.TaskExecutorThreadImpl;
import com.tuan800.sinapoi.internal.TaskManagerMultiImpl;
import com.tuan800.sinapoi.util.Utils;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Main Class of this program
 */
public class Main {
    public static void main(String[] args) {
        PropertiesConfiguration conf = Utils.getConfig() ;
        String dowhat=conf.getString("dowhat") ;
        if("publish".equals(dowhat)){
            new TaskManagerMultiImpl().getPersistence().publish();
        }else if("fetch".equals(dowhat)){
           new TaskExecutorThreadImpl().execute();
        }else{
            System.exit(0);
        }
    }
}
