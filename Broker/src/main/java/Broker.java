import java.util.concurrent.ExecutionException;

/**
 * Created by Adrian on 10/14/2015.
 */
public class Broker {
    public static void main(String[] args) {

        System.out.println("Broker...");
        try {

            IOperation broker = new BrokerService(3333);
            String message;
            while (!(message = broker.AsyncRead().get()).contains("exit b")) {
                broker.AsyncWrite(message);
                System.out.println(message);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
