package vsga.sidiq.validasilogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final int DELAY_SPLASH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(this::checkLogin,DELAY_SPLASH);
    }

    private void checkLogin() {
        File sdcard = getFilesDir();
        File file = new File(sdcard, "Login.txt");
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
            Intent intent = new Intent(this, DasboardActivity.class);
            intent.putExtra("username",dataUser[0]);
            startActivity(intent);
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}