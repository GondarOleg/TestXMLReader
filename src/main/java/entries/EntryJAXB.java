package entries;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "entries.Entry")
public class EntryJAXB {
    private int id;
    private String content;
    private Date creationDate;

    public int getId() {
        return id;
    }

    public String getContent() {

        return content;
    }

    @XmlElement
    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @XmlElement
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }
}

