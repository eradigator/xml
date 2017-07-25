package kz.epam.javalab22.parser;

import kz.epam.javalab22.entity.bankAccount.BankAccountStatus;
import kz.epam.javalab22.entity.bankAccount.Credit;
import kz.epam.javalab22.entity.bankAccount.Debit;
import kz.epam.javalab22.entity.database.BankDatabase;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
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
    private static final String BLANK_STRING = "";

    private int bankAccountID;
    private long customerID;
    private double amount;
    private double limit;
    private BankAccountStatus status;

    private String elementName;

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
                        elementName = qName;

                        if (DEBIT.equals(qName) || CREDIT.equals(qName)) {
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
                        }

                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();

                        if (!BLANK_STRING.equals(characters.getData().trim())) {
                            switch (elementName) {
                                case BANK_ACCOUNT_ID:
                                    bankAccountID = Integer.parseInt(characters.getData());
                                    break;
                                case CUSTOMER_ID:
                                    customerID = Long.parseLong(characters.getData());
                                    break;
                                case AMOUNT:
                                    amount = Double.parseDouble(characters.getData());
                                    break;
                                case LIMIT:
                                    limit = Double.parseDouble(characters.getData());
                                    break;
                            }
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
