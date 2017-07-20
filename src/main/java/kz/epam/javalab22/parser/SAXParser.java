package kz.epam.javalab22.parser;

import kz.epam.javalab22.entity.bankAccount.BankAccount;
import kz.epam.javalab22.entity.bankAccount.Credit;
import kz.epam.javalab22.entity.database.BankDatabase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParser extends DefaultHandler {

    private boolean isBankAccount = false;
    private boolean isBankAccountID = false;
    private boolean isCustomerID = false;
    private boolean isAmount = false;
    private boolean isLimit = false;
    private boolean isDebit = false;
    private boolean isCredit = false;

    private int bankAccountID;

    BankDatabase database = new BankDatabase();


    @Override
    public void startDocument() throws SAXException {
        System.out.println("Started");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String attrType = attributes.getQName(i);
            String attrValue = attributes.getValue(i);
            if (attrType.equalsIgnoreCase("status")) {
                System.out.println("status: " + attrValue);
            }
        }

        if (qName.equalsIgnoreCase("bankAccount")) {
            isBankAccount = true;
        }

        if (qName.equalsIgnoreCase("bank:Debit")) {
            isDebit = true;
        }

        if (qName.equalsIgnoreCase("bank:Credit")) {
            isCredit = true;
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
            isLimit = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (isBankAccount) {
            System.out.println("Found New Bank Account");
            isBankAccount = false;
        }

        if (isDebit) {
            System.out.println("Type: DEBIT ");
            isDebit = false;
        }

        if (isCredit) {
            System.out.println("Type: CREDIT");
            isCredit = false;
        }

        if (isBankAccountID) {
            System.out.println("BankAccountID: " + new String(ch, start, length));
            isBankAccountID = false;
            bankAccountID = Integer.parseInt(new String(ch,start,length));
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

        if (qName.equalsIgnoreCase("bank:Credit")) {
            System.out.println("Тут надо создать объект Credit");
            System.out.println("Сохраненный в переменную bankAccountID: " + bankAccountID);

            database.getDatabase().add(new Credit());
        }

        if (qName.equalsIgnoreCase("bank:Debit")) {
            System.out.println("Тут надо создать объект Debit");
            System.out.println("Сохраненный в переменную bankAccountID: " + bankAccountID);
        }

        if (qName.equalsIgnoreCase("bankAccount")) {
            System.out.println("___________________________________________________________________");
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Ended");

        for (BankAccount ba : database.getDatabase()) {
            System.out.println(ba);
        }

    }
}
