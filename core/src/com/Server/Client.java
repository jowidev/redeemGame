package com.Server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client extends Thread{
    static DatagramSocket clientSocket;
    static int serverPort;
    static InetAddress serverAddress;
    public Client() {
        try {
            clientSocket = new DatagramSocket(); //auto
            serverAddress = InetAddress.getByName("127.0.0.1");
            serverPort = 5000; //tiene que ser el mismo que el del serevr
            String message = "hola profe";
            byte[] sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);


            //clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void placeSlime(boolean slimeOnMouse, Rectangle slimeHitbox) {
        try {
            // Check if slime is on the mouse and mouse left button is just pressed
            if (slimeOnMouse && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                String message = "Slime placed at x:" + slimeHitbox.x + ", y:" + slimeHitbox.y; // Construct the message
                byte[] sendData = message.getBytes();
                // Create and send the DatagramPacket to the server
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                clientSocket.send(sendPacket);
            }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }


    public static void placeBoulder(boolean boulderOnMouse, Rectangle boulderHitbox) {
        try {
            // Check if the boulder is interacted with
            if (boulderOnMouse && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                String message = "Boulder placed at x:" + boulderHitbox.x + ", y:" + boulderHitbox.y; // Construct the message
                byte[] sendData = message.getBytes();
                // Create and send the DatagramPacket to the server
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
            System.out.println("el otro chabon dijo: " + receivedMessage);
        }


        //super.run();
    }
}
