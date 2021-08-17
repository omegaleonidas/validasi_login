package vsga.sidiq.validasilogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DasboardActivity extends AppCompatActivity {
    TextView username, password, address, fullName, asalSekolah, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        username = findViewById(R.id.edUsername);
        email = findViewById(R.id.edEmail);
        password = findViewById(R.id.edPassword);
        address = findViewById(R.id.edAddress);
        asalSekolah = findViewById(R.id.edAsalSekolah);
        fullName = findViewById(R.id.edFullName);
        readFile();
    }

    private void readFile() {
        File sdcard = getFilesDir();
        String fileName = getIntent().getStringExtra("username");
        File file = new File(sdcard, fileName.concat(".txt"));
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
            Log.e("login: ", data);
            String[] dataUser = data.split(":");
            username.setText(dataUser[0]);
            password.setText(dataUser[1]);
            email.setText(dataUser[2]);
            address.setText(dataUser[3]);
            asalSekolah.setText(dataUser[4]);
            fullName.setText(dataUser[5]);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        File file = new File(getFilesDir(), "Login.txt");
        if (file.exists()) {
            file.delete();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

}