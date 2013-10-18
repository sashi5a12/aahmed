package sample.ch02;

import java.util.Date;
import javax.jws.WebService;

@WebService(endpointInterface = "sample.ch02.TimeServer")
public class TimeServerImpl implements TimeServer{

    @Override
    public String getTimeAsString() {
        return new Date().toString();
    }

    @Override
    public long getTimeAsLong() {
        return new Date().getTime();
    }
    
}
