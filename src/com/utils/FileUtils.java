package com.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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

    public static void WriteTag(String tagXml,String value)
    {
        sbufferLog.append("<"+tagXml+">");
        sbufferLog.append(value);
        sbufferLog.append("</"+tagXml+">");
        sbufferLog.append("\n");

    }
    private static void appendToLog(String appe)
    {

        try
        {
            File file=new File("/sdcard/log.txt");
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
