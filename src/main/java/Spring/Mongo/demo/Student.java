package Spring.Mongo.demo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Student {

    @Id
    private String id;

    private String firstName;

    private String LastName;

    @Indexed(unique = true)
    private String email;

    private Gender gender;

    private Adress adress;

    private List<String> favouriteSubjects;

    private BigDecimal totalSpentInBooks;

    private Date created;

    public Student(String firstName, String lastName, String email, Gender gender, Adress adress, List<String> favouriteSubjects, BigDecimal totalSpentInBooks, Date created) {
        this.firstName = firstName;
        LastName = lastName;
        this.email = email;
        this.gender = gender;
        this.adress = adress;
        this.favouriteSubjects = favouriteSubjects;
        this.totalSpentInBooks = totalSpentInBooks;
        this.created = created;
    }



}
