package Utils;

import Servicii.ElevationQueryWeb;
import Servicii.ReverseGeocodeQueryWeb;
import Servicii.WeatherQueryWeb;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/16/12
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServicesFactory {
    private static ElevationQueryWeb _elevationService;
    private static ReverseGeocodeQueryWeb _reverseGeocodeService;
    private static WeatherQueryWeb _weatherQueryWeb;
    public static synchronized ElevationQueryWeb getElevationService()
    {
        if(_elevationService==null) _elevationService=new ElevationQueryWeb();
        return _elevationService;
    }
    public static synchronized  ReverseGeocodeQueryWeb getReverseGeocodeService()
    {
        if(_reverseGeocodeService==null) _reverseGeocodeService=new ReverseGeocodeQueryWeb();
        return _reverseGeocodeService;
    }
    public static synchronized WeatherQueryWeb getWeatherService()
    {
        if(_weatherQueryWeb==null) _weatherQueryWeb=new WeatherQueryWeb();
        return _weatherQueryWeb;
    }
}
