// How to get a
// String value = xPath.compile(expression).evaluate(xmlDocument);
// Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
// NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

package xml.Parser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXMLFile {

	public static void main(String[] args) {

		try {

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			builder = builderFactory.newDocumentBuilder();
			Document sourceDOM = builder.parse( new FileInputStream("Admin.profile"));
			Document targetDOM = builder.parse( new FileInputStream("Adminew.profile"));
			XPath xPath =  XPathFactory.newInstance().newXPath();
			String profile = "/Profile";
			String layoutAssignments = "/layoutAssignments";
			
			Node targetProfile = (Node) xPath.compile(profile).evaluate(targetDOM, XPathConstants.NODE);
			NodeList targetNodeList = (NodeList) xPath.compile(profile+layoutAssignments).evaluate(targetDOM, XPathConstants.NODESET);
			NodeList sourceNodeList = (NodeList) xPath.compile(profile+layoutAssignments).evaluate(sourceDOM, XPathConstants.NODESET);
			System.out.println(sourceNodeList.getLength());
			System.out.println(targetNodeList.getLength());
			System.out.println(sourceNodeList.item(0).getFirstChild().getNextSibling().getFirstChild().getNodeValue());
			
			for (int i = 0,j = 0; i < sourceNodeList.getLength() || j < targetNodeList.getLength(); i++,j++) {
			    if(sourceNodeList.item(i).getFirstChild().getNextSibling().getFirstChild().getNodeValue().equals(targetNodeList.item(j).getFirstChild().getNextSibling().getFirstChild().getNodeValue()))
			    	System.out.println(sourceNodeList.item(i).getFirstChild().getNextSibling().getFirstChild().getNodeValue()+
			    			" <=> "+
			    			targetNodeList.item(j).getFirstChild().getNextSibling().getFirstChild().getNodeValue());
			    else{
			    	System.out.println("\tcopy here!");
			    	targetProfile.insertBefore(targetDOM.adoptNode(sourceNodeList.item(i).cloneNode(true)), targetNodeList.item(j));
			    	j--;
			    }
			}
			
			//write to target file
			TransformerFactory.newInstance().newTransformer().transform(
				    new DOMSource(targetDOM),
				    new StreamResult(new FileOutputStream("Adminew.profile"))
				);
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