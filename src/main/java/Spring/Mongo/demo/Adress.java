package Spring.Mongo.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Adress {
    private String country;
    private String city;
    private  String postCode;
}
