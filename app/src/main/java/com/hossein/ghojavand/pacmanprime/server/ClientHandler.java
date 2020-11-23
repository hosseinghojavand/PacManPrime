package com.hossein.ghojavand.pacmanprime.server;

import com.hossein.ghojavand.pacmanprime.adapters.ServerRequestsInterface;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable , Serializable {

    private Socket client;
    private ArrayList<ClientHandler> clients;
    public DataInputStream dis;
    public DataOutputStream dout;
    private ServerRequestsInterface serverRequestsInterface;

    public  ClientHandler(Socket clientSocket , ArrayList<ClientHandler> clients , ServerRequestsInterface serverRequestsInterface) throws IOException
    {
        this.serverRequestsInterface = serverRequestsInterface;
        this.client = clientSocket;
        this.clients = clients;
        dis=new DataInputStream(client.getInputStream());
        dout=new DataOutputStream(client.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {

                byte [] request = new byte[1000];
                int count = dis.read(request);

                serverRequestsInterface.onRequestReceived(this , request , count);

            }
        }
        catch (IOException e) {
            System.err.println("IO exception in client handler");
            System.err.println(e.getStackTrace());
        }
        finally {
            try {
                dout.close();
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
