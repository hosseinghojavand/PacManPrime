package com.hossein.ghojavand.pacmanprime.server;


import com.hossein.ghojavand.pacmanprime.adapters.ServerInterface;
import com.hossein.ghojavand.pacmanprime.adapters.ServerRequestsInterface;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.hossein.ghojavand.pacmanprime.GameManager.scores;

public class Server implements Serializable {

    private boolean is_server_on = true;

    private  ArrayList<ClientHandler> clients = new ArrayList<>();
    private final ExecutorService pool = Executors.newFixedThreadPool(4);
    private final ServerSocket listener;

    private ServerRequestsInterface serverRequestsInterface;
    private ServerInterface serverInterface ;

    public Server(String ip_address ,int port , ServerInterface serverInterface , ServerRequestsInterface serverRequestsInterface) throws IOException
    {
        this.serverRequestsInterface = serverRequestsInterface;
        InetAddress server_ip = InetAddress.getByName(ip_address);
        listener = new ServerSocket(port, 50, server_ip);
        ServerListener serverListener = new ServerListener();
        new Thread(serverListener).start();
        this.serverInterface = serverInterface;
    }


    public void turn_off()
    {
        is_server_on = false;
    }


    public void send_to_all(byte [] request)
    {
        int i =1;
        for (ClientHandler aClient: clients)
        {
            try {
                if (request.length ==111)
                {
                    request[1] = (byte) scores[i-1];
                    request[110] = (byte) i;
                    i++;
                }
                aClient.dout.write(request);
                aClient.dout.flush();
            }
            catch (IOException ignored)
            {

            }
        }
    }



    public class ServerListener implements Runnable , Serializable{

        @Override
        public void run() {
            while (is_server_on) {
                try {

                    Socket client = listener.accept();

                    ClientHandler clientThread = new ClientHandler(client, clients , serverRequestsInterface);
                    clients.add(clientThread);
                    pool.execute(clientThread);


                    serverInterface.notifyClientConnected(clientThread);
                } catch (IOException ignored) {

                }
            }
            try {
                listener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
