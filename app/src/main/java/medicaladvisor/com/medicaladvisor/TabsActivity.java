package medicaladvisor.com.medicaladvisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import medicaladvisor.com.medicaladvisor.Adapter.BloodAdapter;
import medicaladvisor.com.medicaladvisor.Adapter.DiseaseAdapter;
import medicaladvisor.com.medicaladvisor.Adapter.HospitalAdapter;
import medicaladvisor.com.medicaladvisor.Adapter.MedicineAdapter;
import medicaladvisor.com.medicaladvisor.Loader.ShowLoader;
import medicaladvisor.com.medicaladvisor.Model.BloodModel;
import medicaladvisor.com.medicaladvisor.Model.DiseaseModel;
import medicaladvisor.com.medicaladvisor.Model.HospitalModel;
import medicaladvisor.com.medicaladvisor.Model.MedicineModel;

public class TabsActivity extends AppCompatActivity {
    Button diseaseButton, medicineButton, hospitalButton, bloodButton;
    EditText searchEditText;
    ListView listView;
    DiseaseAdapter diseaseAdapter;
    MedicineAdapter medicineAdapter;
    HospitalAdapter hospitalAdapter;
    BloodAdapter bloodAdapter;
    ArrayList<DiseaseModel> diseaseModelList = new ArrayList<>();
   // ArrayList<String> autoCompleteList = new ArrayList<>();
    ArrayList<MedicineModel> medicineModelArrayList = new ArrayList<>();
   // ArrayList<String> medicineAutoCompleteList = new ArrayList<>();
    ArrayList<HospitalModel> hospitalModelArrayList = new ArrayList<>();
   // ArrayList<String> hospitalAutoCompleteList = new ArrayList<>();
    ArrayList<BloodModel> bloodModelArrayList = new ArrayList<>();
   // ArrayList<String> bloodAutoCompleteList = new ArrayList<>();
    FirebaseFirestore firebaseFirestore;
    ShowLoader showLoader;
    String selectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        Initialization();
    }

    public void Initialization() {
        diseaseButton = findViewById(R.id.diseasesTab);
        medicineButton = findViewById(R.id.medicineTab);
        hospitalButton = findViewById(R.id.hospitalsTab);
        bloodButton = findViewById(R.id.bloodTab);
        searchEditText = findViewById(R.id.diseasesSearch);
        listView = findViewById(R.id.diseasesList);

        selectType = "DISEASE";

        diseaseButton.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        medicineButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        hospitalButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        bloodButton.setBackgroundColor(getResources().getColor(R.color.colorGray));

        firebaseFirestore = FirebaseFirestore.getInstance();
        showLoader = new ShowLoader(TabsActivity.this);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchEditText.getText().toString().toLowerCase(Locale.getDefault());
                if (selectType == "DISEASE") {
                    diseaseAdapter.filter(text);
                } else if (selectType == "MEDICINE") {
                    medicineAdapter.filter(text);
                } else if (selectType == "HOSPITAL") {
                    hospitalAdapter.filter(text);
                } else if (selectType == "BLOOD") {
                    bloodAdapter.filter(text);
                }
            }
        });
    }

    public void diseaseListImplementation(View view) {
        selectType = "DISEASE";
        searchEditText.setHint("Search Disease");
        diseaseButton.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        medicineButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        hospitalButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        bloodButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        getDiseasesList();

    }

    public void medicineListImplementation(View view) {
        selectType = "MEDICINE";
        searchEditText.setHint("Search Medicine");
        diseaseButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        medicineButton.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        hospitalButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        bloodButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        getMedicineList();
    }

    public void hospitalsListImplementation(View view) {
        selectType = "HOSPITAL";
        searchEditText.setHint("Search Hospital");
        diseaseButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        medicineButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        hospitalButton.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        bloodButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        getHospitalList();
    }

    public void bloodListImplementation(View view) {
        selectType = "BLOOD";
        searchEditText.setHint("Search Blood");
        diseaseButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        medicineButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        hospitalButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        bloodButton.setBackgroundColor(getResources().getColor(R.color.colorOrange));
        getBloodList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDiseasesList();
    }

    public void getDiseasesList() {
        diseaseModelList.clear();
        showLoader.showProgressDialog("Please wait..");
        firebaseFirestore.collection("Diseases").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentChange> documents = queryDocumentSnapshots.getDocumentChanges();
                for (int i = 0; i < documents.size(); i++) {
                    DiseaseModel diseaseModel = new DiseaseModel();
                    diseaseModel = documents.get(i).getDocument().toObject(DiseaseModel.class);
                    diseaseModelList.add(diseaseModel);
                 //   autoCompleteList.add(diseaseModel.getName());
                }

                diseaseAdapter = new DiseaseAdapter(TabsActivity.this, diseaseModelList);
                listView.setAdapter(diseaseAdapter);

                showLoader.dismissDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoader.dismissDialog();
                showLoader.showProgressDialog(e.getMessage());
            }
        });
    }

    public void getMedicineList() {
        medicineModelArrayList.clear();
        showLoader.showProgressDialog("Please wait..");
        firebaseFirestore.collection("Medicines").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentChange> documents = queryDocumentSnapshots.getDocumentChanges();
                for (int i = 0; i < documents.size(); i++) {
                    MedicineModel medicineModel = new MedicineModel();
                    medicineModel = documents.get(i).getDocument().toObject(MedicineModel.class);
                    medicineModelArrayList.add(medicineModel);
                //    medicineAutoCompleteList.add(medicineModel.getName());
                }

                medicineAdapter = new MedicineAdapter(TabsActivity.this, medicineModelArrayList);
                listView.setAdapter(medicineAdapter);

                showLoader.dismissDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoader.dismissDialog();
                showLoader.showProgressDialog(e.getMessage());
            }
        });
    }

    public void getHospitalList() {
        hospitalModelArrayList.clear();
        showLoader.showProgressDialog("Please wait..");
        firebaseFirestore.collection("Hospitals").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentChange> documents = queryDocumentSnapshots.getDocumentChanges();
                for (int i = 0; i < documents.size(); i++) {
                    HospitalModel hospitalModel = new HospitalModel();
                    hospitalModel = documents.get(i).getDocument().toObject(HospitalModel.class);
                    hospitalModelArrayList.add(hospitalModel);
                 //   hospitalAutoCompleteList.add(hospitalModel.getHospitalName());
                }

                hospitalAdapter = new HospitalAdapter(TabsActivity.this, hospitalModelArrayList);
                listView.setAdapter(hospitalAdapter);

                showLoader.dismissDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoader.dismissDialog();
                showLoader.showProgressDialog(e.getMessage());
            }
        });
    }

    public void getBloodList() {
        bloodModelArrayList.clear();
        showLoader.showProgressDialog("Please wait..");
        firebaseFirestore.collection("Blood").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentChange> documents = queryDocumentSnapshots.getDocumentChanges();
                for (int i = 0; i < documents.size(); i++) {
                    BloodModel bloodModel = new BloodModel();
                    bloodModel = documents.get(i).getDocument().toObject(BloodModel.class);
                    bloodModelArrayList.add(bloodModel);
                  //  bloodAutoCompleteList.add(bloodModel.getDonnerName());
                }

                bloodAdapter = new BloodAdapter(TabsActivity.this, bloodModelArrayList);
                listView.setAdapter(bloodAdapter);

                showLoader.dismissDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoader.dismissDialog();
                showLoader.showProgressDialog(e.getMessage());
            }
        });
    }
}

