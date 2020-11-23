package com.hossein.ghojavand.pacmanprime.model;

import java.io.Serializable;

public class PacMan  implements Serializable {
    public final static int RED = 1 , BLUE =2 , YELLOW = 3;

    public int iPosition;
    public int jPosition;
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
