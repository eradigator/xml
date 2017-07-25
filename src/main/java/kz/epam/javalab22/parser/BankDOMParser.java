package kz.epam.javalab22.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class BankDOMParser {

    public static void main(String[] args) {
        new BankDOMParser().parseXMLtoObjects();
    }

    public void parseXMLtoObjects() {

        try {
            File fXmlFile = new File("src/main/xml/bankAccount.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("credit");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("status : " + eElement.getAttribute("status"));
                    System.out.println("bankAccountID : " + eElement.getElementsByTagName("bankAccountID").item(0).getTextContent());
                    System.out.println("customerID : " + eElement.getElementsByTagName("customerID").item(0).getTextContent());
                    System.out.println("amount : " + eElement.getElementsByTagName("amount").item(0).getTextContent());
                    //System.out.println("Salary : " + eElement.getElementsByTagName("limit").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
