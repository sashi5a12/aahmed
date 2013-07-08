/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author aahmed
 */
public class HelloClient {
    public static void main(String[] args){
        Client client= ClientBuilder.newClient();
        WebTarget target=client.target("http://localhost:8081/jersey/rest");
        target.path("hello");
//        target.queryParam("greeting", "Hi World");
        Invocation.Builder invocationBuilder=target.request();
        invocationBuilder.header("some-header", true);
        invocationBuilder.accept(MediaType.TEXT_PLAIN);
        Response response=invocationBuilder.get();
        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));
        
        String responseMsg = target.path("helloWorld/adnan").request().get(String.class);
        String responseMsg2 = target.path("hello").request(MediaType.TEXT_XML).get(String.class);
        System.out.println(responseMsg2);
    }
}
