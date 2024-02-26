package com.Server;

import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameScreen;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client extends Thread{
    static DatagramSocket clientSocket;
    static int serverPort;
    static InetAddress serverAddress;
    private final GameScreen gameScreen;
    boolean end;

    public Client(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        try {
            clientSocket = new DatagramSocket(); //auto
            serverAddress = InetAddress.getByName("127.0.0.1");
            serverPort = 5000; //tiene que ser el mismo que el del server
            String message = "connect"; //msj al server
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);


            //clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void processEvent(DatagramPacket dp) { //el cliente recibe msjs del server y los procesa
        String msg = (new String(dp.getData())).trim();
        String[] msgComponents = msg.split("#");
        System.out.println("SV MSG "+msg);
        //algo#otro algo
        switch (msgComponents[0]){ //se usa el algo
            case "connected":
                Globals.clientId = Integer.valueOf(msgComponents[1]);
                Globals.clientScreen.setTeam(Globals.clientId == 0 ? TeamScreen.Team.SLIME : TeamScreen.Team.BOULDER);
                break;
            case "ready":
                Globals.ready = true;
                break;
            case "planted":
                int cell = Integer.valueOf(msgComponents[1]);
                // planted # 0 # MONEY_SLIME
                Globals.clientScreen.placeTroop(Globals.troopFactory(Globals.TROOP.valueOf(msgComponents[2]), Globals.clientScreen.money, Globals.clientScreen.bulletArr), cell);
                break;
            case "money":
                // money#150
                float money = Float.valueOf(msgComponents[1]);
                Globals.clientScreen.money = money;
                break;
            case "timer":
                // timer # 50
                int time = Integer.valueOf(msgComponents[1]);
                Globals.clientScreen.getHUD().setTime(time);
                break;
            case "over":
                // over#0/1
                int winner = Integer.valueOf(msgComponents[1]);
                Globals.over = winner;
            default:
                return;
        }
    }


    public void sendEventToServer(String event) {
        byte[] data = event.getBytes();
        DatagramPacket dp = new DatagramPacket(data,data.length, serverAddress, serverPort);
        try {
            clientSocket.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("EVENTO CLIENTE "+event);
    }
 public static void placeTroopServer(Rectangle hitbox, String team) {
    try {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            String message =team+":"+(int)hitbox.x + ":" + (int)hitbox.y;
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @Override
    public void run() {
        while(!end) {
            byte[] data = new byte[1024];
            DatagramPacket dp = new DatagramPacket(data,data.length);
            try {
                clientSocket.receive(dp);
                processEvent(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
