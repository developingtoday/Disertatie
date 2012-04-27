package com.abstracte;

import com.obj.SensorData;

/**
 * Created with IntelliJ IDEA.
 * User: Revan
 * Date: 4/27/12
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISensorDataManager {
    public void addData(SensorData data);
    public void removeData(SensorData data);
    public SensorData getLastSensorDataKnown();
    public void flushToDataSource();
    public void empty();
}
