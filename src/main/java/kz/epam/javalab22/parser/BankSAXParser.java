package kz.epam.javalab22.parser;

import kz.epam.javalab22.entity.bankAccount.BankAccountStatus;
import kz.epam.javalab22.entity.bankAccount.Credit;
import kz.epam.javalab22.entity.bankAccount.Debit;
import kz.epam.javalab22.entity.database.BankDatabase;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class BankSAXParser extends DefaultHandler {

    private BankDatabase bankDatabase = new BankDatabase();

    private static final String STATUS = "status";
    private static final String ACTIVE = "ACTIVE";
    private static final String PAUSED = "PAUSED";
    private static final String CLOSED = "CLOSED";
    private static final String CREDIT = "credit";
    private static final String DEBIT = "debit";
    private static final String BANK_ACCOUNT_ID = "bankAccountID";
    private static final String CUSTOMER_ID = "customerID";
    private static final String AMOUNT = "amount";
    private static final String LIMIT = "limit";
    private static final String BLANK_STRING = "";

    private int bankAccountID;
    private long customerID;
    private double amount;
    private double limit;
    private BankAccountStatus status;

    private String elementName;


    public BankDatabase parseXMLtoObjects(String pathToXMLFile) {

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(false);
            SAXParser sp = spf.newSAXParser();
            sp.parse(new File(pathToXMLFile), this);
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            ex.printStackTrace();
        }

        return bankDatabase;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        elementName = qName;

        for (int i = 0; i < attributes.getLength(); i++) {
            if (STATUS.equalsIgnoreCase(attributes.getQName(i))) {
                {
                    switch (attributes.getValue(i).toUpperCase()) {
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
                }
            }
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (!BLANK_STRING.equals(String.valueOf(ch, start, length).trim())) {
            switch (elementName) {
                case BANK_ACCOUNT_ID:
                    bankAccountID = Integer.parseInt(new String(ch, start, length));
                    break;
                case CUSTOMER_ID:
                    customerID = Long.parseLong(new String(ch, start, length));
                    break;
                case AMOUNT:
                    amount = Double.parseDouble(new String(ch, start, length));
                    break;
                case LIMIT:
                    limit = Double.parseDouble(new String(ch, start, length));
                    break;
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        switch (qName) {
            case CREDIT:
                bankDatabase.getDatabase().add(new Credit(bankAccountID, customerID, amount, status, limit));
                break;
            case DEBIT:
                bankDatabase.getDatabase().add(new Debit(bankAccountID, customerID, amount, status));
                break;
        }

    }

}