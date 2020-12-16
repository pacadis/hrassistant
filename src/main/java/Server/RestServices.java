package Server;


import Model.*;
import Model.dtos.*;
import Model.enums.EmployeeType;
import Model.enums.HolidayType;
import Model.enums.RequestStatus;
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
    private final ClockingRepository clockingRepository = new ClockingRepository();
    private final HolidayRepository holidayRepository = new HolidayRepository();

    // Service
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        Employee employee = employeeRepository.findOne(username);
        Company company = companyRepository.findOne(username);
        if (employee != null) {
            if (employee.getPassword().equals(password))
                return new ResponseEntity<>(new ResponseDTO("employee", employee.getFirstName()), HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (company != null) {
            if (company.getPassword().equals(password))
                return new ResponseEntity<>(new ResponseDTO("company", company.getName()), HttpStatus.OK);
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
                                        @PathVariable("month") String month){
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

    @GetMapping("/viewClocking/{employeeUsername}/{year}/{month}")
    public ResponseEntity<?> getClocking(@PathVariable("employeeUsername") String employeeUsername,
                                        @PathVariable("year") String year,
                                        @PathVariable("month") String month){
        ClockingDTO clockingDTO = null;
        for (Clocking clocking : clockingRepository.findAll())
            if (clocking.getUsernameEmployee().equals(employeeUsername) && clocking.getYear().equals(year) && clocking.getMonth().equals(month)) {
                clockingDTO = new ClockingDTO(clocking.getYear(), clocking.getMonth(), clocking.getWorkedHours(), clocking.getRequiredHours(),
                        clocking.getOvertimeHours(), clocking.getOvertimeLeave());
            }
        if (clockingDTO == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(clockingDTO, HttpStatus.OK);
    }

    @GetMapping("/viewHoliday/{employeeUsername}")
    public ResponseEntity<?> getHoliday(@PathVariable("employeeUsername") String employeeUsername){
        HolidayDTO holidayDTO = null;
        for (Holiday holiday : holidayRepository.findAll())
            if (holiday.getUsernameEmployee().equals(employeeUsername)) {
                holidayDTO = new HolidayDTO(holiday.getType(), holiday.getFromDate(), holiday.getToDate(), holiday.getProxyUsername());
            }
        if (holidayDTO == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(holidayDTO, HttpStatus.OK);
    }

    @PostMapping("/saveRequest")
    public ResponseEntity<?> saveRequest(@RequestBody Request request, @RequestBody Holiday holiday) {
        request.setId(UUID.randomUUID().toString());
        requestRepository.save(request);
        if (requestRepository.findOne(request.getId()) != null) {
            holiday.setIdRequest(request.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/request/{idRequest}/{string}")
    public ResponseEntity<?> processingRequest(@PathVariable("idRequest") String idRequest, @PathVariable("string") String string){
        if (string.equals(RequestStatus.DECLINE.toString())) {
            Request request = requestRepository.findOne(idRequest);
            request.setRequestStatus(RequestStatus.DECLINE.toString());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        if (string.equals(RequestStatus.ACCEPT.toString())) {
            boolean ok = acceptRequest(idRequest);
            if (ok == true)
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public boolean acceptRequest(String idRequest){
        Request request = requestRepository.findOne(idRequest);
        Holiday holiday = holidayRepository.findOneByIdRequest(idRequest);
        Clocking clocking = clockingRepository.findOneByIdRequest(idRequest);
        Contract contract = contractRepository.findOne(request.getUsernameEmployee());
        if (holiday.getType().equals(HolidayType.Normal.toString())) {
            if (holiday.getToDate().getDay() - holiday.getFromDate().getDay() <= holiday.getDaysOff()) {
                holiday.setDaysOff(holiday.getToDate().getDay() - holiday.getFromDate().getDay());
                request.setRequestStatus(RequestStatus.ACCEPT.toString());
                return true;
            }
            else {
                request.setRequestStatus(RequestStatus.DECLINE.toString());
                return false;
            }
        }
        if (holiday.getType().equals(HolidayType.BloodDonation.toString()) ||
                holiday.getType().equals(HolidayType.Death.toString()) || holiday.getType().equals(HolidayType.Mariage.toString())) {
            request.setRequestStatus(RequestStatus.ACCEPT.toString());
            return true;
        }
        if (holiday.getType().equals(HolidayType.Overtime.toString())) {
            if (contract.getType().equals(EmployeeType.FullTime.toString())) {
                if (holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 8<= clocking.getOvertimeHours()) {
                    clocking.setOvertimeHours(clocking.getOvertimeHours() - holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 8);
                    request.setRequestStatus(RequestStatus.ACCEPT.toString());
                    return true;
                }
                else {
                    request.setRequestStatus(RequestStatus.DECLINE.toString());
                    return false;
                }
            }
            if (contract.getType().equals(EmployeeType.PartTime6.toString())) {
                if (holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 6<= clocking.getOvertimeHours()) {
                    clocking.setOvertimeHours(clocking.getOvertimeHours() - holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 4);
                    request.setRequestStatus(RequestStatus.ACCEPT.toString());
                    return true;
                }
                else {
                    request.setRequestStatus(RequestStatus.DECLINE.toString());
                    return false;
                }
            }
            if (contract.getType().equals(EmployeeType.PartTime4.toString())) {
                if (holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 4<= clocking.getOvertimeHours()) {
                    clocking.setOvertimeHours(clocking.getOvertimeHours() - holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 4);
                    request.setRequestStatus(RequestStatus.ACCEPT.toString());
                    return true;
                }
                else {
                    request.setRequestStatus(RequestStatus.DECLINE.toString());
                    return false;
                }
            }
        }
        return false;
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
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
        if (employee.getCnp().length()!=13)
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        employee.setId(employee.getUsername() + employee.getPassword());
        Employee employee1 = employeeRepository.findOne(employee.getUsername());
        if (employee.getCompany() != employee1.getCompany() || employee1 == null) {
            employeeRepository.save(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/employee/{usernameEmployee}")
    public void updateEmployee(@RequestBody Employee employee, @PathVariable("usernameEmployee") String usernameEmployee) {
        employee.setId(usernameEmployee);
        employeeRepository.update(employee);
    }

    @DeleteMapping("/employee/{usernameEmployee}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("usernameEmployee") String usernameEmployee) {
        Employee employee = employeeRepository.findOne(usernameEmployee);
        if (employee!=null) {
            employeeRepository.delete(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
