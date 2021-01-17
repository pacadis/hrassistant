package Model;

public class Payslip {
    private String usernameEmployee;
    private String year;
    private String month;
    private float brutSalary;
    private float netSalary;
    private float realizedSalary;
    private float workedHours;
    private float requiredHours;
    private Integer idPayslip;

    public Payslip(String usernameEmployee, String year, String month, float brutSalary,
                   float netSalary, float realizedSalary, int workedHours, int requiredHours) {
        this.usernameEmployee = usernameEmployee;
        this.year = year;
        this.month = month;
        this.brutSalary = brutSalary;
        this.netSalary = netSalary;
        this.realizedSalary = realizedSalary;
        this.workedHours = workedHours;
        this.requiredHours = requiredHours;
    }

    public Payslip() {
    }

    public Integer getIdPayslip() {
        return idPayslip;
    }

    public void setIdPayslip(Integer idPayslip) {
        this.idPayslip = idPayslip;
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
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

    public float getBrutSalary() {
        return brutSalary;
    }

    public void setBrutSalary(float brutSalary) {
        this.brutSalary = brutSalary;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(float netSalary) {
        this.netSalary = netSalary;
    }

    public float getRealizedSalary() {
        return realizedSalary;
    }

    public void setRealizedSalary(float realizedSalary) {
        this.realizedSalary = realizedSalary;
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
}
