package Server;


import Model.Company;
import Model.Employee;
import Model.User;
import Repository.CompanyRepository;
import Repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/hr")
public class RestServices {

    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final CompanyRepository companyRepository = new CompanyRepository();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        Employee employee = employeeRepository.findOne(username);
        Company company = companyRepository.findOne(username);
        if (employee != null) {
            if (employee.getPassword().equals(password))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (company != null) {
            if (company.getPassword().equals(password))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Company company) {
        company.setId(company.getUsername() + company.getPassword());
        Employee employee = employeeRepository.findOne(company.getUsername());
        Company companyFind = companyRepository.findOne(company.getUsername());
        if (companyFind == null && employee == null) {
            companyRepository.save(company);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody Employee employee) {
        employee.setId(employee.getUsername() + employee.getPassword());
        System.out.println(employee.getUsername());
        Employee employee1 = employeeRepository.findOne(employee.getUsername());
        Company companyFind = companyRepository.findOne(employee.getUsername());
        if (companyFind == null && employee1 == null) {
            employeeRepository.save(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    // EmployeeServices
    @GetMapping("/employee")
    public ResponseEntity<?> getEmployees() {
        List<Employee> list = employeeRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getEmployee(@PathVariable("employeeId") String employeeId) {
        return new ResponseEntity<>(employeeRepository.findOne(employeeId), HttpStatus.OK);
    }

    @PostMapping("/employee")
    public void saveEmployee(@RequestBody Employee employee) {
        System.out.println(employee);
        //generare id
        //asdasd124542raczxc123
        employee.setId("20");
        employeeRepository.save(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee, @PathVariable("employeeId") String employeeId) {
        employee.setId(employeeId);
        employeeRepository.update(employee);
    }

    @DeleteMapping("/employee/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") String employeeId) {
        employeeRepository.delete(employeeRepository.findOne(employeeId));
    }


    // CompanyServices
    @GetMapping("/company")
    public ResponseEntity<?> getCompanys() {
        List<Company> list = companyRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getCompany(@PathVariable("companyId") String companyId) {
        return new ResponseEntity<>(companyRepository.findOne(companyId), HttpStatus.OK);
    }

    @PostMapping("/company")
    public void saveCompany(@RequestBody Company company) {
        System.out.println(company);
        //generare id
        //asdasd124542raczxc123
        company.setId("20");
        companyRepository.save(company);
    }

    @PutMapping("/company/{companyId}")
    public void updateCompany(@RequestBody Company company, @PathVariable("companyId") String companyId) {
        company.setId(companyId);
        companyRepository.update(company);
    }

    @DeleteMapping("/company/{companyId}")
    public void deleteCompany(@PathVariable("companyId") String companyId) {
        companyRepository.delete(companyRepository.findOne(companyId));
    }
}
