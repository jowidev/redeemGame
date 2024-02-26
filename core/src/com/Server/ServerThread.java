package com.Server;

import com.Troops.BaseTroop;
import com.Troops.Slime;
import com.Troops.TeamTroops.MoneySlime;
import com.badlogic.gdx.utils.compression.lzma.Base;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private static final int SERVER_PORT = 5000;

    private int clientes = 0;
    private DatagramSocket serverSocket;
    private List<Connection> clients;

    boolean fin = false;

    public boolean ready;


    public ServerThread(ServerScreen serverScreen) {
        try {
            // Inicializar el socket del servidor en el puerto especificado
            serverSocket = new DatagramSocket(SERVER_PORT);
            clients = new ArrayList<>();
            System.out.println("Server activo.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(!this.fin) {
            byte[] data = new byte[1024];
            DatagramPacket dp = new DatagramPacket(data,data.length);
            try {
                this.serverSocket.receive(dp);
                processMessage(dp);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private void processMessage(DatagramPacket dp) { //procesa el msj del cliente

        String msg = (new String(dp.getData())).trim();
        String[] msgComponents = msg.split("#");
        System.out.println("msg "+msg);
        int id = verificarExisteCliente(dp);
        switch (msgComponents[0]){
            case "connect":
                if(clientes >= 2) return;
                else {
                    Connection c= new Connection(dp.getAddress(), dp.getPort());
                    sendEvent("connected#"+clientes,c);
                    clients.add(c);
                    clientes++;
                }
                if(clientes == 2){
                    broadcastEvent("ready");
                    ready = true;
                }
                break;
            case "plant":
                // plant # cell # tropa
                if(id == -1) return; //ningun cliente conectado
                int cell = Integer.valueOf(msgComponents[1]);
                Globals.TROOP t = Globals.TROOP.valueOf(msgComponents[2]);
                if((t.equals(Globals.TROOP.MONEY_SLIME) || t.equals(Globals.TROOP.SHIELD_SLIME) || t.equals(Globals.TROOP.SHOOTER_SLIME)) && id == 0){
                    // Slime
                    BaseTroop slime = Globals.troopFactory(Globals.TROOP.valueOf(msgComponents[2]), Globals.player0Money, Globals.serverScreen.bulletArr);
                    if(Globals.player0Money >= slime.troopCost) {
                        Globals.player0Money -= slime.troopCost;
                        Globals.serverScreen.placeTroop(slime,cell);
                        broadcastEvent("planted#"+cell+"#"+msgComponents[2]);
                        Globals.server.sendEvent("money#"+Globals.player0Money, Globals.server.getClients().get(0));
                    }
                } else if(id == 1){
                    // boulder
                    BaseTroop boulder = Globals.troopFactory(Globals.TROOP.valueOf(msgComponents[2]), Globals.player0Money, Globals.serverScreen.bulletArr);
                    if(Globals.player1Money >= boulder.troopCost) {
                        Globals.player1Money -= boulder.troopCost;
                        Globals.serverScreen.placeTroop(boulder,cell);
                        broadcastEvent("planted#"+cell+"#"+msgComponents[2]);
                        Globals.server.sendEvent("money#"+Globals.player1Money, Globals.server.getClients().get(1));
                    }
                } else return;

                break;
            default:
                return;
        }
    }



    public void sendEvent(String event, Connection client) {
        byte[] data = event.getBytes();
        DatagramPacket dp = new DatagramPacket(data,data.length,client.getIp(),client.getPuerto());
        try {
            System.out.println(dp);
            serverSocket.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void broadcastEvent(String event) {
        System.out.println("BC "+event);
        for(int i = 0; i < clientes; i++){
            sendEvent(event, clients.get(i));
        }
    }

    private int verificarExisteCliente(DatagramPacket dp) {
        for(Connection c : clients){
            if(c.getIp().equals(dp.getAddress()) && c.getPuerto() == dp.getPort()){
                return clients.indexOf(c);
            }
        }
        return -1;
    }

    public void reset(){
        ready = false;
        clients.clear();
        clientes = 0;
        Globals.player0Money = Globals.INITIAL_MONEY;
        Globals.player1Money = Globals.INITIAL_MONEY;
    }
    public List<Connection> getClients() {
        return clients;
    }
}