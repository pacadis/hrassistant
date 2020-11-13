package hr.services.rest;

import hr.model.User;
import hr.persistance.hibernate.CompanyRepository;
import hr.persistance.hibernate.EmployeeRepository;
import hr.persistance.hibernate.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("")
public class RestServices {

    private final UserRepository userRepository = new UserRepository();
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final CompanyRepository companyRepository = new CompanyRepository();

    @GetMapping("/user")
    public ResponseEntity<?> getUsers() {
        List<User> list = userRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userRepository.findOne(userId), HttpStatus.OK);
    }

    @PostMapping("/user")
    public void saveUser(@RequestBody User user) {
        System.out.println(user);
        user.setId("20");
    }

    @PutMapping("/user/{userId}")
    public void updateUser(@RequestBody User user, @PathVariable("userId") String userId) {
        userRepository.update(userId, user);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {
        userRepository.delete(userId);
    }
}
