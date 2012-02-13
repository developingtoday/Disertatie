package Obj;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/13/12
 * Time: 8:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class GpsPoint {
    private double Latitude, Longitude,Altitude;

    public double  getLatitude()
    {
        return Latitude;
    }
    public double getLongitude()
    {
        return Longitude;
    }
    public void setLatitude(double value)
    {
        Latitude=value;
    }
    public void setLongitude(double value)
    {
        Longitude=value;
    }
    public double getAltitude()
    {
        return Altitude;
    }
    public void setAltitude(double value)
    {
        Altitude=value;
    }
    @Override
    public String toString()
    {
         return Latitude+" "+Longitude+" "+Altitude;
    }
}

