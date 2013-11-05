package com.ch04;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;

// A generic service provider rather than a SOAP-based service.
@WebServiceProvider

// Two ServiceModes: PAYLOAD, the default, signals that the service
// needs access to only the message payload, whereas MESSAGE signals
// that the service needs access to the entire message.
@ServiceMode(value = Service.Mode.MESSAGE)

//The annotation announces that the service deals with raw XML over HTTP instead of SOAP over HTTP.
@BindingType(value = HTTPBinding.HTTP_BINDING)
public class RestfulTeams implements Provider<Source> {

    @Resource
    WebServiceContext ws_ctx;               //dependency injection
    private Map<String, Team> team_map;     //for easy lookups
    private List<Team> teams;               //serialized/deserialized
    private byte[] team_bytes;              //from the persistence file
    private static final String file_name = "teams.ser";
    private static final String put_post_key = "Cargo";

    public RestfulTeams() {
        read_teams_from_file();
        deserialize();
    }

    // Implementation of the Provider interface method: this
    // method handles incoming requests and generates the outgoing response.
    public Source invoke(Source source) {
        if (ws_ctx == null) {
            throw new RuntimeException("Injection failed on ws_ctx.");
        }
        // Grab the message context and extract the request verb.
        MessageContext msg_ctx = ws_ctx.getMessageContext();
        String http_verb = (String) msg_ctx.get(MessageContext.HTTP_REQUEST_METHOD);
        http_verb = http_verb.trim().toUpperCase();

        // Act on the verb.
        if (http_verb.equals("GET")) {
            return doGet(msg_ctx);
        } else if (http_verb.equals("POST")) {
            return doPost(msg_ctx);
        } else if (http_verb.equals("PUT")) {
            return doPut(msg_ctx);
        } else if (http_verb.equals("DELETE")) {
            return doDelete(msg_ctx);
        } else {
            throw new HTTPException(405); // bad verb exception
        }
    }
    
    private Source doGet(MessageContext ctx){
        // Parse the query string.
        String qs = (String)ctx.get(MessageContext.QUERY_STRING);
        
        // Get all teams.
        // Respond with list of all teams
        if (qs == null) {
            return new StreamSource(new ByteArrayInputStream(team_bytes));
        }
        else {
            String name = get_name_from_qs(qs);
            
            // Check if named team exists.
            Team team = team_map.get(name);
            if (team == null){
                throw new HTTPException(404); // not found
            }
            
            // Respond with named team.
            ByteArrayInputStream stream = encode_to_stream(team);
            return new StreamSource(stream);
        }
    }
    
    
    private void read_teams_from_file() {
        try {
            String path = get_file_path();
            int len = (int) new File(path).length();
            team_bytes = new byte[len];
            new FileInputStream(path).read(team_bytes);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void serialize() {
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(get_file_path()));
            XMLEncoder enc = new XMLEncoder(out);
            enc.writeObject(teams);
            enc.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    private void deserialize() {
        // Deserialize the bytes into a list of teams
        XMLDecoder dec = new XMLDecoder(new ByteArrayInputStream(team_bytes));
        teams = (List<Team>) dec.readObject();
        dec.close();

        // Create a map for quick lookups of teams.
        team_map = new HashMap<String, Team>();
        for (Team team : teams) {
            team_map.put(team.getName(), team);
        }
    }

    private ByteArrayInputStream encode_to_stream(Object obj){
        ByteArrayOutputStream  stream = new ByteArrayOutputStream ();
        XMLEncoder enc = new XMLEncoder(stream);
        enc.writeObject(obj);
        enc.close();
        return new ByteArrayInputStream(stream.toByteArray());
    }
    private String get_file_path() {
        String cwd = System.getProperty("user.dir");
        String sep = System.getProperty("file.separator");
        return cwd + sep + file_name;
    }
    
    private String get_name_from_qs(String qs) {
        String[] parts = qs.split("=");

        // Check if query string has form: name=<team name>
        if (!parts[0].equalsIgnoreCase("name")) {
            throw new HTTPException(400); // bad request
        }
        return parts[1].trim();
    }

}
