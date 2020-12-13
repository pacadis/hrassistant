package Model.dtos;

public class EmployeeHoursDTO {
    private String username;
    private String firstName;
    private String lastName;
    private int workedHours;
    private int overtimeHours;

    public EmployeeHoursDTO(String username, String firstName, String lastName, int workedHours,
                            int overtimeHours) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workedHours = workedHours;
        this.overtimeHours = overtimeHours;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(int workedHours) {
        this.workedHours = workedHours;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }
}
