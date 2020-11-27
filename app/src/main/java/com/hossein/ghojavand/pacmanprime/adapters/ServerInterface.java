package com.hossein.ghojavand.pacmanprime.adapters;

import com.hossein.ghojavand.pacmanprime.server.ClientHandler;

import java.io.Serializable;

public interface ServerInterface extends Serializable {
    void onClientConnected(ClientHandler client);

    void onMapChanged(byte[][] map , int score);

    void onGameEnded(byte [] data);
}
