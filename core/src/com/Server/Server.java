package com.Server;

import com.Troops.BaseTroop;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(5000);
            byte[] receiveData = new byte[1024]; //dedicar espacio para guardar paquete //basicamente el buffer
            System.out.println("El servidor está listo para recibir mensajes.");
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); //el length 1024 x el byte //creas un datagrampacket vacio
                serverSocket.receive(receivePacket); //lo llenas con la info del buffer
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                System.out.println("el otro cliente dijo: " + receivedMessage);
                //processTroopCoords(receivedMessage);


                //Obtener la dirección IP y el puerto del cliente
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Preparar el mensaje de respuesta
                String responseMessage = "Mensaje recibido correctamente por el servidor.";
                byte[] sendData = responseMessage.getBytes();

                // Crear un nuevo DatagramPacket para enviar la respuesta al cliente
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);  // Enviar el mensaje de respuesta al cliente

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static void processTroopCoords(String coords) {
        // Parse the coordinates and update the game state
        // Example: Split the coordinates string and update the game state accordingly
        String[] coordinateValues = coords.split(":");
        int x = Integer.parseInt(coordinateValues[0]);
        int y = Integer.parseInt(coordinateValues[1]);

    }
}
