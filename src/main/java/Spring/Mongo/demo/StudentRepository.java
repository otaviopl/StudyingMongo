package Spring.Mongo.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student,String> {
    @Query("{'firstName': ?0}")
    List<Student> findStudentByFirstName(String firstName);
    Optional<Student> findByEmail(String email);

    List<Student> findByFirstNameAndGender(String firstName, Gender gender);

    List<Student> findByFavouriteSubjects(List<String> favouriteSubjects);

    List<Student> findStudentByGenderAndFavouriteSubjects (Gender gender, List<String> favouritesubjects);
}










