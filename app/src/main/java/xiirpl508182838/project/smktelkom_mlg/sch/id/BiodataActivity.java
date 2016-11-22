package xiirpl508182838.project.smktelkom_mlg.sch.id;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import xiirpl508182838.project.smktelkom_mlg.sch.id.db.DataUser;

public class BiodataActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNama, etAlamat;
    private Button btSubmit;

    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        etNama = (EditText) findViewById(R.id.editTextNama);
        etAlamat = (EditText) findViewById(R.id.editTextAlamat);
        btSubmit = (Button) findViewById(R.id.buttonSubmit);

        btSubmit.setOnClickListener(this);

    }

    private void saveDataUser() {

        if (isValid()) {
            String nama = etNama.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();

            DataUser dataUser = new DataUser(nama, alamat);
            FirebaseUser user = firebaseAuth.getCurrentUser();

            databaseReference.child(user.getUid()).setValue(dataUser);
        }
    }

    private boolean isValid() {

        boolean valid = true;
        String nama = etNama.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();

        if (nama.isEmpty()) {
            etNama.setError("Harap isikan nama");
            valid = false;
        } else {
            etNama.setError(null);
        }
        if (alamat == null) {
            etNama.setError("Harap isikan alamat");
            valid = false;
        } else {
            etNama.setError(null);
        }
        return valid;
    }


    @Override
    public void onClick(View v) {
        if (v == btSubmit) {
            saveDataUser();
            startActivity(new Intent(BiodataActivity.this, MainActivity.class));
            finish();
        }
    }
}
