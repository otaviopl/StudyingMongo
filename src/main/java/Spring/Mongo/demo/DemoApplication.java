package Spring.Mongo.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ExecutableFindOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(StudentRepository repository,StudentService studentService, MongoTemplate mongoTemplate){
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("===== Menu =====");
			System.out.println("1. Create Student");
			System.out.println("2. Read Students");
			System.out.println("3. Update Student");
			System.out.println("4. Delete Student");
			System.out.println("5. Get By First Name and Gender");
			System.out.println("6. Get By Favourite Subjects");
			System.out.println("0. Exit");
			System.out.print("Select an option: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1:
					studentService.createStudent(scanner);
					break;
				case 2:
					studentService.getAllStudents();
					break;
				case 3:
					studentService.updateStudent(scanner);
					break;
				case 4:
					studentService.deleteStudent(scanner);
					break;
				case 5:
					studentService.findStudentByNameAndGender();
					break;
				case 6:
					studentService.findStudentByFavoriteSubjects();
					break;
				case 0:
					System.out.println("Exiting program.");
					scanner.close();
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
	}
};


