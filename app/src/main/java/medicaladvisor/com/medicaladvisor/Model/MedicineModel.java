package medicaladvisor.com.medicaladvisor.Model;

public class MedicineModel {
    String name;
    String details;

    public MedicineModel() {
    }

    public MedicineModel(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
