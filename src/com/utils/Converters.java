package com.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/22/12
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Converters {
    
    public static String getOrientation(float azimuth)
    {
       if(azimuth>=0 && azimuth<90) return "N";
       if(azimuth>=90 && azimuth<180) return "E";
       if(azimuth>=180 && azimuth<270) return "S";
       if(azimuth>=270 && azimuth<360) return "W";
       if(azimuth==360 && azimuth==0) return "N";
        return Float.toString(azimuth);
    }
}
