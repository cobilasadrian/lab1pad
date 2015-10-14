import java.io.IOException;
import java.net.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Adrian on 10/11/2015.
 */

public class BrokerService implements IOperation {

    private DatagramSocket socket = null;
    private Collection<SocketAddress> receivers = new HashSet<>();
    private final static ExecutorService executor = Executors.newFixedThreadPool(10);

    public BrokerService (int port)
    {
        try {
            this.socket = new DatagramSocket(port);
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
                String receivedData = new String(packet.getData());
                if(receivedData.contains("subscribe")){
                    receivers.add(packet.getSocketAddress());
                }
                return receivedData;
            }
        });
    }

    public Future AsyncWrite(final String message) {
        return executor.submit(new Runnable() {
            public void run() {

                byte[] buff = message.getBytes();
                receivers.forEach(r->{
                    try {
                        socket.send(new DatagramPacket(buff, 0, buff.length, r));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        });
    }

}