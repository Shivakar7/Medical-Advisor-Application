package medicaladvisor.com.medicaladvisor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import medicaladvisor.com.medicaladvisor.Model.BloodModel;
import medicaladvisor.com.medicaladvisor.Model.DiseaseModel;
import medicaladvisor.com.medicaladvisor.Model.HospitalModel;
import medicaladvisor.com.medicaladvisor.Model.MedicineModel;

public class ViewDetailsActivity extends AppCompatActivity {

    TextView nameText, detailsText;
    String view_type, getDiseaseString, getMedicineString, getHospitalString, getBloodString;
    DiseaseModel diseaseModel;
    MedicineModel medicineModel;
    HospitalModel hospitalModel;
    BloodModel bloodModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        Initialization();
    }

    public void Initialization() {
        nameText = findViewById(R.id.view_name);
        detailsText = findViewById(R.id.view_details);
        view_type = getIntent().getStringExtra("VIEW_TYPE");
        if (view_type.equals("DISEASE")) {
            getDiseaseString = getIntent().getStringExtra("DiseaseData");
            Gson gson = new Gson();
            diseaseModel = gson.fromJson(getDiseaseString, DiseaseModel.class);
            nameText.setText("Name: "+diseaseModel.getName());
            detailsText.setText("Details: "+diseaseModel.getDetails());
        } else if (view_type.equals("MEDICINE")) {
            getMedicineString = getIntent().getStringExtra("MedicineData");
            Gson gson = new Gson();
            medicineModel = gson.fromJson(getMedicineString, MedicineModel.class);
            nameText.setText("Name: "+medicineModel.getName());
            detailsText.setText("Details: "+medicineModel.getDetails());;
        } else if (view_type == "HOSPITAL") {
            getHospitalString = getIntent().getStringExtra("HospitalData");
            Gson gson = new Gson();
            hospitalModel = gson.fromJson(getHospitalString, HospitalModel.class);
            nameText.setText("Hospital Name: "+hospitalModel.getHospitalName());
            detailsText.setText("Hospital Address: "+hospitalModel.getHospitalAddress()+"\n\nHospital Contact: "+hospitalModel.getHospitalContact()+"\n\nDoctor Name: "+hospitalModel.getDoctorName()+"\n\nDoctor Details: "+hospitalModel.getDoctorDetails());
        } else if (view_type == "BLOOD") {
            getBloodString = getIntent().getStringExtra("BloodData");
            Gson gson = new Gson();
            bloodModel = gson.fromJson(getBloodString, BloodModel.class);
            nameText.setText("Donner Name"+bloodModel.getDonnerName());
            detailsText.setText("Blood Type: "+bloodModel.getBloodGroup()+"\n\nAddress: "+bloodModel.getDonnerAddress()+"\n\nContact: "+bloodModel.getDonnerContact());
        }
    }
}
