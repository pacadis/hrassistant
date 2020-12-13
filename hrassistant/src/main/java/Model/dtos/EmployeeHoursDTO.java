package Model.dtos;

public class EmployeeHoursDTO {
    private String year;
    private String month;
    private float workedHours;
    private float requiredHours;
    private float overtimeHours;
    private float overtimeLeave;

    public EmployeeHoursDTO(String year, String month, float workedHours, float requiredHours, float overtimeHours, float overtimeLeave) {
        this.year = year;
        this.month = month;
        this.workedHours = workedHours;
        this.requiredHours = requiredHours;
        this.overtimeHours = overtimeHours;
        this.overtimeLeave = overtimeLeave;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public float getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(float workedHours) {
        this.workedHours = workedHours;
    }

    public float getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(float requiredHours) {
        this.requiredHours = requiredHours;
    }

    public float getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(float overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public float getOvertimeLeave() {
        return overtimeLeave;
    }

    public void setOvertimeLeave(float overtimeLeave) {
        this.overtimeLeave = overtimeLeave;
    }
}
