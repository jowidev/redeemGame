package com.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int SERVER_PORT = 5000;
    private int chabonesConectados = 0;
    private DatagramSocket serverSocket;
    private List<ClientInfo> clients;
    public Server() {
        try {
            // Inicializar el socket del servidor en el puerto especificado
            serverSocket = new DatagramSocket(SERVER_PORT);

            // Inicializar la lista de clientes
            clients = new ArrayList<>();

            System.out.println("Server activo.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para iniciar el servidor
    public void startServer() {
        try {
            byte[] receiveData = new byte[1024];

            while (true) {
                // Esperar a recibir un paquete
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // Obtener la dirección IP y el puerto del cliente
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Verificar si el cliente ya está registrado
                if (chabonesConectados <3) {
                    if (!clientExists(clientAddress, clientPort)) {
                        // Nuevo cliente, registrar su dirección y puerto
                        clients.add(new ClientInfo(clientAddress, clientPort));
                        chabonesConectados++;

                    }
                } else {
                    String rejectionMessage = "Server is full. Try again later.";
                    sendToClientIfFull(rejectionMessage, clientAddress, clientPort);
                    continue;
                }

                // Obtener el mensaje del cliente
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client said: " + receivedMessage);

                // Enviar el mensaje a todos los clientes
                sendToAllClients(receivedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToClientIfFull(String message, InetAddress clientAddress, int clientPort) {
        try {
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);
            System.out.println("Message sent to client: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar si un cliente ya está registrado
    private boolean clientExists(InetAddress address, int port) {
        for (ClientInfo client : clients) {
            if (client.getAddress().equals(address) && client.getPort() == port) {
                return true;
            }
        }
        return false;
    }

    // Método para enviar un mensaje a todos los clientes
    private void sendToAllClients(String message) { //le manda a los chabones conectados{
        byte[] sendData = message.getBytes();

        for (ClientInfo client : clients) {
            try {
                // Crear un paquete con el mensaje y enviarlo al cliente
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, client.getAddress(), client.getPort());
                serverSocket.send(sendPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método principal del servidor
    public static void main(String[] args) {
        // Crear una instancia del servidor y comenzar la ejecución
        Server server = new Server();
        server.startServer();
    }

    // Clase interna para representar la información de un cliente
    private static class ClientInfo {
        private InetAddress address;
        private int port;

        public ClientInfo(InetAddress address, int port) {
            this.address = address;
            this.port = port;
        }

        public InetAddress getAddress() {
            return address;
        }

        public int getPort() {
            return port;
        }
    }
}