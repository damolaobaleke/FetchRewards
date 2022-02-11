package com.fetchrewards.hiring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.fetchrewards.hiring.views.items.ItemsActivity;

public class MainActivity extends AppCompatActivity {
    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splash = findViewById(R.id.splashLogo);

        splash.animate().scaleY(1.4f).scaleX(1.4f).start();
        splash.animate().alpha(1f).setStartDelay(1000).start();

        Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
                startActivity(intent);
            }
        };

        handler.postDelayed(run, 2000);
    }
}