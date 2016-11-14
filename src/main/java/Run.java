import org.hibernate.Session;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by O.Gondar on 14.11.2016.
 */
public class Run {
    public static void main(String[] args) throws IOException{
        validateXML();
        writeDataToDB();
    }




    public static void validateXML() throws IOException {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(classLoader.getResource("entrySchema.xsd").getFile()));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(classLoader.getResource("test.xml").getFile()));
            System.out.println("Validation is successful");
        } catch (SAXException e) {
            System.out.println("Error when validate XML against XSD Schema");
            System.out.println("Message: " + e.getMessage());
        }
    }

    public static void writeDataToDB(){
        Entry entry = new Entry();
        entry.setContent("test");
        entry.setCreationDate(Calendar.getInstance().getTime());
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(entry);
        session.getTransaction().commit();
        session.close();
        System.out.println("Done");
    }

}
