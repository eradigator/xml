package kz.epam.javalab22.parser;

import kz.epam.javalab22.entity.bankAccount.BankAccountStatus;
import kz.epam.javalab22.entity.bankAccount.Credit;
import kz.epam.javalab22.entity.bankAccount.Debit;
import kz.epam.javalab22.entity.database.BankDatabase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class BankDOMParser {

    private BankDatabase bankDatabase = new BankDatabase();

    private static final String STATUS = "status";
    private static final String ACTIVE = "ACTIVE";
    private static final String PAUSED = "PAUSED";
    private static final String CLOSED = "CLOSED";
    private static final String CREDIT = "credit";
    private static final String DEBIT = "debit";
    private static final String BANK_ACCOUNT = "bankAccount";
    private static final String BANK_ACCOUNT_ID = "bankAccountID";
    private static final String CUSTOMER_ID = "customerID";
    private static final String AMOUNT = "amount";
    private static final String LIMIT = "limit";


    public BankDatabase parseXMLtoObjects(String pathToXMLFile) {

        int bankAccountID;
        long customerID;
        double amount;
        double limit;
        BankAccountStatus status = null;

        try {
            File fXmlFile = new File(pathToXMLFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(BANK_ACCOUNT);

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                NodeList childNodes = nNode.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);

                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) childNode;

                        switch (eElement.getAttribute(STATUS)) {
                            case ACTIVE:
                                status = BankAccountStatus.ACTIVE;
                                break;
                            case PAUSED:
                                status = BankAccountStatus.PAUSED;
                                break;
                            case CLOSED:
                                status = BankAccountStatus.CLOSED;
                                break;
                        }

                        bankAccountID = Integer.parseInt(eElement.getElementsByTagName(BANK_ACCOUNT_ID).item(0).getTextContent());
                        customerID = Long.parseLong(eElement.getElementsByTagName(CUSTOMER_ID).item(0).getTextContent());
                        amount = Double.parseDouble(eElement.getElementsByTagName(AMOUNT).item(0).getTextContent());

                        switch (childNode.getNodeName()) {
                            case CREDIT:
                                limit = Double.parseDouble(eElement.getElementsByTagName(LIMIT).item(0).getTextContent());
                                bankDatabase.getDatabase().add(new Credit(bankAccountID, customerID, amount, status, limit));
                                break;
                            case DEBIT:
                                bankDatabase.getDatabase().add(new Debit(bankAccountID, customerID, amount, status));
                                break;
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bankDatabase;
    }
}
