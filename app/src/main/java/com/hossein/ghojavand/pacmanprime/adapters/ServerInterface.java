package com.hossein.ghojavand.pacmanprime.adapters;

import com.hossein.ghojavand.pacmanprime.server.ClientHandler;

import java.io.Serializable;

public interface ServerInterface extends Serializable {
    void notifyClientConnected(ClientHandler client);

    void notifyMapChanged(byte[][] map);
}
