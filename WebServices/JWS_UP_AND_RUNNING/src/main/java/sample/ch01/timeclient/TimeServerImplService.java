package sample.ch01.timeclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "TimeServerImplService", targetNamespace = "http://ch01.sample/", wsdlLocation = "http://localhost:9876/ts?wsdl")
public class TimeServerImplService extends Service {

    private final static URL TIMESERVERIMPLSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(sample.ch01.timeclient.TimeServerImplService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = sample.ch01.timeclient.TimeServerImplService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:9876/ts?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:9876/ts?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        TIMESERVERIMPLSERVICE_WSDL_LOCATION = url;
    }

    public TimeServerImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TimeServerImplService() {
        super(TIMESERVERIMPLSERVICE_WSDL_LOCATION, new QName("http://ch01.sample/", "TimeServerImplService"));
    }

    @WebEndpoint(name = "TimeServerImplPort")
    public TimeServer getTimeServerImplPort() {
        return super.getPort(new QName("http://ch01.sample/", "TimeServerImplPort"), TimeServer.class);
    }

    @WebEndpoint(name = "TimeServerImplPort")
    public TimeServer getTimeServerImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ch01.sample/", "TimeServerImplPort"), TimeServer.class, features);
    }
}
