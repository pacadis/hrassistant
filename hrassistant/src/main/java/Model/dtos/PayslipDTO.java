package Model.dtos;

public class PayslipDTO {
    private String year;
    private String month;
    private float brutSalary;
    private float netSalary;
    private float realizedSalary;
    private int workedHours;
    private int requiredHours;

    public PayslipDTO(String year, String month, float brutSalary,
                   float netSalary, float realizedSalary, int workedHours, int requiredHours) {
        this.year = year;
        this.month = month;
        this.brutSalary = brutSalary;
        this.netSalary = netSalary;
        this.realizedSalary = realizedSalary;
        this.workedHours = workedHours;
        this.requiredHours = requiredHours;
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

    public int getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(int workedHours) {
        this.workedHours = workedHours;
    }

    public int getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(int requiredHours) {
        this.requiredHours = requiredHours;
    }
}
