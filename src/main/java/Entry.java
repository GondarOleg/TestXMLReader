import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Length(max = 1024)
    @Column(name = "content")
    private String content;

    @Column(name = "creationDate")
    @Type(type = "timestamp")
    private Date creationDate;

    public int getId() {
        return id;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }
}

