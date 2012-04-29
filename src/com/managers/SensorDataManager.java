package com.managers;

import com.abstracte.ISensorDataManager;
import com.obj.SensorData;
import com.utils.FileUtils;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 4/27/12
 * Time: 1:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SensorDataManager implements ISensorDataManager {

    Stack<SensorData> dataCol;

    public SensorDataManager()
    {
       dataCol=new Stack<SensorData>();
    }

    @Override
    public void addData(SensorData data) {
        dataCol.push(data);
    }

    @Override
    public void removeData(SensorData data) {
       dataCol.remove(data);
    }

    @Override
    public SensorData getLastSensorDataKnown() {
        if(dataCol.empty()) return new SensorData();
        return dataCol.peek();
    }

    @Override
    public void flushToDataSource() {

        FileUtils.BeginTag("Walk");
        for(SensorData loc:dataCol)
        {
            FileUtils.BeginTag("SensorData");
            FileUtils.WriteTag("Latitude", Double.toString(loc.getLatitudine()));
            FileUtils.WriteTag("Longitude",Double.toString(loc.getLongitudine()));
            FileUtils.WriteTag("Speed",Double.toString(loc.getViteza()));
            FileUtils.WriteTag("DistanceTravelled",Float.toString(loc.getDistantaParcursa()));
            FileUtils.WriteTag("Altitude",Double.toString(loc.getAltitudine()));
            FileUtils.WriteTag("Pressure",Double.toString(loc.getPresiune()));
            FileUtils.WriteTag("Orientation",Float.toString(loc.getOrientare()));
            FileUtils.EndTag("SensorData");
        }
        FileUtils.EndTag("Walk");
        FileUtils.appendToLog();
        dataCol.clear();
    }

    @Override
    public void empty() {
        dataCol.clear();
    }
}
