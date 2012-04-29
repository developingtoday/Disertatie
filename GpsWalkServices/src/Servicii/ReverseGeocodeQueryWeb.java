/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicii;

import Abstract.AbstractXmlQuery;
import Obj.GeoInfo;
import javax.xml.xpath.XPathConstants;

import Obj.GpsPoint;
import org.w3c.dom.NodeList;

/**
 *
 * @author Revan
 */
public class ReverseGeocodeQueryWeb extends AbstractXmlQuery<GeoInfo> {

    private GeoInfo Point;

    public GeoInfo getPoint() {
        return Point;
    }

    public void setPoint(GeoInfo Point) {
        this.Point = Point;
    }
    private String urlTemp = "http://maps.googleapis.com/maps/api/geocode/xml?latlng=44.200172,28.625323&sensor=true";

    public ReverseGeocodeQueryWeb()
    {
        super("http://maps.googleapis.com/maps/api/geocode/xml?latlng=");
    }

    @Override
    protected void setupUrlWithPoint(GpsPoint point) {
        String lat = Double.toString(point.getLatitude());
        String longit = Double.toString(point.getLongitude());
        Url += lat + "," + longit + "&sensor=true";
    }

    @Override
    protected GeoInfo populeaza() throws Exception {
        GeoInfo Point=new GeoInfo();
        NodeList nodes;
       String queryAdresa = "//GeocodeResponse[status='OK']/result[type='route']/formatted_address/text()"; //intreaga adresa
        String queryLocalitate = "//GeocodeResponse[status='OK']/result[type='route']/address_component[type='locality']/long_name/text()"; //localitatea
      String queryTara = "//GeocodeResponse[status='OK']/result[type='route']/address_component[type='country']/long_name/text()"; //tara
        String queryJudet = "//GeocodeResponse[status='OK']/result[type='route']/address_component[type='administrative_area_level_1']/long_name/text()"; //judetul
       nodes=queryXml(queryAdresa, XPathConstants.NODESET);
       Point.setAdress(nodes.item(0).getNodeValue());
       nodes=queryXml(queryLocalitate, XPathConstants.NODESET);
       Point.setCity(nodes.item(0).getNodeValue());          //TODO add validation
       nodes=queryXml(queryJudet, XPathConstants.NODESET);
       Point.setAdministrativeLevel1(nodes.item(0).getNodeValue());
       nodes=queryXml(queryTara, XPathConstants.NODESET);
       Point.setCountry(nodes.item(0).getNodeValue());
        return Point;
    }


}
