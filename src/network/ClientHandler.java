package network;

import application.Main;
import application.Message;
import com.esotericsoftware.kryonet.*;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientHandler extends Listener{
    static Client client;
    static int udpPort = 54555, tcpPort = 54777;
    public static String ip = /*"25.79.40.26"*/ "127.0.0.1";
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
   // static boolean received = false;


    public static void start(){
        try {
            disableWarning();
            client = new Client();
            Register.register(client.getKryo());

            client.start();

            client.connect(5000, ip, tcpPort, udpPort);

            client.addListener(new ClientHandler());
            Main.isConnected = true;
            Main.g.btnHost.setEnabled(false);
            Main.g.btnClient.setEnabled(false);
            Main.g.btnDisconnect.setEnabled(true);

            Main.g.taChat.append("["+ LocalTime.now().format(dtf) +  " Connected as Client]\n");
        } catch (IOException e) {
            Main.g.taChat.append("["+ LocalTime.now().format(dtf) +  " No Host found]\n");
            Main.g.btnHost.setEnabled(true);
            Main.g.btnClient.setEnabled(true);
            Main.g.btnDisconnect.setEnabled(false);


           // Main.g.btnDisconnect.setEnabled(false);
            //Main.g.btnHost.setEnabled(true);
            //Main.g.btnClient.setEnabled(true);
            close();
            //e.printStackTrace();
        }
    }


//    public static String discover(){
//        List<InetAddress> list = client.discoverHosts(tcpPort, 2000);
//        for (InetAddress a: list){
//            System.out.println(a.getHostName());
//        }
//        return "";
//    }

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

    public static void close(){
        Main.g.taChat.append("[Disconnected]\n");
        client.close();
    }


}
