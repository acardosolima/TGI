/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 *
 * @author lucasnote
 */
public class Socket implements Runnable{
    
    private DatagramSocket socket;
    private DatagramPacket packet;
    private IGrafica.Panel outTextPane;
    
    public Socket(IGrafica.Panel outTextPane){
        byte[] receive = new byte[1500]; // alter at init
        this.packet = new DatagramPacket(receive, receive.length);
        this.outTextPane = outTextPane;
        try {
            this.outTextPane.panelPrint("Binding port and setting things up");
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
            } catch (IOException ex) {
                Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, null, ex);
            }
            String msg = new String(packet.getData(), 0, packet.getLength());
            this.outTextPane.panelPrint(msg);
        }
    }
    
    public void run(){
        this.outTextPane.panelPrint("Preparing to run");
        this.listen();
    }
}
