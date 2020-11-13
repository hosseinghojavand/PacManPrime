package com.hossein.ghojavand.pacmanprime.model;

import android.widget.ImageView;

import java.util.List;

public class Board {
    public Cell [][] cells;
    public ImageView[][] gui;

    public Board()
    {
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