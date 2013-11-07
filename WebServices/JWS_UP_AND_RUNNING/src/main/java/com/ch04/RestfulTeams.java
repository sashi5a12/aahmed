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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.NodeList;

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
    
    private Source doPost(MessageContext ctx){
        // Extract the POST request from the message.
        Map<String, List> headers = (Map<String, List>)ctx.get(MessageContext.HTTP_REQUEST_HEADERS);
        
        List<String> cargo = headers.get(put_post_key);
        
        if (cargo == null) {
            throw new HTTPException(400); // bad request
        }
        
        ByteArrayInputStream xml_stream = list_to_bytes(cargo);
        String team_name = null;
        
        try{
            // Set up the XPath object to search for the XML elements.
            DOMResult dom = get_dom(xml_stream);
            XPath xp = get_xpath();
            
            // Extract the team's name.
            team_name = xp.evaluate("/create_team/name", dom.getNode());
            
            // Trying to create an already existing team is not allowed.
            if (team_map.containsKey(team_name.trim())) {
                throw new HTTPException(400); // bad request
            }
            
            // Extract the players, names and nicknames
            List<Player> team_players = new ArrayList<Player>();
            NodeList players = (NodeList)xp.evaluate("player", dom.getNode(), XPathConstants.NODESET);
            
            for (int i = 1; i <= players.getLength(); i++) {
                String name = xp.evaluate("name", dom.getNode());
                String nickname = xp.evaluate("nickname", dom.getNode());
                Player player = new Player(name, nickname);
                team_players.add(player);
            }

            // Update the team list and map, persist the new list.
            Team team = new Team(team_name, team_players);
            teams.add(team);
            team_map.put(team_name, team);
            serialize();
            
        } catch (XPathExpressionException e) {
            throw new HTTPException(400);   // bad request
        }
        // Send back a confirmation that the team has been created.
        return send_response("Team " + team_name + " created.");
    }
    private StreamSource send_response(String msg) {
        HttpResponse response = new HttpResponse();
        response.setResponse(msg);
        ByteArrayInputStream stream = encode_to_stream(response);
        return new StreamSource(stream);
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
    private ByteArrayInputStream list_to_bytes(List<String> list) {
        String xml = "";
        for (String next : list) {
            xml += next.trim();
        }
        return new ByteArrayInputStream(xml.getBytes());
    }
    private DOMResult get_dom(ByteArrayInputStream stream){
        DOMResult dom_result=null;
        try {
            dom_result = new DOMResult();
            Transformer trans= TransformerFactory.newInstance().newTransformer();
            trans.transform(new StreamSource(stream), dom_result);
        } catch (TransformerConfigurationException ex) {
            new HTTPException(500);
        } catch (TransformerException ex) {
            new HTTPException(500);
        }
        return dom_result;
    }
    private XPath get_xpath(){
        // Set up the XPath object to search for the XML elements.
        XPath xpath=null;
        try {
            URI uri = new URI("create_team");
            XPathFactory fact = XPathFactory.newInstance();
            xpath = fact.newXPath();
            xpath.setNamespaceContext(new NSResolver("",uri));
        } catch (URISyntaxException ex) {
            new HTTPException(500);
        }
        return xpath;
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
