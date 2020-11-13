package com.hossein.ghojavand.pacmanprime.model;

public class Cell {
    public boolean has_pacman;
    public boolean has_fruit;
    public boolean is_wall;
    public boolean has_sprint;


    public Cell()
    {
        this.has_pacman = false;
        this.has_fruit = false;
        this.is_wall = false;
        this.has_sprint = false;
    }

    public Cell(boolean has_pacman , boolean has_fruit ,boolean is_wall , boolean has_sprint)
    {
        this.has_pacman = has_pacman;
        this.has_fruit = has_fruit;
        this.is_wall = is_wall;
        this.has_sprint = has_sprint;
    }

    public boolean isEmpty()
    {
        if (!has_fruit && !has_pacman && !has_sprint)
            return true;
        else
            return false;
    }
}
