package network;

import application.Main;
import application.Message;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServerHandler extends Listener{
    static Server server;
    static int udpPort = 54555, tcpPort = 54777;
   // static boolean received = false;


    public static void start(){
        try {
            disableWarning();
            server = new Server();
            Register.register(server.getKryo());
            server.bind(tcpPort,udpPort);
            server.start();

            server.addListener(new ServerHandler());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            Main.g.taChat.append("["+LocalTime.now().format(dtf) +  " Connected as Host]\n");
            Main.isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void kill(){
        server.close();
        server = null;
    }

    private static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void send(Object o) {
        if(o instanceof Message){
            Message m = (Message) o;
            Main.g.taChat.append(m.getMessage());
            server.sendToAllTCP(o);
        }
    }

    public void connected(Connection c){
        Main.g.taChat.append("["+c.getRemoteAddressTCP().getHostString() + " Connected]\n");
    }

    public void received(Connection c, Object o){

        if(o instanceof Message){
            Message m = (Message) o;
            Main.g.taChat.append(m.getMessage());
            server.sendToAllExceptTCP(c.getID(), m);
        }

    }

    public void disconnected(Connection c){
        Main.g.taChat.append("["+c.getRemoteAddressTCP().getHostString() + " Disconnected]\n");
    }

    public static void close(){
        Main.g.taChat.append("[Disconnected]\n");
        server.close();
    }


}
