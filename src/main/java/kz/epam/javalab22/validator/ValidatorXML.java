package kz.epam.javalab22.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class ValidatorXML {

    public boolean validate(String pathToXMLFile, String pathToXSDFile) {

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(pathToXSDFile));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathToXMLFile));
            return true;
        } catch (SAXException | IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
