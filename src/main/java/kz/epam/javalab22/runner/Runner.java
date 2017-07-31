package kz.epam.javalab22.runner;

import kz.epam.javalab22.entity.database.BankDatabase;
import kz.epam.javalab22.parser.BankDOMParser;
import kz.epam.javalab22.parser.BankSAXParser;
import kz.epam.javalab22.parser.BankStAXParser;
import kz.epam.javalab22.validator.ValidatorXML;

/**
 * Created by Erad
 * XML Parser
 */
public class Runner {

    private static final String PATH_TO_XML_FILE = "src/main/xml/bankAccount.xml";
    private static final String PATH_TO_XSD_FILE = "src/main/xml/bank.xsd";
    private static final String VALIDATOR_RESULT = "Validator result: ";
    private static final String SAX = "SAX";
    private static final String STAX = "StAX";
    private static final String DOM = "DOM";
    private static final String EQUALS = "Equals: ";
    private static final String HASHCODES = "HashCodes: ";
    private static final String DIVIDER = ", ";


    public static void main(String[] args) {

        ValidatorXML validator = new ValidatorXML();
        System.out.println(VALIDATOR_RESULT + validator.validate(PATH_TO_XML_FILE, PATH_TO_XSD_FILE));

        BankDatabase database1 = new BankSAXParser().parseXMLtoObjects(PATH_TO_XML_FILE);
        System.out.println(SAX);
        database1.printToScreen();

        BankDatabase database2 = new BankStAXParser().parseXMLtoObjects(PATH_TO_XML_FILE);
        System.out.println(STAX);
        database2.printToScreen();

        BankDatabase database3 = new BankDOMParser().parseXMLtoObjects(PATH_TO_XML_FILE);
        System.out.println(DOM);
        database3.printToScreen();

        System.out.println(EQUALS + (database1.equals(database2) && database2.equals(database3)));

        System.out.println(HASHCODES + database1.hashCode() + DIVIDER + database2.hashCode() + DIVIDER + database3.hashCode());

    }
}
