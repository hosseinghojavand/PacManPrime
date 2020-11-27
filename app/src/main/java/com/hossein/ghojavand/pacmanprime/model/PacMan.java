package com.hossein.ghojavand.pacmanprime.model;

import java.io.Serializable;

public class PacMan  implements Serializable {
    public final static int RED = 3 , BLUE =2 , YELLOW = 1;

    public int iPosition; // 0 - 11
    public int jPosition; // 0 - 8
    public int score;
    public int color;

    public PacMan(int iPosition , int jPosition , int color)
    {
        this.iPosition = iPosition;
        this.jPosition = jPosition;
        score = 0;
        this.color = color;
    }


}
