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

import Obj.GpsPoint;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public abstract class AbstractXmlQuery<T extends GpsPoint> {

          protected   String Url;
           protected String auxBaseUrl;

    public AbstractXmlQuery(String baseUrl)
    {
        Url=baseUrl;
        auxBaseUrl=baseUrl;
    }

   private Document getDocument() throws Exception
   {
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       factory.setNamespaceAware(true);
       DocumentBuilder builder;
       Document doc = null;
       builder = factory.newDocumentBuilder();
       doc = builder.parse(Url);
       return doc;
   }

   protected NodeList queryXml(String query, QName qret) throws Exception{

       XPathExpression expr = null;
        XPathFactory xFactory = XPathFactory.newInstance();
        XPath xpath = xFactory.newXPath();
        expr = xpath.compile(query);
        Object result = expr.evaluate(getDocument(), qret);
        NodeList nodes = (NodeList) result;
        return nodes;

    }

    protected abstract void setupUrlWithPoint(GpsPoint point) throws Exception;

    protected abstract T populeaza() throws Exception;


    public T getInfoFromPoint(GpsPoint point) throws Exception
    {
        setupUrlWithPoint(point);
        T p=populeaza();
        Url=auxBaseUrl;
        return p;
    }


     
}
