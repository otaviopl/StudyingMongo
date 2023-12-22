package Spring.Mongo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Service
public class StudentService {

    private  final StudentRepository studentRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public StudentService(StudentRepository studentRepository, MongoTemplate mongoTemplate) {
        this.studentRepository = studentRepository;
        this.mongoTemplate = mongoTemplate;
    }

public List<Student> findStudentByFirstName  (String firstName){
    System.out.println("estudante:"+studentRepository.findStudentByFirstName(firstName));
        return studentRepository.findStudentByFirstName(firstName);
    }
    public  void createStudent(Scanner scanner) {
        System.out.println("===== Create Student =====");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter gender (Male/Female): ");
        Gender gender = Gender.valueOf(scanner.nextLine());
        System.out.print("Enter country: ");
        String country = scanner.nextLine();
        System.out.print("Enter state: ");
        String state = scanner.nextLine();
        System.out.print("Enter zip code: ");
        String zipCode = scanner.nextLine();
        Adress address = new Adress(country, state, zipCode);
        System.out.print("Enter favorite subjects (comma-separated): ");
        List<String> favoriteSubjects = List.of(scanner.nextLine().split(","));
        System.out.print("Enter total spent in books: ");
        BigDecimal totalSpentInBooks = scanner.nextBigDecimal();
        scanner.nextLine();  // Consumir a quebra de linha após o próximo BigDecimal

        Student newStudent = new Student(
                firstName,
                lastName,
                email,
                gender,
                address,
                favoriteSubjects,
                totalSpentInBooks,
                Date.from(Instant.now())
        );
        insertStudent(newStudent,email);}
    public  void insertStudent(Student newStudent, String email){
        studentRepository.findByEmail(email).ifPresentOrElse(
                existingStudent -> System.out.println("Student already exists: " + existingStudent),
                () -> {
                    studentRepository.insert(newStudent);
                    System.out.println("Student created: " + newStudent);});}


    public  void deleteStudent(Scanner scanner) {
        System.out.println("===== Delete Student =====");
        System.out.print("Enter email of the student to delete: ");
        String emailToDelete = scanner.nextLine();

        studentRepository.findByEmail(emailToDelete).ifPresentOrElse(
                existingStudent -> {
                    System.out.println("Deleting student:");
                    System.out.println(existingStudent);

                    studentRepository.delete(existingStudent);
                    System.out.println("Student deleted.");
                },
                () -> System.out.println("No student found with email: " + emailToDelete));
    }

    public List<Student> findStudentByIntervalo(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the first value:");
        BigDecimal FirstValue = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
        System.out.println("Type the first value:");
        BigDecimal SecondValue = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));


        Query query = new Query();
        query.addCriteria(Criteria.where("totalSpentInBooks").gte(new BigDecimal(String.valueOf(FirstValue))).lte(new BigDecimal(String.valueOf(SecondValue))));
        return mongoTemplate.find(query, Student.class);
    }

    public  void updateStudent(Scanner scanner) {
        System.out.println("===== Update Student =====");
        System.out.print("Enter email of the student to update: ");
        String emailToUpdate = scanner.nextLine();

        studentRepository.findByEmail(emailToUpdate).ifPresentOrElse(
                existingStudent -> {
                    System.out.println("Current details of the student:");
                    System.out.println(existingStudent);


                    System.out.print("Enter new first name: ");
                    String newFirstName = scanner.nextLine();

                    existingStudent.setFirstName(newFirstName);

                    studentRepository.save(existingStudent);
                    System.out.println("Student updated: " + existingStudent);
                },
                () -> System.out.println("No student found with email: " + emailToUpdate)
        );
    }


    public Student findStudentByGenderAndSubject(){
        Scanner scanner = new Scanner(System.in);


        System.out.println("Type the subject you want, press '/' to stop:");

        List<String> favouriteSubjects =  new ArrayList<>();
        while (true) {
            String subject = scanner.nextLine();

            if ("/".equals(subject)) {
                break;}
            favouriteSubjects.add(subject);
        }
        System.out.println("Type the gender you want to search:");
        Gender gender = Gender.valueOf(scanner.nextLine());
        System.out.println("Subjects selected: " + favouriteSubjects);


        return getStudentByGenderAndFavouriteSubjects(gender,favouriteSubjects);
    }
    public Student getStudentByGenderAndFavouriteSubjects(Gender gender,List<String> favouritesubjects){
        System.out.println("All students:"+studentRepository.findStudentByGenderAndFavouriteSubjects(gender,favouritesubjects));
        return studentRepository.findStudentByGenderAndFavouriteSubjects(gender, favouritesubjects).stream().findFirst().
                orElseThrow(() -> new IllegalStateException("No one found with these parameters"));
    }

    public List<Student> getAllStudents() {
        System.out.println("All students:"+ studentRepository.findAll());
        return studentRepository.findAll();
    }

    public Student getStudentByNameAndGender(String firstName, Gender gender) {
        System.out.println("All students:"+ studentRepository.findByFirstNameAndGender(firstName, gender));
        return studentRepository.findByFirstNameAndGender(firstName, gender).stream().findFirst().
                orElseThrow(() -> new IllegalStateException("No one found with these parameters"));

    }

    public Student getStudentByFavoriteSubjects(List<String> favouriteSubjects) {
        System.out.println("All students:"+ studentRepository.findByFavouriteSubjects(favouriteSubjects));
        return studentRepository.findByFavouriteSubjects(favouriteSubjects).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("No one found with these parameters"));
    }

    public Student findStudentByFavoriteSubjects() {
        Scanner scanner = new Scanner(System.in);
        List<String> favouriteSubjects = new ArrayList<>();

        System.out.println("Type the subject you want, press '/' to stop:");

        while (true) {
            String subject = scanner.nextLine();

            if ("/".equals(subject)) {
                break;
            }
            favouriteSubjects.add(subject);
        }
        System.out.println("Subjects selected: " + favouriteSubjects);

        return getStudentByFavoriteSubjects(favouriteSubjects);
    }

    public Student findStudentByNameAndGender() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the name of the student you want:");
        String name = scanner.nextLine();

        System.out.println("Type the gender of the student:");
        Gender gender = Gender.valueOf(scanner.nextLine());

        return getStudentByNameAndGender(name, gender);
    }

    public void existsEmail(String mail, Student student) {
        Optional<Student> existingStudent = studentRepository.findByEmail(mail);
        if (existingStudent.isEmpty()) {
            System.out.println("Inserting student");
            studentRepository.insert(student);
        } else {
            System.out.println(student + " already exists");
        }
    }
}
