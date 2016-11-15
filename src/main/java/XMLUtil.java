import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by O.Gondar on 15.11.2016.
 */
public class XMLUtil {

    public static boolean validateXML(File file) throws IOException {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(classLoader.getResource("entrySchema.xsd").getFile()));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
            System.out.println("Validation is successful");
            return true;
        } catch (SAXException e) {
            System.out.println("Error when validate XML against XSD Schema");
            System.out.println("Message: " + e.getMessage());
            return false;
        }
    }

    public static Entry unmarshal(File file) throws JAXBException, IOException, ParserConfigurationException, SAXException {

       JAXBContext jaxbContext = JAXBContext.newInstance(EntryJAXB.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Entry) jaxbUnmarshaller.unmarshal(file);

    }
}
