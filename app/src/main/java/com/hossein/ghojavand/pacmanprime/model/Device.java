package com.hossein.ghojavand.pacmanprime.model;

import com.hossein.ghojavand.pacmanprime.server.ClientHandler;

import java.io.Serializable;

public class Device implements Serializable {

    public String name ;
    public int id;
    public ClientHandler client;

    public Device(int id , ClientHandler client)
    {
        this.id = id;
        this.client = client;
    }
}
