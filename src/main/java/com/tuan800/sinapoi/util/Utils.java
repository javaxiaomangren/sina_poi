package com.tuan800.sinapoi.util;

import com.tuan800.sinapoi.model.Coordinate;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Some utilities methods
 * @author Yang hua
 */
public class Utils {

    /* The configuration file instance */
    private static PropertiesConfiguration properties = null ;

    /**
     * format a date as the specific format
     * @param date
     * @param format
     * @return  a string of date
     */
    public static String format(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format) ;
        String s = df.format(date);
        return s ;
    }

    /**
     * parse a date as the specific date format
     * @param date
     * @param format
     * @return  a Date by the specific date format
     */
    public static Date format(String date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format) ;
        Date d = null ;
        try {
             d = df.parse(date) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d ;
    }

    /**
     *  Get the configuration properties file instance
     * @return
     */
    public static PropertiesConfiguration getConfig(){
        try {
            if(null == properties)
                properties = new PropertiesConfiguration("config.properties") ;
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return properties ;
    }

    /**
     * generate a multi point by the min, max lat and lng
     * @param minLat
     * @param maxLat
     * @param minLng
     * @param maxLng
     * @return a set of multi point
     */
    public static Set<Coordinate> genMultiPoint(float minLat, float maxLat, float minLng, float maxLng){
        Set<Coordinate> coordinates = new HashSet<Coordinate>() ;
        for(float i=minLat ; i<= maxLat ; i+=0.003100f){
            for (float j =  minLng ; j < maxLng; j+=0.004100f) {
                Coordinate coor = new Coordinate(new BigDecimal(i), new BigDecimal(j)) ;
                coordinates.add(coor) ;
            }
        }
        return coordinates ;
    }

    /**
     * Calculate the distance of two point
     * @param _lat
     * @param _lng
     * @param lat
     * @param lng
     * @return  distance of two point(float)
     */
    public static float getDistance(float _lat, float _lng, float lat, float lng) {

        double dx = Math.PI * 2 * 6378137 * Math.cos(_lat / 180 * Math.PI) * (_lng - lng) / 360;
        double dy = Math.PI * 2 * 6356752 * (_lat - lat) / 360;
        double value = Math.sqrt(dx * dx + dy * dy);
        return (float) value;
    }


}
