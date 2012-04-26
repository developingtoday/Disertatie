/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Abstract;

/**
 *
 * @author Revan
 */
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
public abstract class AbstractXmlQuery {
           private String Url;
           private String Key;

    public void setKey(String Key) {
        this.Key = Key;
    }


   protected NodeList QueryXml(String query, QName qret) throws ParserConfigurationException, SAXException,
            IOException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        XPathExpression expr = null;
        builder = factory.newDocumentBuilder();
        doc = builder.parse(Url);
        XPathFactory xFactory = XPathFactory.newInstance();
        XPath xpath = xFactory.newXPath();
        //expr = xpath.compile("/xml_api_reply/weather/forecast_information");
        expr = xpath.compile(query);
        Object result = expr.evaluate(doc, qret);
        NodeList nodes = (NodeList) result;
        ProcesQuery(nodes);
        return nodes;

    }
   protected abstract void  ProcesQuery(NodeList nodes);
       public String getUrl() {

        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }
     
}
