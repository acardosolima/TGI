/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucasnote
 */
public class Socket implements Runnable{
    private byte[] receive, send;
    private DatagramPacket sendPacket;
    private DatagramSocket socket;
    private DatagramPacket receivePacket;
    private InetAddress sendIP = null;
    private int sendPort = 0;
    private IGrafica.Panel outPut;
    private HashMap dirs;
    
    private ByteImage img;
    
    public Socket(IGrafica.Panel outPut){
        this.receive = new byte[1500];
        this.send = new byte[1500];
        this.receivePacket = new DatagramPacket(receive, receive.length);
        this.outPut = outPut;
        dirs = new HashMap();
        
        dirs.put(87, 1);    dirs.put(83, 2);    dirs.put(65, 3);
        dirs.put(68, 4);    dirs.put(37, 5);    dirs.put(39, 6);    
        
        try {
            this.outPut.panelPrint("Binding port and setting things up");
            this.socket = new DatagramSocket(9999);
        } catch (SocketException ex) {
            Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void listen(){
        this.outPut.panelPrint("Listening");
        int i = 0;
        while(true){
            try {
                socket.receive(receivePacket);
                this.sendIP = receivePacket.getAddress();
                this.sendPort = receivePacket.getPort();
            } catch (IOException ex) {
                Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(receivePacket.getLength() > 0){
                System.out.println("pkg :" + (i++));
                this.handlePacket(receivePacket);
            }
        }
    }
    
    private void send(int n){
        this.send = Integer.toString(n).getBytes();
        this.sendPacket = new DatagramPacket(this.send, this.send.length, this.sendIP, this.sendPort);
        try {
            System.out.println("enviando: " + this.send + " - " + this.send.length + " - " + this.sendIP);
            this.socket.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(Socket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mover(int d){
        if(dirs.containsKey(d)){
            this.send((Integer)dirs.get(d));
        }       
            
    }
    
    private void handlePacket(DatagramPacket pkg){
        switch(new String(pkg.getData(), 0, pkg.getLength())){
            case "hello": 
                this.outPut.panelPrint("dispositivo conectado");
                break;
            case "in":
                this.outPut.panelPrint("recebendo imagem");
                img = new ByteImage();
                break;
            case "fi": 
                this.outPut.panelPrint("gerando imagem");
                String nome = img.gerar();
                if(!nome.isEmpty())
                    outPut.setImage(nome);
                break;
            default:
                img.popular(pkg.getData(), pkg.getLength());
                break;
        }
    }
    @Override
    public void run(){
        this.outPut.panelPrint("Preparing to run");
        this.listen();
    }
}
