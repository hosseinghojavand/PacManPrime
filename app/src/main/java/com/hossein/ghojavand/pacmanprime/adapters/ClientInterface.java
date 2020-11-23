package com.hossein.ghojavand.pacmanprime.adapters;

import java.io.Serializable;

public interface ClientInterface extends Serializable {

    public void notifyDataReceived(byte[] data , int length);
}
