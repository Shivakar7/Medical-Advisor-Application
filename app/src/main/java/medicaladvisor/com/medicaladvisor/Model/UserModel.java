package medicaladvisor.com.medicaladvisor.Model;

public class UserModel {
    String pictureUrl;
    String name;
    String phone;
    String email;

    public UserModel(String pictureUrl, String name, String phone, String email) {
        this.pictureUrl = pictureUrl;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
