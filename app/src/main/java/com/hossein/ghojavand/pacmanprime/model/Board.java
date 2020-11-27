package com.hossein.ghojavand.pacmanprime.model;

import android.content.Context;
import android.graphics.ImageDecoder;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.hossein.ghojavand.pacmanprime.MainActivity;
import com.hossein.ghojavand.pacmanprime.R;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board implements Serializable {
    public Cell [][] cells;
    public ImageView[][] gui;

    private MainActivity context;

    public Board(MainActivity context)
    {
        this.context = context;
        cells = new Cell[12][9];
        gui = new ImageView[12][9];

        for (int i = 0 ; i < 12 ; i++)
        {
            for(int j =0 ; j <9 ; j++) {

                if ((i == 0 && j == 8) || (i == 11 && (j == 0 || j == 8))) {
                    //has packman
                    cells[i][j] = new Cell(true, false, false, false);
                }
                else if (i == 5 && j == 4) {
                    //empty above sprint
                    cells[i][j] = new Cell(false, false, false, false);
                }
                else if (i == 6 && j == 4) {
                    //has sprint
                    cells[i][j] = new Cell(false, false, false, true);
                }
                else if (
                        (i == 1 && (j == 2 || j == 3 || j == 5 || j == 6)) ||
                                (i == 2 && (j == 0 || j == 8)) ||
                                (i == 3 && (j == 0 || j == 2 || j == 3 || j == 4 || j == 5 || j == 6 || j == 8)) ||
                                (i == 5 && (j == 1 || j == 3 || j == 5 || j == 7)) ||
                                (i == 6 && (j == 3 || j == 5)) ||
                                (i == 7 && (j == 0 || j == 3 || j == 4 || j == 5 || j == 8)) ||
                                (i == 8 && (j == 0 || j == 8)) ||
                                (i == 9 && (j == 2 || j == 4 || j == 6)) ||
                                (i == 10 && (j == 1 || j == 2 || j == 4 || j == 6 || j == 7))
                )
                {
                    //these are walls
                    cells[i][j] = new Cell(false, false, true, false);
                }
                else {
                    // these are cells which have fruits in them;
                    cells[i][j] = new Cell(false, true, false, false);
                }


            }
        }
    }

    public void refresh(byte[][] map )
    {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ; i < 12 ; i++)
                {
                    for(int j =0 ; j <9 ; j++) {
                        if (map[i][j] == 0)
                        {
                            cells[i][j] = new Cell(false, false, false, false);
                            gui[i][j].setImageDrawable(null);
                        }
                        if (map[i][j] == 1)
                        {
                            cells[i][j] = new Cell(true, false, false, false);
                            gui[i][j].setImageDrawable(context.getApplicationContext().getDrawable(R.drawable.pacman));
                            gui[i][j].setColorFilter(ContextCompat.getColor(context.getApplicationContext(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                        }
                        if (map[i][j] == 2)
                        {
                            cells[i][j] = new Cell(true, false, false, false);
                            gui[i][j].setImageDrawable(context.getApplicationContext().getDrawable(R.drawable.pacman));
                            gui[i][j].setColorFilter(ContextCompat.getColor(context.getApplicationContext(), R.color.blue));
                        }
                        if (map[i][j] == 3)
                        {
                            cells[i][j] = new Cell(true, false, false, false);
                            gui[i][j].setImageDrawable(context.getApplicationContext().getDrawable(R.drawable.pacman));
                            gui[i][j].setColorFilter(ContextCompat.getColor(context.getApplicationContext(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                        }
                        else if(map[i][j] == 4)
                        {
                            cells[i][j] = new Cell(false, false, false, true);
                            gui[i][j].setImageDrawable(context.getApplicationContext().getDrawable(R.drawable.sprint));
                        }
                        else if(map[i][j] == 5)
                        {
                            cells[i][j] = new Cell(false, false, true, false);
                            //wall does not need to be loaded again
                        }
                        else if(map[i][j] == 6)
                        {
                            cells[i][j] = new Cell(false, true, false, false);
                            gui[i][j].setImageDrawable(context.getApplicationContext().getDrawable(R.drawable.fruit));
                        }
                    }
                }
            }
        });


    }
}
/*

  if (!(  (i==0 && j==8) ||
                        (i==1 && (j==2 || j== 3 || j==5 || j==6)) ||
                        (i==2 && (j==0 || j==8)) ||
                        (i==3 && (j==0 || j==2 || j==3 || j==4 || j==5 || j==6 ||j== 8))||
                        (i==5 && (j==1 || j==3 || j==4 || j==5 || j==7))||
                        (i==6 && (j==3 || j==4 || j==5 ))||
                        (i==7 && (j==0 || j==3 || j==4 || j==5 ||j== 8))||
                        (i==8 && (j==0 ||j== 8))||
                        (i==9 && (j==2 || j==4 || j==6))||
                        (i==10&& (j==1 || j==2 || j==4 || j==6 || j==7))||
                        (i==11 &&(j==0 ||j== 8))
                     )
                )
                {

                }
* */