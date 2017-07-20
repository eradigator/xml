package kz.epam.javalab22.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParser extends DefaultHandler{

    private boolean isBankAccount = false;
    private boolean isBankAccountID = false;
    private boolean isCustomerID = false;
    private boolean isAmount = false;
        private boolean isLimit = false;
    private boolean isDebit = false;
    private String status;




    @Override
    public void startDocument() throws SAXException {
        System.out.println("Started");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //System.out.println("Start element: " + qName);

        //String attrType = attributes.getQName(0);
        //String attrValue = attributes.getValue(0);
        //System.out.println("Type: " + attrType + " Value: " + attrValue);
        status = attributes.getValue(0);

        if (qName.equalsIgnoreCase("bankAccount")) {
            isBankAccount = true;
        }

        if (qName.equalsIgnoreCase("bank:Debit")) {
            isDebit = true;
        }

        if (qName.equalsIgnoreCase("bankAccountID")) {
            isBankAccountID = true;
        }

        if (qName.equalsIgnoreCase("customerID")) {
            isCustomerID = true;
        }

        if (qName.equalsIgnoreCase("amount")) {
            isAmount = true;
        }

        if (qName.equalsIgnoreCase("limit")) {
            isLimit= true;
        }


    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (isBankAccount) {
            System.out.println("Found New Bank Account");
            System.out.println("Status:" + status);
            isBankAccount = false;
        }

        if (isDebit) {
            System.out.println("Type: Debit ");
            isDebit = false;
        }

        if (isBankAccountID) {
            System.out.println("BankAccountID: " + new String(ch, start, length));
            isBankAccountID = false;
        }

        if (isCustomerID) {
            System.out.println("CustomerID: " + new String(ch, start, length));
            isCustomerID = false;
        }

        if (isAmount) {
            System.out.println("Amount: " + new String(ch, start, length));
            isAmount = false;
        }

        if (isLimit) {
            System.out.println("Limit: " + new String(ch, start, length));
            isLimit = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //super.endElement(uri, localName, qName);
        //System.out.println("End element: " + qName);
        if (qName.equalsIgnoreCase("bankAccount")) {
            System.out.println("___________________________________________________________________");
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Ended");
    }
}
