package examplepagination.com.pagination_zoho_practice.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import examplepagination.com.pagination_zoho_practice.R;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(SplashScreen.this, "splash screen", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                              finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
