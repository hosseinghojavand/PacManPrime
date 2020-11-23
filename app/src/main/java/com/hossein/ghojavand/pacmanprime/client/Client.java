package com.hossein.ghojavand.pacmanprime.client;

import com.hossein.ghojavand.pacmanprime.adapters.ClientInterface;

import java.io.*;
import java.net.Socket;

public class Client {

    DataOutputStream dout;

    public ClientInterface clientInterface;

    public Client (String server_ip , int server_port , ClientInterface clientInterface) throws IOException{

        this.clientInterface = clientInterface;

            Socket socket = new Socket(server_ip, server_port);

            ServerConnection serverConn = new ServerConnection(socket , clientInterface);

            dout = new DataOutputStream(socket.getOutputStream());

            new Thread(serverConn).start();
    }

    public void make_request(byte [] request)
    {
        try {
            dout.write(request);
            dout.flush();
        }catch (Exception ignored)
        {

        }
    }



}
