package Server;


import Model.*;
import Model.dtos.*;
import Model.enums.EmployeeType;
import Model.enums.HolidayType;
import Model.enums.RequestStatus;
import Model.validator.ValidationException;
import Model.validator.Validator;
import Repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/hr")
public class RestServices {

    private final Validator validator = new Validator();
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final CompanyRepository companyRepository = new CompanyRepository();
    private final RequestRepository requestRepository = new RequestRepository();
    private final ContractRepository contractRepository = new ContractRepository();
    private final PayslipRepository payslipRepository = new PayslipRepository();
    private final ClockingRepository clockingRepository = new ClockingRepository();
    private final HolidayRepository holidayRepository = new HolidayRepository();
    private final ContactRepository contactRepository = new ContactRepository();

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
        try {
            validator.validateCompany(company);
        } catch (ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        Employee employee = employeeRepository.findOne(company.getUsername());
        Company companyFind = companyRepository.findOne(company.getUsername());
        if (companyFind == null && employee == null) {
            companyRepository.save(company);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/employeeAccounts/{companyUsername}")
    public ResponseEntity<?> getAllEmployeeAccount(@PathVariable("companyUsername") String companyUsername){
        List<EmployeeAccountDTO> employeeAccountDTOList = new ArrayList<>();
        employeeRepository.findAll()
                .forEach(employee -> {
                    if (employee.getCompany().equals(companyUsername)) {
                        EmployeeAccountDTO employeeAccountDTO =
                                new EmployeeAccountDTO(employee.getUsername(), employee.getFirstName(), employee.getLastName());
                        employeeAccountDTOList.add(employeeAccountDTO);
                    }
                });
        return new ResponseEntity<>(employeeAccountDTOList, HttpStatus.OK);
    }

    private List<RequestDTO> getRequests(String companyName){
        List<RequestDTO> requestDTOList = new ArrayList<>();
        requestRepository.findAll()
                .forEach(request -> {
                    Employee employee = employeeRepository.findOne(request.getUsernameEmployee());
                    if(employee.getCompany().equals(companyName)) {
                        RequestDTO requestDTO = new RequestDTO(request.getId(), employee.getFirstName(), employee.getLastName(),
                                request.getDescription(), request.getRequestStatus(), request.getDate());
                        requestDTOList.add(requestDTO);
                    }
                });
        return requestDTOList;
    }

    @GetMapping("/noRequests/{company}")
    public ResponseEntity<?> getNumberOfPendingRequests(@PathVariable("company") String companyName){
        int noPending = 0;
        for(RequestDTO requestDTO: getRequests(companyName)){
            if(requestDTO.getRequestStatus().equals("PENDING")) noPending++;
        }
        return new ResponseEntity<>(noPending, HttpStatus.OK);
    }

    @GetMapping("/requests/{company}")
    public ResponseEntity<?> getAllRequests(@PathVariable("company") String companyName){
        return new ResponseEntity<>(getRequests(companyName), HttpStatus.OK);
    }

    @GetMapping("/employeeRequests/{employee}")
    public ResponseEntity<?> getEmployeeRequests(@PathVariable("employee") String employeeUsername){
        List<RequestDTO> requests = new ArrayList<>();
        requestRepository.findAll().forEach(request -> {
            if(request.getUsernameEmployee().equals(employeeUsername)) {
                Employee employee = employeeRepository.findOne(employeeUsername);
                RequestDTO requestDTO = new RequestDTO(request.getId(), employee.getFirstName(), employee.getLastName(),
                        request.getDescription(), request.getRequestStatus(), request.getDate());
                requests.add(requestDTO);
            }
        });
        return new ResponseEntity<>(requests, HttpStatus.OK);
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

    @GetMapping("/viewContracts/{companyUsername}")
    public ResponseEntity<?> getAllContracts(@PathVariable("companyUsername") String companyUsername){
        List<ContractDTO> contractDTOList = new ArrayList<>();
        for (Contract contract : contractRepository.findAll()) {
            Employee employee = employeeRepository.findOne(contract.getUsernameEmployee());
            if (employee!=null && employee.getCompany().equals(companyUsername)) {
                ContractDTO contractDTO = new ContractDTO(employee.getFirstName(), employee.getLastName(),
                        contract.getGrossSalary(), contract.getHireDate(), contract.getType(),
                        contract.getDuration(), contract.getExpirationDate(), employee.getCnp());
                contractDTOList.add(contractDTO);
            }
        }
        if (contractDTOList.size() > 0)
            return new ResponseEntity<>(contractDTOList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/viewPayslip/{employeeUsername}")
    public ResponseEntity<?> getPayslip(@PathVariable("employeeUsername") String employeeUsername){
        List<PayslipDTO> payslipDTOList = new ArrayList<>();
        for (Payslip payslip : payslipRepository.findAll())
            if (payslip.getUsernameEmployee().equals(employeeUsername)) {
                PayslipDTO payslipDTO = new PayslipDTO(payslip.getYear(), payslip.getMonth(), payslip.getBrutSalary(), payslip.getNetSalary(),
                        payslip.getRealizedSalary(), payslip.getWorkedHours(), payslip.getRequiredHours());
                payslipDTOList.add(payslipDTO);
            }
        if (payslipDTOList.size() == 0)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(payslipDTOList, HttpStatus.OK);
    }

    @GetMapping("/viewClocking/{employeeUsername}")
    public ResponseEntity<?> getClocking(@PathVariable("employeeUsername") String employeeUsername){
        List<ClockingDTO> clockingDTOList = new ArrayList<>();
        for (Clocking clocking : clockingRepository.findAll())
            if (clocking.getUsernameEmployee().equals(employeeUsername)) {
                ClockingDTO clockingDTO = new ClockingDTO(clocking.getYear(), clocking.getMonth(), clocking.getDay(), clocking.getWorkedHours(), clocking.getRequiredHours(),
                        clocking.getOvertimeHours(), clocking.getOvertimeLeave());
                clockingDTOList.add(clockingDTO);
            }
        if (clockingDTOList.size() == 0)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(clockingDTOList, HttpStatus.OK);
    }

    @GetMapping("/viewHoliday/{employeeUsername}")
    public ResponseEntity<?> getHoliday(@PathVariable("employeeUsername") String employeeUsername){
        List<HolidayDTO> holidayDTOList = new ArrayList<>();
        holidayRepository.findAll().forEach(holiday -> {
            if (holiday.getUsernameEmployee().equals(employeeUsername)) {
                System.out.println(holiday.getType());
                HolidayDTO holidayDTO = new HolidayDTO(holiday.getType(), holiday.getFromDate(), holiday.getToDate(), holiday.getProxyUsername());
                if (holidayDTO.getType().equals("Normal")) {
                    holidayDTO.setType("Concediu");
                }
                if (holidayDTO.getType().equals("BloodDonation")) {
                    holidayDTO.setType("Concediu pentru donare de sange");
                }
                if (holidayDTO.getType().equals("Death")) {
                    holidayDTO.setType("Concediu pentru inmormantare");
                }
                if (holidayDTO.getType().equals("Mariage")) {
                    holidayDTO.setType("Concediu pentru casatorie");
                }
                if (holidayDTO.getType().equals("Overtime")) {
                    holidayDTO.setType("Concediu din ore suplimentare");
                }
                holidayDTOList.add(holidayDTO);
            }
        });
        return new ResponseEntity<>(holidayDTOList, HttpStatus.OK);
    }

    @PostMapping("/saveRequest")
    public ResponseEntity<?> saveRequest(@RequestBody SaveRequestDTO saveRequestDTO) {
        String uniqueID = UUID.randomUUID().toString();
        if (saveRequestDTO.getType().equals("Concediu")) {
            saveRequestDTO.setType("Normal");
        }
        if (saveRequestDTO.getType().equals("Concediu pentru donare de sange")) {
            saveRequestDTO.setType("BloodDonation");
        }
        if (saveRequestDTO.getType().equals("Concediu pentru inmormantare")) {
            saveRequestDTO.setType("Death");
        }
        if (saveRequestDTO.getType().equals("Concediu pentru casatorie")) {
            saveRequestDTO.setType("Mariage");
        }
        if (saveRequestDTO.getType().equals("Concediu din ore suplimentare")) {
            saveRequestDTO.setType("Overtime");
        }
        Request request = new Request(saveRequestDTO.getUsernameEmployee(), saveRequestDTO.getDescription(), saveRequestDTO.getRequestStatus(),
                saveRequestDTO.getDate());
        request.setId(uniqueID);
        try{
            validator.validateRequest(request);
        }
       catch (ValidationException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        requestRepository.save(request);
        //int daysOff = (int) ((int)saveRequestDTO.getToDate().getTime() - saveRequestDTO.getFromDate().getTime());
        int days = (int) ((int)saveRequestDTO.getToDate().getTime() - saveRequestDTO.getFromDate().getTime());
        long daysOff = TimeUnit.MILLISECONDS.toDays(days);
        Holiday holiday = new Holiday(saveRequestDTO.getUsernameEmployee(), (int) daysOff,saveRequestDTO.getType(),
                saveRequestDTO.getFromDate(),saveRequestDTO.getToDate(), saveRequestDTO.getProxyName());
        holiday.setIdRequest(request.getId());
        try{
            validator.validateHoliday(holiday);
        }
        catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        holidayRepository.save(holiday);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/request/{idRequest}/{string}")
    public ResponseEntity<?> processingRequest(@PathVariable("idRequest") String idRequest, @PathVariable("string") String string){
        if (string.equals(RequestStatus.DECLINE.toString())) {
            Request request = requestRepository.findOne(idRequest);
            request.setRequestStatus(RequestStatus.DECLINE.toString());
            requestRepository.update(request);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        if (string.equals(RequestStatus.ACCEPT.toString())) {
            boolean ok = acceptRequest(idRequest);
            if (ok == true)
                return new ResponseEntity<>(HttpStatus.OK);

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
                holidayRepository.update(holiday);
                requestRepository.update(request);
                return true;
            }
            else {
                request.setRequestStatus(RequestStatus.DECLINE.toString());
                requestRepository.update(request);
                return false;
            }
        }
        if (holiday.getType().equals(HolidayType.BloodDonation.toString()) ||
                holiday.getType().equals(HolidayType.Death.toString()) || holiday.getType().equals(HolidayType.Mariage.toString())) {
            request.setRequestStatus(RequestStatus.ACCEPT.toString());
            holidayRepository.update(holiday);
            requestRepository.update(request);
            return true;
        }
        if (holiday.getType().equals(HolidayType.Overtime.toString())) {
            if (contract.getType().equals(EmployeeType.FullTime.toString())) {
                if (holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 8<= clocking.getOvertimeHours()) {
                    clocking.setOvertimeHours(clocking.getOvertimeHours() - holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 8);
                    request.setRequestStatus(RequestStatus.ACCEPT.toString());
                    clockingRepository.update(clocking);
                    requestRepository.update(request);
                    return true;
                }
                else {
                    request.setRequestStatus(RequestStatus.DECLINE.toString());
                    requestRepository.update(request);
                    return false;
                }
            }
            if (contract.getType().equals(EmployeeType.PartTime6.toString())) {
                if (holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 6<= clocking.getOvertimeHours()) {
                    clocking.setOvertimeHours(clocking.getOvertimeHours() - holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 4);
                    request.setRequestStatus(RequestStatus.ACCEPT.toString());
                    clockingRepository.update(clocking);
                    requestRepository.update(request);
                    return true;
                }
                else {
                    request.setRequestStatus(RequestStatus.DECLINE.toString());
                    requestRepository.update(request);
                    return false;
                }
            }
            if (contract.getType().equals(EmployeeType.PartTime4.toString())) {
                if (holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 4<= clocking.getOvertimeHours()) {
                    clocking.setOvertimeHours(clocking.getOvertimeHours() - holiday.getToDate().getDay() - holiday.getFromDate().getDay() * 4);
                    request.setRequestStatus(RequestStatus.ACCEPT.toString());
                    clockingRepository.update(clocking);
                    requestRepository.update(request);
                    return true;
                }
                else {
                    request.setRequestStatus(RequestStatus.DECLINE.toString());
                    requestRepository.update(request);
                    return false;
                }
            }
        }
        return false;
    }

    @PostMapping("/contact")
    public ResponseEntity<?> saveContact(@RequestBody Contact contact) {
        try{
            validator.validateContact(contact);
            final String fromEmail = "office.hrassistant10@gmail.com"; //requires valid gmail id
            final String password = "proiectcolectiv10"; // correct password for gmail id
            final String toEmail = contact.getEmail(); // can be any email id

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
            props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
            props.put("mail.smtp.port", "465"); //SMTP Port

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            MimeMessage msg = new MimeMessage(Session.getDefaultInstance(props, auth));
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));
            msg.setSubject("Sesizare HRAssistant", "UTF-8");
            msg.setText("Am primit sesizarea dumneavoastra si vom veni cu un raspuns in cel mai scurt timp\n\n`" + contact.getMessage() + "`\n\nMultumim!", "UTF-8");
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("NO BINEEEEE");
            Transport.send(msg);
        } catch (ValidationException | MessagingException | UnsupportedEncodingException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        contactRepository.save(contact);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // EmployeeServices
    @GetMapping("/employee")
    public ResponseEntity<?> getEmployees() {
        List<Employee> list = employeeRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/employee/{usernameEmployee}")
    public ResponseEntity<?> getEmployeeContract(@PathVariable("usernameEmployee") String usernameEmployee) {
        Employee employee = employeeRepository.findOne(usernameEmployee);
        Contract contract = contractRepository.findOne(usernameEmployee);
        EditEmployeeDTO editEmployeeDTO;
        if (employee != null) {
            if (contract != null)
                editEmployeeDTO = new EditEmployeeDTO(employee.getUsername(), employee.getPassword(),
                        employee.getFirstName(), employee.getLastName(), employee.getCnp(), contract.getGrossSalary(),
                        contract.getHireDate(), contract.getType(), contract.getDuration(), contract.getExpirationDate());
            else
                editEmployeeDTO = new EditEmployeeDTO(employee.getUsername(), employee.getPassword(),
                        employee.getFirstName(), employee.getLastName(), employee.getCnp(), 0,
                        new Date(), "", "", new Date());
            return new ResponseEntity<>(editEmployeeDTO, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/employee")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
        employee.setId(employee.getUsername() + employee.getPassword());
        try {
            validator.validateEmployee(employee);
        } catch (ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        Employee employee1 = employeeRepository.findOne(employee.getUsername());
        if (employee1 == null) {
            employeeRepository.save(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/employee/{usernameEmployee}")
    public ResponseEntity<?> updateEmployee(@RequestBody EditEmployeeDTO employeeEdited, @PathVariable("usernameEmployee") String usernameEmployee) {
        Employee employee = employeeRepository.findOne(employeeEdited.getUsername());
        Contract contract = contractRepository.findOne(usernameEmployee);
        employee.setFirstName(employeeEdited.getFirstName());
        employee.setLastName(employeeEdited.getLastName());
        employee.setUsername(employeeEdited.getUsername());
        employee.setPassword(employeeEdited.getPassword());
        employee.setCnp(employeeEdited.getCnp());
        contract.setDuration(employeeEdited.getDuration());
        contract.setHireDate(employeeEdited.getHireDate());
        contract.setExpirationDate(employeeEdited.getExpirationDate());
        contract.setGrossSalary(employeeEdited.getGrossSalary());
        contract.setType(employeeEdited.getType());
        try {
            validator.validateEmployee(employee);
        }catch (ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            validator.validateContract(contract);
        }catch (ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        employeeRepository.update(employee);
        contractRepository.update(contract);
        return new ResponseEntity<>(HttpStatus.OK);
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
}
