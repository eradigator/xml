package kz.epam.javalab22.runner;

import kz.epam.javalab22.entity.database.BankDatabase;
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

    public static void main(String[] args) {

        System.out.println(VALIDATOR_RESULT + new ValidatorXML().validate(PATH_TO_XML_FILE,PATH_TO_XSD_FILE));

        BankDatabase database1 = new BankSAXParser().parseXMLtoObjects(PATH_TO_XML_FILE);
        database1.printToScreen();

        BankDatabase database2 = new BankStAXParser().parseXMLtoObjects(PATH_TO_XML_FILE);
        database2.printToScreen();

    }
}
