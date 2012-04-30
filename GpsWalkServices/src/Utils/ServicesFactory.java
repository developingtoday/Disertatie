package Utils;

import Servicii.GoogleElevationQueryWeb;
import Servicii.GoogleReverseGeocodeQueryWeb;
import Servicii.GoogleWeatherQueryWeb;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/16/12
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServicesFactory {
    private static GoogleElevationQueryWeb _Google_elevationService;
    private static GoogleReverseGeocodeQueryWeb _Google_reverseGeocodeService;
    private static GoogleWeatherQueryWeb _Google_weatherQueryWeb;
    public static synchronized GoogleElevationQueryWeb getElevationService()
    {
        if(_Google_elevationService ==null) _Google_elevationService =new GoogleElevationQueryWeb();
        return _Google_elevationService;
    }
    public static synchronized GoogleReverseGeocodeQueryWeb getReverseGeocodeService()
    {
        if(_Google_reverseGeocodeService ==null) _Google_reverseGeocodeService =new GoogleReverseGeocodeQueryWeb();
        return _Google_reverseGeocodeService;
    }
    public static synchronized GoogleWeatherQueryWeb getWeatherService()
    {
        if(_Google_weatherQueryWeb ==null) _Google_weatherQueryWeb =new GoogleWeatherQueryWeb();
        return _Google_weatherQueryWeb;
    }
}
