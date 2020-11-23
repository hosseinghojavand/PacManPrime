package com.hossein.ghojavand.pacmanprime.adapters;

import com.hossein.ghojavand.pacmanprime.server.ClientHandler;

import java.io.Serializable;

public interface ServerRequestsInterface extends Serializable {

    public void onRequestReceived(ClientHandler client , byte[] request , int length);

}
