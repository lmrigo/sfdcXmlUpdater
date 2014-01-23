package xml.Parser;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXMLFile {

	public static void main(String[] args) {

		try {

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse( new FileInputStream("Admin.profile"));
			Document newXmlDocument = builder.parse( new FileInputStream("Adminew.profile"));
			XPath xPath =  XPathFactory.newInstance().newXPath();
			String layoutAssignments = "/Profile/layoutAssignments";
			String layout = "/layout";
			String recordType = "/recordType";
			 
			//String email = xPath.compile(expression).evaluate(xmlDocument);
			//Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
			
			NodeList delNodeList = (NodeList) xPath.compile(layoutAssignments+layout).evaluate(newXmlDocument, XPathConstants.NODESET);
			
			NodeList nodeList = (NodeList) xPath.compile(layoutAssignments+layout).evaluate(xmlDocument, XPathConstants.NODESET);
			System.out.println(nodeList.getLength());
			for (int i = 0,j = 0; i < nodeList.getLength() || j < delNodeList.getLength(); i++,j++) {
			    if(nodeList.item(i).getFirstChild().getNodeValue().equals(delNodeList.item(j).getFirstChild().getNodeValue()))
			    	System.out.println(nodeList.item(i).getFirstChild().getNodeValue()+" <=> "+delNodeList.item(j).getFirstChild().getNodeValue());
			    else{
			    	Node n = nodeList.item(i).getFirstChild().cloneNode(true);
			    	newXmlDocument.appendChild(n);
			    }
//			    newXmlDocument.replaceChild(nodeList.item(i).getFirstChild(), delNodeList.item(i).getFirstChild());
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace(); 
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}