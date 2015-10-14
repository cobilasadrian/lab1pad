import java.util.concurrent.Future;

/**
 * Created by Adrian on 10/14/2015.
 */
public interface IOperation {
    Future<String> AsyncRead();
    Future AsyncWrite(final String message);
}
