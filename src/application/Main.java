package application;

import gui.Gui;

public class Main {
    public static boolean isHost;
    public static boolean isConnected;
    public static Gui g;
    public static void main(String[] args) {
        g = new Gui();
        g.create();
    }
}
