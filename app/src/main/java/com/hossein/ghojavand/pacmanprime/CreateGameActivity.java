package com.hossein.ghojavand.pacmanprime;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hossein.ghojavand.pacmanprime.adapters.ConnectedDevicesAdapter;
import com.hossein.ghojavand.pacmanprime.adapters.ServerInterface;
import com.hossein.ghojavand.pacmanprime.model.Device;
import com.hossein.ghojavand.pacmanprime.server.ClientHandler;
import com.hossein.ghojavand.pacmanprime.server.Server;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.hossein.ghojavand.pacmanprime.GameManager.devices;

public class CreateGameActivity extends AppCompatActivity implements ServerInterface , Serializable {

    private TextView ip_tv;
    private RecyclerView friends_rv;
    private ConnectedDevicesAdapter adapter;



//    public static GameManager gameManager;
    public static GameManager gameManager;

    private Button start_game_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

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
        initAdapter();

        gameManager = new GameManager(getApplicationContext() , GameManager.SERVER);
        gameManager.create_game(this);

        start_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int res = gameManager.start_game();


                if (res == 0)
                {
                    gameManager.tell_clients_game_started();
                    startActivity(new Intent(CreateGameActivity.this , MainActivity.class).putExtra("origin" , "CreateGameActivity"));
                }
                else {
                    String msg = "شما برای شروع بازی به " + res + " بازیکن دیگر نیاز دارید";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    private void init() {
        ip_tv = findViewById(R.id.ip_tv);
        friends_rv = findViewById(R.id.friends_rv);
        start_game_btn = findViewById(R.id.start_game_btn);



        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()) + ":9000";
        ip_tv.setText(ip);
    }

    private void initAdapter()
    {
        adapter = new ConnectedDevicesAdapter(getApplicationContext(), devices);
        friends_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        friends_rv.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateGameActivity.this , MenuActivity.class));
        finish();
    }

    @Override
    public void notifyClientConnected(ClientHandler client) {

        devices.add(new Device(devices.size()+1 , client));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext() , "یک بازیکن اضافه گردید" , Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void notifyMapChanged(byte[][] map) {

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isApOn() {
        WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        try {
            Method method = wifimanager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (Boolean) method.invoke(wifimanager);
        }
        catch (Throwable ignored) {}
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean configApState(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiConfiguration wificonfiguration = null;
        try {
            // if WiFi is on, turn it off
            if(isApOn()) {
                wifimanager.setWifiEnabled(false);
            }
            Method method = wifimanager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(wifimanager, wificonfiguration, !isApOn());
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}