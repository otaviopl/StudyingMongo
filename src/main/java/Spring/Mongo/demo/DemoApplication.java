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

		Student studentTest = StudentService.findStudentByNameAndGender();
		System.out.println(studentTest);

	}
	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate){
		return args -> {
			Adress adress = new Adress(
					"Brasil",
					"SC",
					"13456");
			String mail = "otavio.lopes@gmail.com";
			Student student = new Student(
					"Otavio",
					"Lopes",
					mail,
					Gender.Male,
					adress,
					List.of("Computer Science","Math"),
					BigDecimal.ZERO,
					Date.from(Instant.now())
			);
			repository.findStudentByEmail(mail).ifPresentOrElse(s->{
				System.out.println(student+" already exists.");
			},()->{
				System.out.println("Inserting student "+student);
				repository.insert(student);
			});
		};
	}
}
