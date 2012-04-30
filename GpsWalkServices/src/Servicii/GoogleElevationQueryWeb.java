package Servicii;

import Abstract.AbstractXmlQuery;
import Obj.GpsPoint;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;

/**
 * Created by IntelliJ IDEA.
 * User: Revan
 * Date: 2/13/12
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoogleElevationQueryWeb extends AbstractXmlQuery<GpsPoint> {




    public GoogleElevationQueryWeb()
    {
        super("https://maps.googleapis.com/maps/api/elevation/xml?locations=");
    }



    @Override
    protected void setupUrlWithPoint(GpsPoint point) {
        if(point==null) return;
         Url+=point.getLatitude()+","+point.getLongitude()+"&sensor=true";
    }

    @Override
    protected GpsPoint populeaza() throws Exception {
        GpsPoint p=new GpsPoint();
        String queryElevation="//ElevationResponse[status='OK']/result/elevation/text()";
        NodeList nodes;
        nodes=queryXml(queryElevation, XPathConstants.NODESET);
        if(nodes.getLength()==0) return p;
        p.setAltitude(Double.parseDouble(nodes.item(0).getNodeValue()));
        return p;
    }



}
