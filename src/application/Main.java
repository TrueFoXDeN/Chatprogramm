package application;

import gui.Gui;
import network.ClientHandler;
import network.ServerHandler;

public class Main {
    public static boolean isHost;
    public static boolean isConnected;
    public static Gui g;
    public static void main(String[] args) {
        g = new Gui();
        g.create();
    }
}
