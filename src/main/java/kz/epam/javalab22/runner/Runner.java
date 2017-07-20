package kz.epam.javalab22.runner;

import kz.epam.javalab22.parser.SAXParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Runner
{
    public static void main( String[] args ) throws IOException, SAXException, ParserConfigurationException{

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(false);

        javax.xml.parsers.SAXParser sp = spf.newSAXParser();

        SAXParser handler = new SAXParser();
        sp.parse(new File("src/main/xml/bankAccount.xml"), handler);

    }
}
