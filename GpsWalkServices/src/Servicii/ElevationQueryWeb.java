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
public class ElevationQueryWeb extends AbstractXmlQuery {

    private String TempUrl="https://maps.googleapis.com/maps/api/elevation/xml?locations=";

    private GpsPoint point;

    public void setPoint(GpsPoint value)
    {
        point=value;
    }
    public GpsPoint getPoint()
    {
        return point;
    }

    public void SetUrlWithPoint()
    {
       if(point==null) return;
       TempUrl+=point.getLatitude()+","+point.getLongitude()+"&sensor=true";
       setUrl(TempUrl);
    }
    public void Popupleaza()  throws Exception
    {
        SetUrlWithPoint();
        String queryElevation="//ElevationResponse[status='OK']/result/elevation/text()";
        NodeList nodes;
        nodes=QueryXml(queryElevation, XPathConstants.NODESET);
        if(nodes.getLength()==0) return;
        point.setAltitude(Double.parseDouble(nodes.item(0).getNodeValue()));

    }

    @Override
    public void ProcesQuery(NodeList nodes) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
