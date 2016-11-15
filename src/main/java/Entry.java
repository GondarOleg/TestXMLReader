import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@Entity
@Table(name="Entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Length(max = 1024)
    @Column(name = "content")
    private String content;

    @Column(name = "creationDate")
    @Type(type="timestamp")
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

