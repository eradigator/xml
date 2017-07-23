package kz.epam.javalab22.validator;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class ValidatorXML {

    private static final String NAMESPACE = "http://www.w3.org/2001/XMLSchema";

    public boolean validate(String pathToXMLFile, String pathToXSDFile) {

        try {
            SchemaFactory factory = SchemaFactory.newInstance(NAMESPACE);
            Schema schema = factory.newSchema(new StreamSource(pathToXSDFile));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathToXMLFile));
            return true;
        } catch (SAXException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
