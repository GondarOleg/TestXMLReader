import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLUtil {
    final static Logger logger = Logger.getLogger(XMLUtil.class);

    public static boolean validateXML(File file) throws IOException, SAXException {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(classLoader.getResource("entrySchema.xsd").getFile()));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
            logger.info("Validation is successful");
            return true;

    }

}
