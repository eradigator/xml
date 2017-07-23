package kz.epam.javalab22.parser;

import kz.epam.javalab22.entity.bankAccount.BankAccountStatus;
import kz.epam.javalab22.entity.bankAccount.Credit;
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

    private static boolean isBankAccountID = false;
    private static boolean isCustomerID = false;
    private static boolean isAmount = false;
    private static boolean isLimit = false;

    private static int bankAccountID;
    private static long customerID;
    private static double amount;
    private static double limit;
    private static BankAccountStatus status;

    public static void main(String[] args) {


        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader("src/main/xml/bankAccount.xml"));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();

                        if (qName.equalsIgnoreCase(DEBIT) || qName.equalsIgnoreCase(CREDIT)) {
                            System.out.println("Start Element : bankAccount");

                            Iterator<Attribute> attributes = startElement.getAttributes();
                            String attributeValue = attributes.next().getValue();

                            if (attributeValue.equalsIgnoreCase(ACTIVE)) {
                                status = BankAccountStatus.ACTIVE;
                            } else if (attributeValue.equalsIgnoreCase(PAUSED)) {
                                status = BankAccountStatus.PAUSED;
                            } else if (attributeValue.equalsIgnoreCase(CLOSED)) {
                                status = BankAccountStatus.CLOSED;
                            }

                            System.out.println("Status : " + status);

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
                            System.out.println("BankAccount ID: " + characters.getData());
                            bankAccountID = Integer.parseInt(characters.getData());
                            isBankAccountID = false;
                        }
                        if (isCustomerID) {
                            System.out.println("CustomerID: " + characters.getData());
                            isCustomerID = false;
                        }
                        if (isAmount) {
                            System.out.println("Amount: " + characters.getData());
                            isAmount = false;
                        }
                        if (isLimit) {
                            System.out.println("Limit: " + characters.getData());
                            isLimit = false;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equalsIgnoreCase(CREDIT)) {
                            System.out.println("End Element : bankAccount");
                            bankDatabase.getDatabase().add(new Credit(bankAccountID, customerID, amount, status, limit));
                            System.out.println();
                        }
                        break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        bankDatabase.printToScreen();
    }
}
