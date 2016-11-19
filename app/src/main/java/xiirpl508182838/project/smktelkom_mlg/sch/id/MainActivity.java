package xiirpl508182838.project.smktelkom_mlg.sch.id;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvRegist;
    private Button btLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        tvRegist = (TextView) findViewById(R.id.linkSignup);
        btLogin = (Button) findViewById(R.id.buttonLogin);

        btLogin.setOnClickListener(this);
        tvRegist.setOnClickListener(this);
    }

    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Login in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == btLogin) {
            userLogin();
        }
        if (v == tvRegist) {
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}


