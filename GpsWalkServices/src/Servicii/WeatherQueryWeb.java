/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicii;

/**
 *
 * @author Revan
 */
import Obj.WeatherInfo;
import Abstract.AbstractXmlQuery;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WeatherQueryWeb extends AbstractXmlQuery {

    private String Url;
    private final WeatherInfo Weather;

    public WeatherQueryWeb() {
        Weather=new WeatherInfo();
    }
    @Override
    public void ProcesQuery(NodeList nodes) {
        if (nodes == null) {
            return;
        }
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
                    Weather.setLocation(nodeVal);
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
    }
    public void PopuleazaWeather() throws Exception
    {
        
        this.QueryXml("/xml_api_reply/weather/forecast_information", XPathConstants.NODESET);
        this.QueryXml("/xml_api_reply/weather/current_conditions", XPathConstants.NODESET);
        
    }

    public WeatherInfo getWeather() {
        return Weather;
    }
     

}
