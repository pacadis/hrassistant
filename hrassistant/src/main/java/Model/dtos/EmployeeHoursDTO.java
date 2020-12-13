package Model.dtos;

public class EmployeeHoursDTO {
    private float workedHours;
    private float requiredHours;
    private float overtimeHours;
    private float overtimeLeave;

    public EmployeeHoursDTO(float workedHours, float requiredHours, float overtimeHours, float overtimeLeave) {
        this.workedHours = workedHours;
        this.requiredHours = requiredHours;
        this.overtimeHours = overtimeHours;
        this.overtimeLeave = overtimeLeave;
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
