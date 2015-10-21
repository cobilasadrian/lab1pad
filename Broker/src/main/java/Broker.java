import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * Created by Adrian on 10/14/2015.
 */
public class Broker {

    public static String xmlToJson(String xmlMessage){

        if(validateXML(xmlMessage)==true){

        XStream xstreamForXml = new XStream();
        xstreamForXml.setMode(XStream.NO_REFERENCES);
        Student student = (Student)xstreamForXml.fromXML(xmlMessage);

        XStream xstreamForJson = new XStream(new JettisonMappedXmlDriver());
        xstreamForJson.setMode(XStream.NO_REFERENCES);
        xstreamForXml.alias("Student", Student.class);
        String jsonMessage = xstreamForJson.toXML(student);

        return jsonMessage;
        } else {
            return xmlMessage;
        }
    }

    public static boolean validateXML(String input){

        String pattern = "(<Student><Id>[\\d]<\\/Id><Name>[a-zA-Z ]+<\\/Name><Faculty>[a-zA-Z ]+<\\/Faculty><Specialty>[a-zA-Z ]+<\\/Specialty><\\/Student>)";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(input);
        if (m.find()) {
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) {

        System.out.println("Broker...");
        try {

            IOperation broker = new BrokerService(3333);

            String message;
            while (!(message = broker.AsyncRead().get()).contains("exit b")) {
                message = xmlToJson(message);
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
