package network;

import application.Main;
import application.Message;
import com.esotericsoftware.kryonet.*;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler extends Listener{
    static Client client;
    static int udpPort = 54555, tcpPort = 54777;
    public static String ip = /*"25.79.40.26"*/ "127.0.0.1";
   // static boolean received = false;

    public static void setup(){

            disableWarning();

            client = new Client();

            Register.register(client.getKryo());

    }

    public static void start(){
        try {
            client.start();

            client.connect(5000, ip, tcpPort, udpPort);

            client.addListener(new ClientHandler());
            Main.isConnected = true;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            Main.g.taChat.append("["+ LocalTime.now().format(dtf) +  " Connected as Client]\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void kill(){
        client.close();
        client = null;
    }

    public static InetAddress discover(){
        return client.discoverHost(tcpPort, 2000);
    }

    private static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void send(Object o){
        if(o instanceof Message){
            Message m = (Message) o;
            Main.g.taChat.append(m.getMessage());
            client.sendTCP(m);
        }
    }

    public void received(Connection c, Object o){
        if(o instanceof Message){
            Message m = (Message) o;
            Main.g.taChat.append(m.getMessage());
        }
    }


}
