package vsga.sidiq.validasilogin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;

public class RegisterActivity extends AppCompatActivity {
    EditText username,email,password,address,asalSekolah,fullName;
    Button btnSave;
    private static final int REQUEST_PERMISSION = 201;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.edUsername);
        email = findViewById(R.id.edEmail);
        password = findViewById(R.id.edPassword);
        address = findViewById(R.id.edAddress);
        asalSekolah = findViewById(R.id.edAsalSekolah);
        fullName = findViewById(R.id.edFullName);
        btnSave = findViewById(R.id.btnSimpan);

        btnSave.setOnClickListener(view -> checkPermission());


    }

    void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            createFile();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    createFile();
                } else {
                    Toast.makeText(this, "Permission is required", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(this, "Permission is required", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    void createFile() {

        if(username.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("")
        || address.getText().toString().equals("") || asalSekolah.getText().toString().equals("") || fullName.getText().toString().equals("")){
            Toast.makeText(this,"Please insert all form", Toast.LENGTH_SHORT).show();
        }else{
            File file = new File(getFilesDir(), username.getText().toString().concat(".txt"));
            FileOutputStream outputStream = null;

            String content = username.getText().toString() + ":"+password.getText().toString()+":"+email.getText().toString()+
                    ":"+address.getText().toString()+":"+asalSekolah.getText().toString()+":"+fullName.getText().toString();
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, true);
                outputStream.write(content.getBytes());
                outputStream.flush();
                outputStream.close();
                Toast.makeText(this, "Register is success save", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}