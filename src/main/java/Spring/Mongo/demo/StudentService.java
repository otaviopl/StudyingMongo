package Spring.Mongo.demo;

import Spring.Mongo.demo.Gender;
import Spring.Mongo.demo.Student;
import Spring.Mongo.demo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class StudentService {

    private static StudentRepository studentRepository;
    private static MongoTemplate mongoTemplate;

    @Autowired
    public StudentService(StudentRepository studentRepository, MongoTemplate mongoTemplate) {
        StudentService.studentRepository = studentRepository;
        StudentService.mongoTemplate = mongoTemplate;
    }

    public static List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public static Student getStudentByNameAndGender(String firstName, Gender gender){
        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is(firstName).and("gender").is(gender));


        List<Student> students = mongoTemplate.find(query, Student.class);

        if (students.size() > 1) {
            throw new IllegalStateException("Found many with these parameters: " + students);
        }

        if (students.isEmpty()) {
            throw new IllegalStateException("No one found with these parameters");
        }

            return students.getFirst();
    }



    public static Student findStudentByNameAndGender() {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Type the name of the student you want:");
            String name = scanner.nextLine();

            System.out.println("Type the gender of the student:");
            Gender gender = Gender.valueOf(scanner.nextLine());

            return getStudentByNameAndGender(name,gender);

    }

    public void existsEmail(String mail, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(mail));
        List<Student> students = mongoTemplate.find(query, Student.class);
        if (students.size() > 1) {
            throw new IllegalStateException("found many with this email." + mail);
        }
        if (students.isEmpty()) {
            System.out.println("Inserting student");
            studentRepository.insert(student);
        } else {
            System.out.println(student + " already exists");
        }
    }
}
