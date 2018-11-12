import java.util.*;
import java.io.*;
import java.net.*;


public class Server implements Runnable{

    public static boolean running = true;


    public void run(){
        try{
            DatagramSocket socket = new DatagramSocket();
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