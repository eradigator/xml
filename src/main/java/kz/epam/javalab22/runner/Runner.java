package kz.epam.javalab22.runner;

import kz.epam.javalab22.entity.database.BankDatabase;
import kz.epam.javalab22.parser.BankSAXParser;

/**
 * Created by Erad
 */
public class Runner {

    private static final String pathToFile = "src/main/xml/bankAccount.xml";

    public static void main(String[] args) {

        BankDatabase database1 = new BankSAXParser().parseXMLtoObjects(pathToFile);
        database1.printToScreen();

    }
}
