import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
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
            System.out.println("d:/test/invalid/" + file.getName());
            file.renameTo(new File("d:/test/invalid/" + file.getName()));
            return false;
        }
    }

}
