import java.util.*;
import java.io.*;
import java.net.*;

/*
    Can be run as a thread or as a separate process
*/

public class Receiver implements Runnable{

    public static boolean running = true;

    public static void main(String[] args){
        new Receiver().run();
    }

    public void run(){
        try{
            DatagramSocket socket = new DatagramSocket(1811);
            byte[] buf = new byte[1024];
            while(running){
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println(msg);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}