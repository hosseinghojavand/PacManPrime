package com.hossein.ghojavand.pacmanprime.client;

import com.hossein.ghojavand.pacmanprime.adapters.ClientInterface;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class ServerConnection  implements Runnable , Serializable{

    private Socket server;
    public ClientInterface clientInterface;
    private DataInputStream dis;

    public ServerConnection(Socket s , ClientInterface clientInterface) throws IOException
    {
        this.clientInterface = clientInterface;
        server = s;
        dis=new DataInputStream(s.getInputStream());
    }

    @Override
    public void run() {

        try {
            while (true) {
                byte[] data = new byte[1000];
                int count = dis.read(data);


                clientInterface.onClientDataReceived(data , count);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
