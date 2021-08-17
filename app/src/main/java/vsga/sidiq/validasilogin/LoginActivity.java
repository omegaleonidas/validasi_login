package vsga.sidiq.validasilogin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button login,register;
    private static final int REQUEST_PERMISSION = 201;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.edUsername);
        password = findViewById(R.id.edPassword);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);

        login.setOnClickListener(view -> {
            checkPermission();
        });

        register.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            login();
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
                    login();
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

    void saveLogin(){
        File file = new File(getFilesDir(), "Login.txt");
        FileOutputStream outputStream = null;

        String content = username.getText().toString() + ":"+password.getText().toString();
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login() {

        File sdcard = getFilesDir();
        File file = new File(sdcard, username.getText().toString().concat(".txt"));
        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
                Toast.makeText(this, "username anda tidak tersedia", Toast.LENGTH_SHORT).show();
            }
            String data = text.toString();
            Log.e( "login: ",data );
            String[] dataUser = data.split(":");
            if(dataUser[0].equals(username.getText().toString())){
                if(dataUser[1].equals(password.getText().toString())){
                    saveLogin();
                    Intent intent = new Intent(this, DasboardActivity.class);
                    intent.putExtra("username",username.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Password yang anda masukan salah", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "username anda tidak tersedia", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "username anda tidak tersedia", Toast.LENGTH_SHORT).show();
        }
    }
}