package Spring.Mongo.demo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class StudentService {
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
    private final StudentRepository studentRepository;
}
