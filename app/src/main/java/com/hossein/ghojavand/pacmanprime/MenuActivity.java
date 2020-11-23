package com.hossein.ghojavand.pacmanprime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MenuActivity extends AppCompatActivity {

    private Button create_game_btn , join_game_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.background));
        }
        init();

        create_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this , CreateGameActivity.class));
                finish();
            }
        });


        join_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this , JoinGameActivity.class));
                finish();
            }
        });


    }

    private void init() {
        create_game_btn = findViewById(R.id.create_game_btn);
        join_game_btn = findViewById(R.id.join_game_btn);
    }
}