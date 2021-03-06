import java.util.*;
import java.io.*;
import java.net.*;

/*
    Can be run as a thread or as a separate process
*/

public class Sender implements Runnable{
    private DatagramSocket socket;
    private InetAddress addr;
    private ArrayList<InetAddress> list;
    private String username;

    public static void main(String[] args){
        new Sender().run();
    }

    public static final int PORT = 1811;
    public void run(){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter username: ");
            username = sc.nextLine();
            System.out.println("\n");
            list = generatelList();
            while(true){
                list = generatelList();
                String msg = "";
                System.out.print("> ");
                msg = username + ": " + sc.nextLine();
                for(InetAddress address: list){
                    broadcast(msg, address);
                }
                
            }
             
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void broadcast(String msg, InetAddress address) throws Exception{
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
        socket.send(packet);
        socket.close();
    }

    ArrayList<InetAddress> generatelList() throws Exception{
        ArrayList<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while(interfaces.hasMoreElements()){
            NetworkInterface networkInterface = interfaces.nextElement();
            if(networkInterface.isLoopback() || !networkInterface.isUp())
                continue;
            
            networkInterface.getInterfaceAddresses().stream().map(a -> a.getBroadcast()).filter(Objects::nonNull).forEach(broadcastList::add);
        }

        return broadcastList;
    }

}