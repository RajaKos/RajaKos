package xiirpl508182838.project.smktelkom_mlg.sch.id;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import xiirpl508182838.project.smktelkom_mlg.sch.id.db.DataKos;

public class TambahActivity extends AppCompatActivity {

    private static final int GALLERY_INTENT = 2;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText etNamaKos, etAlamat, etNamaPemilik, etKontakPemilik, etHargaBulanan, etHargaSemester;
    private RadioGroup rgStatus, rgJenis;
    private TextView tvFasilitas;
    private Button btGaleri, btSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        etNamaKos = (EditText) findViewById(R.id.namakost);
        etAlamat = (EditText) findViewById(R.id.alamat);
        etNamaPemilik = (EditText) findViewById(R.id.namapemilik);
        etKontakPemilik = (EditText) findViewById(R.id.kontakpemilik);
        etHargaBulanan = (EditText) findViewById(R.id.hargabulanan);
        etHargaSemester = (EditText) findViewById(R.id.hargasemester);
        rgJenis = (RadioGroup) findViewById(R.id.radioGroupJenis);
        rgStatus = (RadioGroup) findViewById(R.id.radioGroupStatus);
        btGaleri = (Button) findViewById(R.id.buttonGaleri);
        btSubmit = (Button) findViewById(R.id.buttonSimpan);
        storageReference = FirebaseStorage.getInstance().getReference();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaKos = etNamaKos.getText().toString().trim();
                String alamat = etAlamat.getText().toString().trim();
                String namaPemilik = etNamaPemilik.getText().toString().trim();
                String kontakPemilik = etKontakPemilik.getText().toString().trim();
                String hargaBulanan = etHargaBulanan.getText().toString().trim();
                String hargaSemester = etHargaSemester.getText().toString().trim();
                String radioStatus = ((RadioButton) findViewById(rgStatus.getCheckedRadioButtonId())).getText().toString();
                String radioJenis = ((RadioButton) findViewById(rgJenis.getCheckedRadioButtonId())).getText().toString();

                DataKos dataKos = new DataKos(namaKos, alamat, namaPemilik, kontakPemilik, hargaBulanan, hargaSemester, radioStatus, radioJenis);
                FirebaseUser user = firebaseAuth.getCurrentUser();

                databaseReference.child("Data Kos").child(user.getUid()).setValue(dataKos);

            }
        });

        btGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StorageReference filePath = storageReference.child("photos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(TambahActivity.this, "Upload Berhasil", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
