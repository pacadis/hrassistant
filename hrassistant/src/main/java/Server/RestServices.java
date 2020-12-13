package Server;


import Model.*;
import Model.dtos.ContractDTO;
import Model.dtos.EmployeeAccountDTO;
import Model.dtos.PayslipDTO;
import Model.dtos.RequestDTO;
import Repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/hr")
public class RestServices {

    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final CompanyRepository companyRepository = new CompanyRepository();
    private final RequestRepository requestRepository = new RequestRepository();
    private final ContractRepository contractRepository = new ContractRepository();
    private final PayslipRepository payslipRepository = new PayslipRepository();

    // Service
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
    public ResponseEntity<?> registerCompany(@RequestBody Company company) {
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
        Employee employee1 = employeeRepository.findOne(employee.getUsername());
        if (employee.getCompany()!=employee1.getCompany() || employee1 == null) {
            employeeRepository.save(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/employeeAccounts")
    public ResponseEntity<?> getAllEmployeeAccount(){
        List<EmployeeAccountDTO> employeeAccountDTOList = new ArrayList<>();
        employeeRepository.findAll()
                .forEach(employee -> {
                    EmployeeAccountDTO employeeAccountDTO =
                            new EmployeeAccountDTO(employee.getUsername(), employee.getFirstName(), employee.getLastName());
                    employeeAccountDTOList.add(employeeAccountDTO);
                });
        return new ResponseEntity<>(employeeAccountDTOList, HttpStatus.OK);
    }

    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequests(){
        List<RequestDTO> requestDTOList = new ArrayList<>();
        requestRepository.findAll()
                .forEach(request -> {
                    Employee employee = employeeRepository.findOne(request.getUsernameEmployee());
                    RequestDTO requestDTO = new RequestDTO(employee.getFirstName(), employee.getLastName(),
                            request.getDescription(), request.getRequestStatus(), request.getDate());
                    requestDTOList.add(requestDTO);
                });
        return new ResponseEntity<>(requestDTOList, HttpStatus.OK);
    }

    @GetMapping("/viewContract/{employeeUsername}")
    public ResponseEntity<?> getContract(@PathVariable("employeeUsername") String employeeUsername){
        ContractDTO contractDTO = null;
        for (Contract contract : contractRepository.findAll())
            if (contract.getUsernameEmployee().equals(employeeUsername)) {
                Employee employee = employeeRepository.findOne(contract.getUsernameEmployee());
                contractDTO = new ContractDTO(employee.getFirstName(), employee.getLastName(),
                        contract.getGrossSalary(), contract.getHireDate(), contract.getType(),
                        contract.getDuration(), contract.getExpirationDate(), employee.getCnp());
            }
        if (contractDTO == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(contractDTO, HttpStatus.OK);
    }

    @GetMapping("/viewPayslip/{employeeUsername}/{year}/{month}")
    public ResponseEntity<?> getPayslip(@PathVariable("employeeUsername") String employeeUsername,
                                        @PathVariable("year") String year,
                                        @PathVariable("month") String month ){
        PayslipDTO payslipDTO = null;
        for (Payslip payslip : payslipRepository.findAll())
            if (payslip.getUsernameEmployee().equals(employeeUsername) && payslip.getYear().equals(year) && payslip.getMonth().equals(month)) {
                payslipDTO = new PayslipDTO(payslip.getYear(), payslip.getMonth(), payslip.getBrutSalary(), payslip.getNetSalary(),
                        payslip.getRealizedSalary(), payslip.getWorkedHours(), payslip.getRequiredHours());
            }
        if (payslipDTO == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(payslipDTO, HttpStatus.OK);
    }

    @PostMapping("/saveRequest")
    public ResponseEntity<?> saveRequest(@RequestBody Request request) {
        request.setId(UUID.randomUUID().toString());
        requestRepository.save(request);
        if (requestRepository.findOne(request.getId()) != null)
            return new ResponseEntity<>(HttpStatus.OK);
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
