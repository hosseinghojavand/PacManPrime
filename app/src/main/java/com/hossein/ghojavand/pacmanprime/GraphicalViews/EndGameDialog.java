package com.hossein.ghojavand.pacmanprime.GraphicalViews;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.hossein.ghojavand.pacmanprime.R;

import java.util.ArrayList;
import java.util.Collections;

public class EndGameDialog extends Dialog implements android.view.View.OnClickListener {


    Activity activity  ;
    TextView title , first_place_tv , second_place_tv , third_place_tv ;
    ImageView first_place_iv , second_place_iv , third_place_iv ;
    Button exit_btn ;

    ArrayList<Integer> ranks ;

    public EndGameDialog(@NonNull Activity activity, ArrayList<Integer> ranks) {
        super(activity);
        this.activity = activity ;
        this.ranks = ranks ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.end_gama_dialog);
        init() ;
        exit_btn.setOnClickListener(this);
        bind();
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

    public void bind () {
        int max = Collections.max(this.ranks) ;
        int min = Collections.min(this.ranks) ;

        boolean first_place_captured = false , third_place_captured = false ;

        ArrayList<Pair<Pair<Integer,String>,Integer>> ranks = new ArrayList<>() ;
        ranks.add(new Pair<>(new Pair<>(this.ranks.get(0),"زرد"),R.color.yellow));
        ranks.add(new Pair<>(new Pair<>(this.ranks.get(1),"آبی"),R.color.blue));
        ranks.add(new Pair<>(new Pair<>(this.ranks.get(2),"قرمز"),R.color.red));

        for (Pair<Pair<Integer,String>,Integer> record : ranks ) {
            if (record.first.first == max && !first_place_captured) {
                first_place_captured = true ;
                title.setText(record.first.second);
                first_place_tv.setText(""+record.first.first);
                first_place_iv.setColorFilter(ContextCompat.getColor(this.activity,record.second));
            }
            else if ( record.first.first == min && !third_place_captured)  {
                third_place_captured = true ;
                third_place_tv.setText(""+record.first.first);
                third_place_iv.setColorFilter(ContextCompat.getColor(this.activity,record.second));
            }
            else {
                second_place_tv.setText(""+record.first.first);
                second_place_iv.setColorFilter(ContextCompat.getColor(this.activity,record.second));
            }
        }
    }
}
