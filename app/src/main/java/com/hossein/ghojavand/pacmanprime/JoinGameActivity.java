package com.hossein.ghojavand.pacmanprime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinGameActivity extends AppCompatActivity {

    private EditText ip_et;
    private Button join_game_btn;

    public static GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.background));
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        init();
        gameManager = new GameManager(getApplicationContext() , GameManager.CLIENT);

        join_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ip_et.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getApplicationContext() , "لطفا آدرس سرور را وارد کنید" , Toast.LENGTH_LONG).show();
                }
                else
                {
                    String ip_port = ip_et.getText().toString().trim();
                    String [] parts = ip_port.split(":");

                    if (parts.length == 2)
                    {
                        String ip = parts[0];
                        int port = Integer.parseInt(parts[1]);
                        if(gameManager.join_game(ip , port)) {
                            join_game_btn.setEnabled(false);

                            Toast.makeText(getApplicationContext(), "لطفا تا شروع بازی منتظر بمانید", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "اتصال به سرور با خطا مواجه گردید" , Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext() , "آدرس وارد شده صحیح نمی‌باشد" , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void init() {
        ip_et = findViewById(R.id.ip_et);
        join_game_btn = findViewById(R.id.start_game_btn);
    }
}