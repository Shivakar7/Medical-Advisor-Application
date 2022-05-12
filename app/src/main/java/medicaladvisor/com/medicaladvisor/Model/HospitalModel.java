package medicaladvisor.com.medicaladvisor.Model;

public class HospitalModel {
    String hospitalName;
    String hospitalAddress;
    String hospitalContact;
    String doctorName;
    String doctorDetails;

    public HospitalModel() {
    }

    public HospitalModel(String hospitalName, String hospitalAddress, String hospitalContact, String doctorName, String doctorDetails) {
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.hospitalContact = hospitalContact;
        this.doctorName = doctorName;
        this.doctorDetails = doctorDetails;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalContact() {
        return hospitalContact;
    }

    public void setHospitalContact(String hospitalContact) {
        this.hospitalContact = hospitalContact;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorDetails() {
        return doctorDetails;
    }

    public void setDoctorDetails(String doctorDetails) {
        this.doctorDetails = doctorDetails;
    }
}
