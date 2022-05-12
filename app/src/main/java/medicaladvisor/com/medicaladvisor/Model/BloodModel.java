package medicaladvisor.com.medicaladvisor.Model;

public class BloodModel {
    String donnerName;
    String bloodGroup;
    String donnerAddress;
    String donnerContact;

    public BloodModel() {
    }

    public BloodModel(String donnerName, String bloodGroup, String donnerAddress, String donnerContact) {
        this.donnerName = donnerName;
        this.bloodGroup = bloodGroup;
        this.donnerAddress = donnerAddress;
        this.donnerContact = donnerContact;
    }

    public String getDonnerName() {
        return donnerName;
    }

    public void setDonnerName(String donnerName) {
        this.donnerName = donnerName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDonnerAddress() {
        return donnerAddress;
    }

    public void setDonnerAddress(String donnerAddress) {
        this.donnerAddress = donnerAddress;
    }

    public String getDonnerContact() {
        return donnerContact;
    }

    public void setDonnerContact(String donnerContact) {
        this.donnerContact = donnerContact;
    }
}
