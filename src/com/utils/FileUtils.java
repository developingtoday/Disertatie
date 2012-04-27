package com.utils;

import android.provider.ContactsContract;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 4/25/12
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public  class FileUtils {

    static StringBuffer sbufferLog=new StringBuffer();
   static BufferedWriter bw;

   public static void BeginTag(String tagXml)
   {
       sbufferLog.append("<"+tagXml+">");
   }

    public static void WriteTag(String tagXml,String value)
    {
        sbufferLog.append("<"+tagXml+">");
        sbufferLog.append(value);
        sbufferLog.append("</"+tagXml+">");
        sbufferLog.append("\n");

    }

    public static void EndTag(String tagXml)
    {
        sbufferLog.append("</"+tagXml+">");
    }
    private static void appendToLog(String appe)
    {

        try
        {
            String fileName="log_"+Long.toString(new GregorianCalendar().getTime().getTime());
            File dir=new File("/sdcard/GpsWalk/");
            if(!dir.exists())
            {
                dir.mkdir();
            }
            File file=new File("/sdcard/GpsWalk/"+fileName+".xml");

            if(!file.exists())
            {

                file.createNewFile();
            }

            bw=new BufferedWriter(new FileWriter(file,true));
            bw.write(appe);
            bw.newLine();
            bw.close();

        }catch (Exception ex)
        {
            Log.d("Serviciu log", ex.getMessage(), ex);
        }
        finally {
            if(bw==null) return;
            bw=null;
        }


    }
    public static void appendToLog()
    {
        appendToLog(sbufferLog.toString());
        sbufferLog=null;
        sbufferLog=new StringBuffer();

    }

}
