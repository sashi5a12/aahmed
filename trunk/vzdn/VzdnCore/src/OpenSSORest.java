//package com.netpace.opensso;

import java.io.*;
import java.net.*;

public class OpenSSORest {
    private static String serverURL;
    private static String tokenId;
    private static String username;

    public static void main(String[] args1) {
        /*if (args.length != 1) {
            System.err.println(
                "Usage: OpenSSORest server-instance\n");
            System.exit(1);
        }*/
        serverURL = "http://viper.netpace.com:8002/opensso";
        //serverURL = "http://vzdnsso.netpace.com:8080/opensso";
        //serverURL = "http://aahmed.netpace.com:6060/opensso";

        try {
        	
            authenticate();
            //showAttributes();
//            create("shalimaruser1@netpace.com","5002,4001,1001,2018","Verizon","Adnan","Ahmed");		//Content Manager
            //create("user1@netpace.com","5002,4003,1001,2018","Verizon","Adnan1","Ahmed1");		//Content Manager
            //create("user2@netpace.com","5002,4003,1001,2018","Verizon","Adnan2","Ahmed2");		//Content Manager
            create("brian.higgins@verizonwireless.com","3002","Verizon","Brian","Higgins");		//Content Manager

            //validateToken();
            //search();
            //showAttributes();
            //read("demo", null);
            //create();
            

                       
            logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void authenticate()
        throws Exception {
        System.out.println();
        System.out.println("Authenticate to server");
        username = "amadmin";
        //username = "temp3all@netpace.com";
        //username = getUserInput("username: ");
       // String password = getUserInput("password: ");
        //String password = "netpace123";
        String password = "colossal";
        //String password = "123";

        String res = request(new URL(serverURL +
            "/identity/authenticate?" +
            "username=" + URLEncoder.encode(username, "UTF-8") +
            "&password=" +
            URLEncoder.encode(password, "UTF-8")));

        tokenId = res.substring(9);
        System.out.println("pre token : "+tokenId);
        
        tokenId = tokenId.substring(0, tokenId.length() -1);
        System.out.println("post token : "+tokenId);
        succeeded();
    }

    private static void logout()
        throws Exception {
        System.out.println("Logout");

        String res = request(new URL(serverURL +
            "/identity/logout?" +
            "subjectid=" +
            URLEncoder.encode(tokenId, "UTF-8")));
        succeeded();
    }

    private static void validateToken()
        throws Exception {
        System.out.println("Validate Token ID");
        String res = request(new URL(serverURL +
            "/identity/isTokenValid?" +
            "tokenid=" + URLEncoder.encode(tokenId, "UTF-8")));
        succeeded();
    }

    private static void search()
        throws Exception {
        System.out.println("Search");
        String res = request(new URL(serverURL +
            "/identity/search?" +
            "filter=*&admin=" +
            URLEncoder.encode(tokenId, "UTF-8")));
        System.out.println(res);
        succeeded();
    }

    private static void showAttributes()
        throws Exception {
        System.out.println("Show Attributes");
        String res = request(new URL(serverURL +
            "/identity/attributes?" +
            "subjectid=" +
            URLEncoder.encode(tokenId, "UTF-8")));
        System.out.println(res);
        succeeded();
    }    

    private static void read(String user, String attr)
        throws Exception {
        System.out.println("Read attributes");
        String qattr = (attr != null) ?
            "&attributes_names=" + attr : "";

        String res = request(new URL(serverURL +
            "/identity/read?" +
            "name=" + user + "&admin=" +
            URLEncoder.encode(tokenId, "UTF-8") +
            qattr));
        System.out.println(res);
        succeeded();
    }

    private static void create()
    throws Exception {
    System.out.println("Create user");
    String res = request(new URL(serverURL +
        "/identity/create?" +
        "identity_name=test&admin=" +
        URLEncoder.encode(tokenId, "UTF-8") +
        "&identity_attribute_names=userpassword" +
        "&identity_attribute_values_userpassword=123" +
        "&identity_realm=" + URLEncoder.encode("/", "UTF-8") +
        "&identity_type=User"
        ));
    succeeded();
}

    
    private static void create(String newuser,String roles, String userType)
        throws Exception {
        System.out.println("Create user");
        String res = request(new URL(serverURL +
            "/identity/create?" +
            "identity_name="+newuser+"&"+"admin=" +
            URLEncoder.encode(tokenId, "UTF-8") +
            "&identity_attribute_names=userpassword" +
            "&identity_attribute_values_userpassword=123"+
            
            "&identity_attribute_names=vzdn-user-type" +
            "&identity_attribute_values_vzdn-user-type="+userType+
            
            /*"&identity_attribute_names=vzdn-manager-organization" +
            "&identity_attribute_values_vzdn-manager-organization=intertech"+*/
            
            
            
            "&identity_attribute_names=vzdn-user-roles" +
            "&identity_attribute_values_vzdn-user-roles=" +roles+
            
            "&identity_realm=" + URLEncoder.encode("/", "UTF-8") +
            "&identity_type=User"
            ));
        succeeded();
    }

    private static void create(String newuser,String roles, String userType,String firstName, String lastName)
    throws Exception {
    System.out.println("Create calling the rite user");
    String res = request(new URL(serverURL +
        "/identity/create?" +
        "identity_name="+newuser+"&"+"admin=" +
        URLEncoder.encode(tokenId, "UTF-8") +
        "&identity_attribute_names=userpassword" +
        "&identity_attribute_values_userpassword=123"+
        
        "&identity_attribute_names=vzdn-user-type" +
        "&identity_attribute_values_vzdn-user-type="+userType+
        
        "&identity_attribute_names=givenname" +
        "&identity_attribute_values_givenname="+firstName+
        
        "&identity_attribute_names=sn" +
        "&identity_attribute_values_sn="+lastName+
        
        
        "&identity_attribute_names=vzdn-user-roles" +
        "&identity_attribute_values_vzdn-user-roles=" +roles+
        
        "&identity_realm=" + URLEncoder.encode("/", "UTF-8") +
        "&identity_type=User"
        ));
    succeeded();
}
    
    private static void update(String newuser)
        throws Exception {
        System.out.println("Change email address");
        String res = request(new URL(serverURL +
            "/identity/update?" +
            "identity_name="+newuser+"&"+"admin=" +
            URLEncoder.encode(tokenId, "UTF-8") +
            
            "&identity_attribute_names=vzdn-user-roles" +
            "&identity_attribute_values_vzdn-user-roles=101,202,125,125" +
            
            "&identity_attribute_names=mail" +
            "&identity_attribute_values_mail=test@example.com"
            ));
        succeeded();
    }

    private static void delete()
        throws Exception {
        System.out.println("Delete user");
        String res = request(new URL(serverURL +
            "/identity/delete?" +
            "identity_name=test&admin=" +
            URLEncoder.encode(tokenId, "UTF-8") +
            "&identity_type=User"));
        succeeded();
    }

    private static void succeeded() {
        System.out.println("Succeeded");
        System.out.println();
        System.out.println();
    }

    public static String getUserInput(String message)
        throws IOException {
        String userInput = null;
        BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));
        System.out.print(message);
        userInput = in.readLine();
        return userInput;
    }

    public static String request(URL url)
        throws Exception {
        System.out.println(url.toString());
        URLConnection conn = url.openConnection();
        BufferedReader dis = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
        StringBuffer buff = new StringBuffer();
        String inputLine;

        while ((inputLine = dis.readLine()) != null) {
            buff.append(inputLine).append("\n");
        }
        dis.close();
        return buff.toString();
    }
}
