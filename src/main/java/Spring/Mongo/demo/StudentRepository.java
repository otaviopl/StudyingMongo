package Spring.Mongo.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student,String> {
    Optional<Student> findByEmail(String email);

    List<Student> findByFirstNameAndGender(String firstName, Gender gender);

    List<Student> findByFavouriteSubjects(List<String> favouriteSubjects);
}










