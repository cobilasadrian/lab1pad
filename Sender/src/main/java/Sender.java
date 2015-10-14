import java.util.Scanner;

/**
 * Created by Adrian on 10/14/2015.
 */
public class Sender {

    public static void main(String[] args){

        System.out.println("Sender...");
        IOperation sender = new TransportService("127.0.0.1",3333);

        Scanner scanner = new Scanner(System.in);
        String input = null;
        while(!(input = scanner.nextLine()).contains("exit s")){
            sender.AsyncWrite(input);
        }
        scanner.close();
    }
}
