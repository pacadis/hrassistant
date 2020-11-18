package Server;


import Model.Company;
import Model.Employee;
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
