package gui;

import network.ClientHandler;
import application.Main;
import application.Message;
import network.ServerHandler;

import javax.swing.*;
import java.time.LocalTime;

public class Gui {
    JFrame jf;
    JPanel content;
    JButton btnHost, btnClient, btnSend, btnDiscover;
    public JTextField tfInput, tfName, tfIP;
    JScrollPane sp;
    public JTextArea taChat;
    int width = 435, height = 580;

    public void create() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        jf = new JFrame("Chat Test");
        jf.setSize(width, height);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(null);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);

        content = new JPanel();
        content.setBounds(0, 0, width, height);
        content.setLayout(null);

        btnHost = new JButton("Host");
        btnHost.setBounds(10, 10, 100, 25);
        btnHost.setVisible(true);
        btnHost.addActionListener(e -> {
            ClientHandler.kill();
            ServerHandler.start();
            Main.isHost = true;
            disableButtons();

        });
        content.add(btnHost);

        btnClient = new JButton("Client");
        btnClient.setBounds(120, 10, 100, 25);
        btnClient.setVisible(true);
        btnClient.addActionListener(e -> {
            ServerHandler.kill();
            if(!tfIP.getText().isEmpty()){
                ClientHandler.ip = tfIP.getText();
            }
            ClientHandler.start();
            Main.isHost = false;
            disableButtons();
        });
        content.add(btnClient);

//        btnDiscover = new JButton("Discover Host");
//        btnDiscover.setBounds(285, 510, 125, 25);
//        btnDiscover.setVisible(true);
//        btnDiscover.addActionListener(e -> {
//            tfIP.setText(ClientHandler.discover());
//        });
//        content.add(btnDiscover);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(10,485,50,25);
        content.add(lblName);

        tfName = new JTextField();
        tfName.setBounds(10, 510, 80, 25);
        tfName.setVisible(true);
        content.add(tfName);

        JLabel lblIP = new JLabel("Server IP (leer für Localhost):");
        lblIP.setBounds(100,485,200,25);
        content.add(lblIP);

        tfIP = new JTextField();
        tfIP.setBounds(100, 510, 150, 25);
        tfIP.setVisible(true);
        content.add(tfIP);

        tfInput = new JTextField();
        tfInput.setBounds(10, 45, 400, 25);
        tfInput.setVisible(true);
        content.add(tfInput);

        btnSend = new JButton("Send");
        btnSend.setBounds(310, 10, 100, 25);
        btnSend.setVisible(true);
        btnSend.addActionListener(e -> {
            if (Main.isConnected) {
                Message m = new Message();
                m.setText(tfInput.getText());
                m.setAbsender(tfName.getText());
                m.setTimeStamp(LocalTime.now());
                if(Main.isHost){
                    ServerHandler.send(m);
                }else{
                    ClientHandler.send(m);
                }
                tfInput.setText("");
            }

        });
        content.add(btnSend);

        taChat = new JTextArea();
        taChat.setVisible(true);

        sp = new JScrollPane(taChat);
        sp.setBounds(10,80,400,400);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        content.add(sp);

        jf.add(content);
        jf.setVisible(true);
    }

    private void disableButtons(){
        btnHost.setEnabled(false);
        btnClient.setEnabled(false);
    }
}
