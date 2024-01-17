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

    public Client(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        try {
            clientSocket = new DatagramSocket(); //auto
            serverAddress = InetAddress.getByName("127.0.0.1");
            serverPort = 5000; //tiene que ser el mismo que el del serevr
            String message = "cliente conectado, hola";
            byte[] sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);


            //clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 public static void placeObject(boolean objectOnMouse, Rectangle objectHitbox, String objectType) {
    try {
        if (objectOnMouse && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            String message = objectType + " placed at x,y:" + objectHitbox.x + ":" + objectHitbox.y;
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
        while (true) {
            byte[] receiveDataClient = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveDataClient, receiveDataClient.length); //el length 1024 x el byte //creas un datagrampacket vacio
            try {
                clientSocket.receive(receivePacket); //lo llenas con la info del buffer
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("el server dijo: " + receivedMessage);
            gameScreen.handleReceivedTroopCoordinates(receivedMessage, TeamScreen.Team.SLIME);
        }


        //super.run();
    }
}
