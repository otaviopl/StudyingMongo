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

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
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
					List.of("Computer Science"),
					BigDecimal.ZERO,
					Date.from(Instant.now())

			);
			//filter using query with regex: Make shure you are using the correct format of 'QUERY'.
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(mail));
			List<Student> students = mongoTemplate.find(query, Student.class);
			if(students.size()>1){
				throw new IllegalStateException("found many with this email."+mail);
			}
			if(students.isEmpty()){
				System.out.println("Inserting student");
				repository.insert(student);
			}else{
				System.out.println(student+"already exists");
			}

		};

	}

}
