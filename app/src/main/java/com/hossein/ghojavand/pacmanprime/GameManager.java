package com.hossein.ghojavand.pacmanprime;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.widget.Toast;

import com.hossein.ghojavand.pacmanprime.adapters.ClientInterface;
import com.hossein.ghojavand.pacmanprime.adapters.ServerInterface;
import com.hossein.ghojavand.pacmanprime.adapters.ServerRequestsInterface;
import com.hossein.ghojavand.pacmanprime.client.Client;
import com.hossein.ghojavand.pacmanprime.model.Device;
import com.hossein.ghojavand.pacmanprime.server.ClientHandler;
import com.hossein.ghojavand.pacmanprime.server.Server;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameManager implements ClientInterface , ServerRequestsInterface {

    public static final int SERVER = 1 , CLIENT=2;


    public byte[][] server_map = new byte[12][9];


    int current_device_mode = 0;

    private Context context;
    private Server server;
    private Client client;

    public int my_id = 0;


    ServerInterface serverInterface;

    public static List<Device> devices = new ArrayList<>();

    public GameManager(Context context , int mode)
    {
        this.context = context;
        this.current_device_mode = mode;
        init_server_map();


    }

    public void init_server_map()
    {
        for (int i = 0 ; i < 12 ; i++)
        {
            for(int j =0 ; j <9 ; j++) {
                if(i == 11 && j == 0)
                {
                    //has packman
                    server_map[i][j] = 1;

                }
                else if(i ==11 && j == 8)
                {
                    //has packman
                    server_map[i][j] = 2;
                }
                else if (i == 0 && j == 8) {
                    //has packman
                    server_map[i][j] = 3;
                }
                else if (i == 5 && j == 4) {
                    //empty above sprint
                    server_map[i][j] = 0;
                }
                else if (i == 6 && j == 4) {
                    //has sprint
                    server_map[i][j] = 4;
                }
                else if (
                        (i == 1 && (j == 2 || j == 3 || j == 5 || j == 6)) ||
                                (i == 2 && (j == 0 || j == 8)) ||
                                (i == 3 && (j == 0 || j == 2 || j == 3 || j == 4 || j == 5 || j == 6 || j == 8)) ||
                                (i == 5 && (j == 1 || j == 3 || j == 5 || j == 7)) ||
                                (i == 6 && (j == 3 || j == 5)) ||
                                (i == 7 && (j == 0 || j == 3 || j == 4 || j == 5 || j == 8)) ||
                                (i == 8 && (j == 0 || j == 8)) ||
                                (i == 9 && (j == 2 || j == 4 || j == 6)) ||
                                (i == 10 && (j == 1 || j == 2 || j == 4 || j == 6 || j == 7))
                )
                {
                    //these are walls
                    server_map[i][j] = 5;
                }
                else {
                    // these are cells which have fruits in them;
                    server_map[i][j] = 6;
                }


            }
        }
    }


    public void set_server_interface(ServerInterface server_interface)
    {
        this.serverInterface = server_interface;
    }
    public void create_game(ServerInterface serverInterface)
    {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()) ;
        try {
            server = new Server(ip , 9000 , serverInterface , this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(context ,context.getString(R.string.error_while_running_server) , Toast.LENGTH_LONG).show();
        }


        join_game(ip , 9000);
    }

    public int start_game()
    {
        //returns number of gamers needed to start the game
        if (devices.size() >=3 )
        {
            return 0;
        }
        else
        {
            return (3-devices.size());
        }
    }


    public boolean join_game(String server_ip , int server_port)
    {

        if (current_device_mode == SERVER)
            my_id = 1;
        try {
            client = new Client(server_ip, server_port, this);
            return true;

        }catch (IOException e)
        {
            return false;
        }
    }

    public void tell_clients_game_started()
    {
        // the flag related to start game is 1 and it is told to all of the clients
        byte [] request = new byte[111];

        // set game status to in progress
        request[0] = (byte) (request[0] | (0x80));

        //fill map in request body
        int k = 2;
        for (int i = 0 ; i < 12 ; i++)
        {
            for(int j =0 ; j <9 ; j++)
            {
                request[k] = server_map[i][j];
                k++;
            }
        }


        server.send_to_all(request);
    }

    public void send_map_to_all_clients()
    {
        byte [] request = new byte[111];

        // set game status to in progress
        request[0] = (byte) (request[0] | (0x80));

        //fill map in request body
        int k = 2;
        for (int i = 0 ; i < 12 ; i++)
        {
            for(int j =0 ; j <9 ; j++)
            {
                request[k] = server_map[i][j];
                k++;
            }
        }

        server.send_to_all(request);
    }

    public void request_for_move(int direction) {
        byte [] request = new byte [2] ;

        // byte #0 is for client id
        request[0] = (byte) this.my_id ;

        // byte #1 is for direction
        request[1] = (byte) direction ;

        client.make_request(request);
    }
    private  int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    @Override
    public void notifyDataReceived(byte[] data , int length) {
        // data that is received by client
        byte [][] map = null;
        if (length>=110)
        {
            map = new byte[12][9];
            for (int i = 2 ; i < 110 ; i++)
            {
                map[(i-2)/9][(i-2)%9] = data[i];
            }

            // set my own_id
            my_id = data[110];
        }
        if (length>0)
        {
            if(getBit(data[0] , 8)==1)
            {
                if (current_device_mode == CLIENT) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("origin", "JoinGameActivity");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        }

        if (serverInterface!=null && map!=null)
            serverInterface.notifyMapChanged(map);
    }

    public byte getBit(byte d , int position)
    {
        return (byte) ((d >> position) & 1);
    }

    public boolean is_game_over() {
        // TODO is_game_ove func should be used for detecting whether game has been finished.
        int sum_of_fruits = 0 ;
        int sum_of_pacmans = 0 ;

        for(int i= 0 ; i<12 ; i++) {
            for(int j =0; j<9 ; j++) {
                if(server_map[i][j]>=1 && server_map[i][j]<=3)  sum_of_pacmans ++ ;
                else if(server_map[i][j]==6) sum_of_fruits++ ;
            }
        }

        return sum_of_pacmans <= 1 || sum_of_fruits == 0;
    }

    @Override
    public void onRequestReceived(ClientHandler client , byte[] request , int length) {
        // all request that comes from clients will be directed to here
    }


}
