/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicii;

import Abstract.AbstractXmlQuery;
import Obj.GeoInfo;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Revan
 */
public class ReverseGeocodeQueryWeb extends AbstractXmlQuery {

    private GeoInfo Point;

    public GeoInfo getPoint() {
        return Point;
    }

    public void setPoint(GeoInfo Point) {
        this.Point = Point;
    }
    private String urlTemp = "http://maps.googleapis.com/maps/api/geocode/xml?latlng=44.200172,28.625323&sensor=true";

    public ReverseGeocodeQueryWeb(GeoInfo Point) {
        this.Point = Point;
        PrepareUrl();

    }
    public ReverseGeocodeQueryWeb()
    {
        Point=new GeoInfo();
    }

    public void PrepareUrl() {
        String aux = "http://maps.googleapis.com/maps/api/geocode/xml?latlng=";
        if (Point == null) {
            return;
        }
        String lat = Double.toString(Point.getLatitude());
        String longit = Double.toString(Point.getLongitude());
        aux += lat + "," + longit + "&sensor=true";
        setUrl(aux);
    }

    @Override
    public void ProcesQuery(NodeList nodes) {
        //TODO asta mutata intr-o interfata 
       /* if (nodes.getLength() == 0) {
            return;
        }
        Node nodAux;
        NodeList childs;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
            System.out.println(nodes.item(i).getParentNode().getNodeName());
        }
        */
    }
    
   

    public void Populeaza() throws Exception {

        //TODO verificat daca nu are nici o componenta ce se intampla
        NodeList nodes;
       String queryAdresa = "//GeocodeResponse[status='OK']/result[type='route']/formatted_address/text()"; //intreaga adresa
        String queryLocalitate = "//GeocodeResponse[status='OK']/result[type='route']/address_component[type='locality']/long_name/text()"; //localitatea
      String queryTara = "//GeocodeResponse[status='OK']/result[type='route']/address_component[type='country']/long_name/text()"; //tara
        String queryJudet = "//GeocodeResponse[status='OK']/result[type='route']/address_component[type='administrative_area_level_1']/long_name/text()"; //judetul
       nodes=QueryXml(queryAdresa, XPathConstants.NODESET);
       Point.setAdress(nodes.item(0).getNodeValue());
       nodes=QueryXml(queryLocalitate, XPathConstants.NODESET);
       Point.setCity(nodes.item(0).getNodeValue());          //TODO add validation
       nodes=QueryXml(queryJudet, XPathConstants.NODESET);
       Point.setAdministrativeLevel1(nodes.item(0).getNodeValue());
       nodes=QueryXml(queryTara, XPathConstants.NODESET);
       Point.setCountry(nodes.item(0).getNodeValue());
       
    }
}
