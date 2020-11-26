package com.hossein.ghojavand.pacmanprime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.hossein.ghojavand.pacmanprime.GraphicalViews.EndGameDialog;
import com.hossein.ghojavand.pacmanprime.adapters.ServerInterface;
import com.hossein.ghojavand.pacmanprime.model.Board;
import com.hossein.ghojavand.pacmanprime.model.Cell;
import com.hossein.ghojavand.pacmanprime.model.PacMan;
import com.hossein.ghojavand.pacmanprime.server.ClientHandler;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;


public class MainActivity extends AppCompatActivity implements View.OnClickListener , ServerInterface , Serializable {


    private LinearLayout board_layout;
    private RelativeLayout up_btn , left_btn , right_btn , bottom_btn;
    private TextView my_score_tv;

    public static final int UP = 1 , RIGHT = 2 , BOTTOM = 3 , LEFT = 4;

    private PacMan me ;
    private Board board;

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.background));
        }


        Intent intent = getIntent();
        if (intent.hasExtra("origin"))
        {
            if (intent.getStringExtra("origin").equals("CreateGameActivity"))
            {
                gameManager = CreateGameActivity.gameManager;
            }
            else if (intent.getStringExtra("origin").equals("JoinGameActivity"))
            {
                gameManager = JoinGameActivity.gameManager;
            }
        }

        init();
        up_btn.setOnClickListener(this);
        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
        bottom_btn.setOnClickListener(this);


        gameManager.set_server_interface(this);

        if (gameManager.current_device_mode == GameManager.SERVER) {
            gameManager.send_map_to_all_clients();
        }



    }

    private void init() {
        up_btn = findViewById(R.id.up_btn);
        left_btn = findViewById(R.id.left_btn);
        right_btn = findViewById(R.id.right_btn);
        bottom_btn = findViewById(R.id.bottom_btn);
        my_score_tv = findViewById(R.id.my_score_tv);


        switch (gameManager.my_id)
        {
            case 1:
                me = new PacMan(11 , 0 , PacMan.YELLOW);
                break;
            case 2:
                me = new PacMan(11 , 8 , PacMan.BLUE);
                break;
            case 3 :
                me = new PacMan(0 , 8 , PacMan.RED);
                break;
        }




        board = new Board(MainActivity.this);

        imageViewInitializer();
    }

    public void update_score()
    {
        String score = String.valueOf(me.score);
        my_score_tv.setText(score);
    }

    private void imageViewInitializer() {
        int ID ;
        String SID;
        for(int i=0;i<12;i++)
            for(int j=0;j<9;j++) {
                SID="_"+i+""+j;
                ID=getResources().getIdentifier(SID,"id",getPackageName());
                board.gui[i][j] = findViewById(ID);
            }

    }

    @Override
    public void onClick(View view) {
        if (view.equals(up_btn)) {
            gameManager.request_for_move(UP) ;
//            execute_cmd(UP);
        } else if (view.equals(left_btn)) {
            gameManager.request_for_move(LEFT) ;
//            execute_cmd(LEFT);
        } else if (view.equals(right_btn)) {
            gameManager.request_for_move(RIGHT) ;
//            execute_cmd(RIGHT);
        } else if (view.equals(bottom_btn)) {
            gameManager.request_for_move(BOTTOM) ;
//            execute_cmd(BOTTOM);
        }
    }
    public void show_map_in_cmd()
    {
        for (int i=0 ; i<12 ; i++)
        {
            for(int j =0 ; j<9 ; j++)
            {
                if (board.cells[i][j].has_pacman)
                    System.out.print('p');
                else if (board.cells[i][j].has_fruit)
                    System.out.print('f');
                else if (board.cells[i][j].has_sprint)
                    System.out.print('S');
                else if (board.cells[i][j].is_wall)
                    System.out.print('w');
                else
                    System.out.print(" ");
            }
            System.out.println("");
        }
    }


    /*private void execute_cmd(int direction)
    {
        switch (direction)
        {
            case UP:
                if (can_go(direction)) {
                    if (board.cells[me.iPosition - 1][me.jPosition].has_fruit) {
                        me.score++;
                        board.cells[me.iPosition - 1][me.jPosition].has_fruit = false;
                    }
                    //update logic
                    board.cells[me.iPosition][me.jPosition].has_pacman = false;
                    board.cells[me.iPosition - 1][me.jPosition].has_pacman = true;


                    //update gui
                    board.gui[me.iPosition][me.jPosition].setImageDrawable(null);
                    board.gui[me.iPosition - 1][me.jPosition].setImageDrawable(getDrawable(R.drawable.pacman));
                    board.gui[me.iPosition - 1][me.jPosition].setRotation(270);

                    me.iPosition--;

                }
                break;
            case RIGHT:
                if (can_go(direction)) {
                    if (board.cells[me.iPosition][me.jPosition + 1].has_fruit) {
                        me.score++;
                        board.cells[me.iPosition][me.jPosition + 1].has_fruit = false;
                    }
                    //update logic
                    board.cells[me.iPosition][me.jPosition].has_pacman = false;
                    board.cells[me.iPosition][me.jPosition + 1].has_pacman = true;


                    //update gui
                    board.gui[me.iPosition][me.jPosition].setImageDrawable(null);
                    board.gui[me.iPosition][me.jPosition + 1].setImageDrawable(getDrawable(R.drawable.pacman));
                    board.gui[me.iPosition][me.jPosition + 1].setRotation(0);

                    me.jPosition++;


                }
                break;
            case BOTTOM:
                if (can_go(direction)) {
                    if (board.cells[me.iPosition + 1][me.jPosition].has_fruit) {
                        me.score++;
                        board.cells[me.iPosition + 1][me.jPosition].has_fruit = false;
                    }
                    //update logic
                    board.cells[me.iPosition][me.jPosition].has_pacman = false;
                    board.cells[me.iPosition + 1][me.jPosition].has_pacman = true;


                    //update gui
                    board.gui[me.iPosition][me.jPosition].setImageDrawable(null);
                    board.gui[me.iPosition + 1][me.jPosition].setImageDrawable(getDrawable(R.drawable.pacman));
                    board.gui[me.iPosition + 1][me.jPosition].setRotation(90);

                    me.iPosition++;

                }
                break;
            case LEFT:
                if (can_go(direction)) {
                    if (board.cells[me.iPosition][me.jPosition - 1].has_fruit) {
                        me.score++;
                        board.cells[me.iPosition][me.jPosition - 1].has_fruit = false;
                    }
                    //update logic
                    board.cells[me.iPosition][me.jPosition].has_pacman = false;
                    board.cells[me.iPosition][me.jPosition - 1].has_pacman = true;


                    //update gui
                    board.gui[me.iPosition][me.jPosition].setImageDrawable(null);
                    board.gui[me.iPosition][me.jPosition - 1].setImageDrawable(getDrawable(R.drawable.pacman));
                    board.gui[me.iPosition][me.jPosition - 1].setRotation(180);

                    me.jPosition--;

                }
                break;
        }
        update_score();
    }*/

    @Override
    public void notifyMapChanged(byte[][] map , int score) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String s = String.valueOf(score);
                my_score_tv.setText(s);
            }
        });

        board.refresh(map);
    }

    @Override
    public void notifyClientConnected(ClientHandler client) {

    }


    public void handle_end_game(ArrayList<Integer> ranks) {
        EndGameDialog dialog = new EndGameDialog(MainActivity.this) ;
        dialog.setCancelable(false);
        dialog.show();
    }
}