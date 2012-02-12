package main;

import Obj.GeoInfo;
import Servicii.ReverseGeocodeQueryWeb;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/12/12
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainTest {
    public static void main(String[] args) throws Exception
       {
           GeoInfo p=new GeoInfo();
           p.setLatitude(44.200172);
           p.setLongitude(28.625323);
           ReverseGeocodeQueryWeb r=new ReverseGeocodeQueryWeb(p);
           r.Populeaza();
           System.out.println(r.getPoint().toString());
       }
    
}
