package Model.validator;

import Model.Company;
import Model.Contact;
import Model.Employee;
import Model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Validator {
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
}
