import java.io.IOException;
import java.net.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Adrian on 10/7/2015.
 */

public class TransportService implements IOperation {

    private String address = null;
    private int port = 0;
    private DatagramSocket socket = null;
    private final static ExecutorService executor = Executors.newFixedThreadPool(10);


    public TransportService(String address,int port)
    {
        this.address = address;
        this.port = port;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public Future<String> AsyncRead(){
        return executor.submit(new Callable<String>(){
            public String call() throws IOException {

                byte[] buff = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buff, 0, buff.length);
                socket.receive(packet);
                return new String(packet.getData());

            }
        });
    }

    public Future AsyncWrite(final String message){
        return executor.submit(new Runnable() {
            public void run() {

                try {
                    byte[] buff = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(buff, 0, buff.length,InetAddress.getByName(address),port);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}