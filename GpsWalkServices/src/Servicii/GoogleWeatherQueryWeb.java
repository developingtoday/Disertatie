/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicii;

/**
 *
 * @author Revan
 */
import Obj.GeoInfo;
import Obj.GpsPoint;
import Obj.WeatherInfo;
import Abstract.AbstractXmlQuery;

import javax.xml.xpath.XPathConstants;

import Utils.ServicesFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.Normalizer;

public class GoogleWeatherQueryWeb extends AbstractXmlQuery<WeatherInfo> {




    public GoogleWeatherQueryWeb() {
       super("http://www.google.com/ig/api?weather=");
    }

    private WeatherInfo ProcesQuery(NodeList nodes) {
        if (nodes == null) {
            return new WeatherInfo();
        }
        WeatherInfo Weather=new WeatherInfo();
        Node nod = null;
        Node aux = null;
        NamedNodeMap nmp = null;
        String nodeVal="";
        for (int i = 0; i < nodes.getLength(); i++) {
            nod = nodes.item(i);
            NodeList noduri = nod.getChildNodes();
            for (int j = 0; j < noduri.getLength(); j++) {

                aux = noduri.item(j);
                if (aux.getNodeName().equals("temp_f")) {
                    continue;
                }
                if (aux == null) {
                    continue;
                }
                nmp = aux.getAttributes();
                if (nmp == null) {
                    continue;
                }
                nodeVal=nmp.getNamedItem("data").getNodeValue();
                if(aux.getNodeName().equals("city"))
                {
                    Weather.setCity(nodeVal);
                }
                if(aux.getNodeName().equals("forecast_date")){
                    Weather.setDate(nodeVal);
                }
                if(aux.getNodeName().equals("condition"))
                {
                    Weather.setConditionData(nodeVal);
                }
                if(aux.getNodeName().equals("temp_c"))
                {
                    Weather.setTemp(nodeVal);
                }
                if(aux.getNodeName().equals("humidity"))
                {
                    Weather.setUmidity(nodeVal);
                }
                if(aux.getNodeName().equals("wind_condition"))
                {
                    Weather.setWind(nodeVal);
                }
              
            }
        }
        return Weather;
    }

    @Override
    protected void setupUrlWithPoint(GpsPoint point){
        try{
            String p= ServicesFactory.getReverseGeocodeService().getInfoFromPoint(point).getCity();
            String test= Normalizer.normalize(p, Normalizer.Form.NFD);
            String testCity=test.replaceAll("[^\\p{ASCII}]", "") ;
            Url+=testCity+"&hl=ro&oe=ISO-8859-1";
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    protected WeatherInfo populeaza() throws Exception{
        WeatherInfo w=new WeatherInfo();
       String city= ProcesQuery(this.queryXml("/xml_api_reply/weather/forecast_information", XPathConstants.NODESET)).getCity();
        w=ProcesQuery(this.queryXml("/xml_api_reply/weather/current_conditions", XPathConstants.NODESET));
        w.setCity(city);
        return w;
    }





     

}
