/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino;

import java.awt.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 *
 * @author lucasnote
 */
public class Socket implements Runnable{
    private byte[] receive, send;
    private DatagramPacket sendPacket;
    private DatagramSocket sendSocket;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress sendIP = null;
    private int sendPort = 0;
    private IGrafica.Panel outTextPane;
    private HashMap dirs;
    
    public Socket(IGrafica.Panel outTextPane){
        this.receive = new byte[1500];
        this.send = new byte[1500];
        this.packet = new DatagramPacket(receive, receive.length);
        this.outTextPane = outTextPane;
        dirs = new HashMap();
        
        dirs.put(87, 1);    dirs.put(83, 2);    dirs.put(65, 3);
        dirs.put(68, 4);    dirs.put(37, 5);    dirs.put(39, 6);    
        
        try {
            this.outTextPane.panelPrint("Binding port and setting things up");
            this.sendSocket = new DatagramSocket();
            this.socket = new DatagramSocket(9999);
        } catch (SocketException ex) {
            Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void listen(){
        this.outTextPane.panelPrint("Listening");
        while (true) {
            try {
                socket.receive(packet);
                this.sendIP = packet.getAddress();
                this.sendPort = packet.getPort();
            } catch (IOException ex) {
                Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, null, ex);
            }
            String msg = new String(packet.getData(), 0, packet.getLength());
            this.outTextPane.panelPrint(msg);
        }
    }
    
    private void send(int n){
        //System.out.println(n);
        
        this.send = Integer.toString(n).getBytes();
        this.sendPacket = new DatagramPacket(this.send, this.send.length, this.sendIP, this.sendPort);
        try {
            System.out.println("enviando: " + this.send + " - " + this.send.length + " - " + this.sendIP);
            this.sendSocket.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mover(int d){
        if(dirs.containsKey(d)){
            this.send((Integer)dirs.get(d));
        }       
            
    }
    public void run(){
        this.outTextPane.panelPrint("Preparing to run");
        this.listen();
    }
}
