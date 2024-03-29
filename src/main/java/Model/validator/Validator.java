package Model.validator;

import Model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Validator{

    public void validateEmployee(Employee employee) throws ValidationException {
        String message="";
        if (employee.getUsername() == null || employee.getUsername().equals("")) {
            message += "Numele de utilizator invalid.\n";
        }
        if (employee.getPassword() == null || employee.getPassword().equals("")) {
            message += "Parola nu poate fi vida.\n";
        }
        if (employee.getFirstName() == null || employee.getFirstName().equals("")) {
            message += "Prenumele nu poate fi vid.\n";
        }
        if (employee.getLastName() == null || employee.getLastName().equals("")) {
            message += "Numele nu poate fi vid.\n";
        }
        if (employee.getCnp().length()!=13) {
            message += "CNP-ul trebuie sa contina 13 cifre.\n";
        }
        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }

    public void validateCompany(Company company) throws ValidationException {
        String message="";
        if (company.getUsername() == null || company.getUsername().equals("")) {
            message += "Numele de utilizator invalid.\n";
        }
        if (company.getPassword() == null || company.getPassword().equals("")) {
            message += "Parola nu poate fi goala.\n";
        }
        if (company.getName() == null || company.getName().equals("")) {
            message += "Numele nu poate fi gol.\n";
        }
        if (company.getAddress() == null || company.getAddress().equals("")) {
            message += "Adresa invalida.\n";
        }
        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }

    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public void validateContact(Contact contact) throws ValidationException {
        String message="";
        if (contact.getName() == null || contact.getName().equals("")) {
            message += "Numele nu poate fi gol.\n";
        }
        if (!isValid(contact.getEmail())) {
            message += "Email invalid.\n";
        }
        if (contact.getMessage() == null || contact.getMessage().equals("")) {
            message += "Mesajul nu poate fi gol.\n";
        }
        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }

    public void validateContract(Contract contract) throws ValidationException {
        String message="";
        if (contract.getUsernameEmployee() == null || contract.getUsernameEmployee().equals("")) {
            message += "Numele de utilizator nu poate fi vid.\n";
        }
        if (contract.getType() == null || contract.getType().equals("")) {
            message += "Tipul nu poate fi vid.\n";
        }
        if (!contract.getType().equals("FullTime") && !contract.getType().equals("PartTime6")
                && !contract.getType().equals("PartTime4")) {
            message += "Tipul trebuie sa fie FullTime, PartTime6 sau PartTime4.\n";
        }
        if (contract.getDuration() == null || contract.getDuration().equals("")) {
            message += "Durata nu poate fi vida.\n";
        }
        if (contract.getHireDate() == null || contract.getHireDate().equals("")) {
            message += "Data de angajare nu poate fi vida.\n";
        }
        if (contract.getExpirationDate() == null || contract.getExpirationDate().equals("")) {
            message += "Data expirare nu poate fi vida.\n";
        }
        if (!contract.getExpirationDate().after(contract.getHireDate())) {
            message += "Data de angajare trebuie sa fie inainte de data de expirare.\n";
        }
        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }

    public void validateRequest(Request request) throws ValidationException {
        String message="";
        if (request.getUsernameEmployee() == null || request.getUsernameEmployee().equals("")) {
            message += "Numele de utilizator nu poate fi vid.\n";
        }
        if (request.getDescription() == null || request.getDescription().equals("")) {
            message += "Descrierea nu poate fi vida.\n";
        }
        if (!request.getRequestStatus().equals("ACCEPT") && !request.getRequestStatus().equals("DECLINE")
                && !request.getRequestStatus().equals("PENDING")) {
            message += "Statusul trebuie sa fie ACCEPT sau DECLINE sau PENDING.\n";
        }
        if (request.getDate() == null ) {
            message += "Data nu poate fi vida.\n";
        }
        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }
    public void validateHoliday(Holiday holiday) throws ValidationException {
        String message="";
        if (holiday.getUsernameEmployee() == null || holiday.getUsernameEmployee().equals("")) {
            message += "Numele de utilizator nu poate fi vid.\n";
        }
        if (holiday.getDaysOff()< 0 ) {
            message += "Zilele libere nu pot fi negative.\n";
        }
        if (!holiday.getType().equals("Normal") && !holiday.getType().equals("BloodDonation")
                && !holiday.getType().equals("Death") &&  !holiday.getType().equals("Mariage")
            &&  !holiday.getType().equals("Overtime")) {
            message += "Tipul trebuie sa fie Normal sau BloodDonation sau Death sau Mariage sau Overtime.\n";
        }
        if (holiday.getFromDate() == null ) {
            message += "Data de inceput nu poate fi vida.\n";
        }
        if (holiday.getToDate() == null ) {
            message += "Data de sfarsit nu poate fi vida.\n";
        }
        if (!holiday.getToDate().after(holiday.getFromDate())) {
            message += "Data de sfarsit nu poate fi inainte de date de inceput.\n";
        }

        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }
}
