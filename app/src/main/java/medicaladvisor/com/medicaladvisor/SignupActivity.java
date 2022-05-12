package medicaladvisor.com.medicaladvisor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import medicaladvisor.com.medicaladvisor.Loader.ShowLoader;
import medicaladvisor.com.medicaladvisor.LocalStorage.SaveLocalData;
import medicaladvisor.com.medicaladvisor.Model.UserModel;

public class SignupActivity extends AppCompatActivity {
    private static final int REQUESCODE = 10;
    ImageView imageView;
    EditText nameEdit, dobEdit, mobileEdit, emailEdit, passwordEdit, cPasswordEdit;
    String pictureUrl, nameString, mobileString, emailString, passwordString, cPasswordString;
    ShowLoader showLoader;
    private int PReqCode = 100;
    SaveLocalData saveLocalData;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Initialization();
    }

    public void Initialization() {
        imageView = findViewById(R.id.profileImageView);
        nameEdit = findViewById(R.id.name);
        mobileEdit = findViewById(R.id.phone);
        emailEdit = findViewById(R.id.email);
        passwordEdit = findViewById(R.id.password);
        cPasswordEdit = findViewById(R.id.cPassword);
        checkAndRequestForPermission();
        saveLocalData = new SaveLocalData(SignupActivity.this);
        pictureUrl = "";
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        showLoader = new ShowLoader(SignupActivity.this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    public void signupImplementation(View view) {
        nameString = nameEdit.getText().toString();
        mobileString = mobileEdit.getText().toString();
        emailString = emailEdit.getText().toString();
        passwordString = passwordEdit.getText().toString();
        cPasswordString = cPasswordEdit.getText().toString();

        if (pictureUrl.isEmpty() || nameString.isEmpty() || mobileString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty() || cPasswordString.isEmpty()) {
            showLoader.PresentToast("Please verify all the fields");
        } else {
            if (passwordString.equals(cPasswordString)) {
                showLoader.showProgressDialog("Please wait...");
                firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserModel userModel = new UserModel(pictureUrl,nameString, mobileString, emailString);
                        firebaseFirestore.collection("Passengers").document(authResult.getUser().getUid()).set(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                firebaseAuth.getCurrentUser().sendEmailVerification();
                                showLoader.PresentToast("User register successfully,Verification link has been sent to email.");
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showLoader.dismissDialog();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showLoader.dismissDialog();
                        showLoader.PresentToast(e.getMessage());
                    }
                });
            } else {
                showLoader.PresentToast("Password mismatch");
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(SignupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(SignupActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
                ;
            } else {
                ActivityCompat.requestPermissions(SignupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else
            openGallery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri pickedImgUri = data.getData();
            imageView.setImageURI(pickedImgUri);
            updateUserInfo(pickedImgUri);
        }
    }

    private void updateUserInfo(Uri pickedImgUri) {
        showLoader.showProgressDialog("Uploading image....");
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pictureUrl = "" + uri;
                        System.out.println("Picture: " + pictureUrl);
                        if (pictureUrl != null) {
                            Glide.with(SignupActivity.this)
                                    .load(pictureUrl).apply(RequestOptions.circleCropTransform())
                                    .into(imageView);
                        } else {
                            imageView.setBackgroundResource(R.drawable.at);
                        }
                        showLoader.dismissDialog();
                        showLoader.PresentToast("Picture uploaded successfully");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoader.dismissDialog();
                showLoader.PresentToast(e.getMessage());
            }
        });
    }
}
