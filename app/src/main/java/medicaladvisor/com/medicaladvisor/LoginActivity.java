package medicaladvisor.com.medicaladvisor;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import medicaladvisor.com.medicaladvisor.Loader.ShowLoader;

public class LoginActivity extends AppCompatActivity {
    TextView forgotTextView;
    EditText emailEdit, passwordEdit;
    String emailString, passwordString;
    ShowLoader showLoader;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initialization();
    }

    public void Initialization() {
        forgotTextView = findViewById(R.id.forgotText);
        emailEdit = findViewById(R.id.email);
        passwordEdit = findViewById(R.id.password);
        showLoader = new ShowLoader(LoginActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginImplementation(View view) {
        emailString = emailEdit.getText().toString();
        passwordString = passwordEdit.getText().toString();
//        startActivity(new Intent(LoginActivity.this, TabsActivity.class));
        if (emailString.isEmpty() || passwordString.isEmpty()) {
            showLoader.PresentToast("Please verify all the fields");
        } else {
            showLoader.showProgressDialog("Please wait...");
            firebaseAuth.signInWithEmailAndPassword(emailString, passwordString).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    showLoader.dismissDialog();
                    //  if (authResult.getUser().isEmailVerified() == true) {
                    startActivity(new Intent(LoginActivity.this, TabsActivity.class));
                    //                        } else {
                    //                            showLoader.PresentToast("Please verify the email link");
                    //                        }
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

    public void registerImplementation(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    public void sendForgotPasswordImplementation(View view) {
        emailString = emailEdit.getText().toString();
        if (emailString.isEmpty()) {
            showLoader.PresentToast("Please enter email address");
        } else {
            showLoader.showProgressDialog("Please wait..");
            firebaseAuth.sendPasswordResetEmail(emailString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    showLoader.dismissDialog();
                    showLoader.PresentToast("Forgot password link has been sent to email address");
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
}
