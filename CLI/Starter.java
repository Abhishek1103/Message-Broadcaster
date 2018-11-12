public class Starter{

    public static void main(String[] args){
        new Thread(new Receiver()).start();
        new Thread(new Sender()).start();
    }
}