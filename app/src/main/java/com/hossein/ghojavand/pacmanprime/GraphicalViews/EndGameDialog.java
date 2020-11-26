package com.hossein.ghojavand.pacmanprime.GraphicalViews;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.hossein.ghojavand.pacmanprime.R;

public class EndGameDialog extends Dialog implements android.view.View.OnClickListener {


    Activity activity  ;
    TextView title , first_place_tv , second_place_tv , third_place_tv ;
    ImageView first_place_iv , second_place_iv , third_place_iv ;
    Button exit_btn ;

    public EndGameDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.end_gama_dialog);
        init() ;
        exit_btn.setOnClickListener(this);
    }

    private void init() {
        exit_btn = findViewById(R.id.end_game_exit_btn) ;

        title = findViewById(R.id.end_game_title) ;

        first_place_tv = findViewById(R.id.first_place_tv) ;
        second_place_tv = findViewById(R.id.second_place_tv) ;
        third_place_tv = findViewById(R.id.third_place_tv) ;

        first_place_iv = findViewById(R.id.first_place_iv) ;
        second_place_iv = findViewById(R.id.second_place_iv) ;
        third_place_iv = findViewById(R.id.third_place_iv) ;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.end_game_exit_btn) {
            activity.finish();
            System.exit(0);
        }

    }
}
