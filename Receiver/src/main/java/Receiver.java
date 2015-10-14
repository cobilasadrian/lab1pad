import java.util.concurrent.ExecutionException;

/**
 * Created by Adrian on 10/14/2015.
 */
public class Receiver {

    public static void main(String[] args) {

        System.out.println("Receiver...");
        try {

            IOperation receiver = new TransportService("127.0.0.1",3333);
            receiver.AsyncWrite("subscribe");
            String message;
            while(!(message = receiver.AsyncRead().get()).contains("exit r"))
            {
                System.out.println(message);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
