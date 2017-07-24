package kz.epam.javalab22.parser;

import kz.epam.javalab22.entity.bankAccount.BankAccountStatus;
import kz.epam.javalab22.entity.bankAccount.Credit;
import kz.epam.javalab22.entity.bankAccount.Debit;
import kz.epam.javalab22.entity.database.BankDatabase;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class BankStAXParser {

    private static BankDatabase bankDatabase = new BankDatabase();

    private static final String ACTIVE = "ACTIVE";
    private static final String PAUSED = "PAUSED";
    private static final String CLOSED = "CLOSED";
    private static final String CREDIT = "credit";
    private static final String DEBIT = "debit";
    private static final String BANK_ACCOUNT_ID = "bankAccountID";
    private static final String CUSTOMER_ID = "customerID";
    private static final String AMOUNT = "amount";
    private static final String LIMIT = "limit";

    private boolean isBankAccountID = false;
    private boolean isCustomerID = false;
    private boolean isAmount = false;
    private boolean isLimit = false;

    private int bankAccountID;
    private long customerID;
    private double amount;
    private double limit;
    private BankAccountStatus status;


    public BankDatabase parseXMLtoObjects(String pathToXMLFile) {

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(pathToXMLFile));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();

                        if (qName.equalsIgnoreCase(DEBIT) || qName.equalsIgnoreCase(CREDIT)) {

                            Iterator<Attribute> attributes = startElement.getAttributes();
                            String attributeValue = attributes.next().getValue();
                            switch (attributeValue.toUpperCase()) {
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

                        } else if (qName.equalsIgnoreCase(BANK_ACCOUNT_ID)) {
                            isBankAccountID = true;
                        } else if (qName.equalsIgnoreCase(CUSTOMER_ID)) {
                            isCustomerID = true;
                        } else if (qName.equalsIgnoreCase(AMOUNT)) {
                            isAmount = true;
                        } else if (qName.equalsIgnoreCase(LIMIT)) {
                            isLimit = true;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (isBankAccountID) {
                            bankAccountID = Integer.parseInt(characters.getData());
                            isBankAccountID = false;
                        }
                        if (isCustomerID) {
                            customerID = Long.parseLong(characters.getData());
                            isCustomerID = false;
                        }
                        if (isAmount) {
                            amount = Double.parseDouble(characters.getData());
                            isAmount = false;
                        }
                        if (isLimit) {
                            limit = Double.parseDouble(characters.getData());
                            isLimit = false;
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();

                        switch (endElement.getName().getLocalPart()) {
                            case CREDIT:
                                bankDatabase.getDatabase().add(new Credit(bankAccountID, customerID, amount, status, limit));
                                break;
                            case DEBIT:
                                bankDatabase.getDatabase().add(new Debit(bankAccountID, customerID, amount, status));
                                break;
                        }

                        break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        return bankDatabase;

    }
}
