package com.netpace.aims.controller.webservices;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.DOMSerializer;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

import sun.misc.BASE64Decoder;

public class PostXML {

  public static void main(String[] args) {
		
    try {
    
    
      String usernameS = "NetPace";
    
   byte[] nonceB = generateNonce();
   String nonceS = base64Encode(nonceB);
      
      GregorianCalendar currentTime = generateCurrentTime();
      
      
			String createdS = generateCreatedTimestamp(currentTime);
      System.out.println("The created timestamp is " + createdS);
			byte[] createdB = utf8decode(createdS);


      String expiredS = generateExpiredTimestamp(currentTime);
      System.out.println("The expired timestamp is " + expiredS);
      
      String passwordS = "2bOr!2b";
			byte[] passwordB = utf8decode(passwordS);
      
      System.out.println("This is the security header " + getWSSEHeader(usernameS, passwordS, createdS));
           
      
 
      //String passwordDigestS = getBase64Digest(nonceB, createdB, passwordB);
      
        String passwordDigestS = getBase64Digest(nonceB, createdS.getBytes("UTF-8"),
       passwordS.getBytes("UTF-8"));
      
      
      String xmldata = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 

  + " <SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" " 
 + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"

  +    "<SOAP-ENV:Header>"
 /*
  * <wsse:UsernameToken xmlns:wsu='http://schemas.xmlsoap.org/ws/2002/07/utility' wsu:Id=SecurityToken-b0e7e90f-7258-4e20-bb44-7625213c171b>


  * */
   
   +    "<wsse:Security xmlns:wsse=\"http://schemas.xmlsoap.org/ws/2002/07/secext\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">" 
   

	+	" <wsse:UsernameToken  xmlns:wsu=\"http://schemas.xmlsoap.org/ws/2002/07/utility\" wsu:Id=\"SecurityToken-1\">"
  
   +         "<wsse:Username>" + usernameS + "</wsse:Username>"
	+		"<wsse:Password Type=\"PasswordDigest\">" + passwordDigestS + "</wsse:Password>"
   
		+	"<wsse:Nonce>" +  nonceS + "</wsse:Nonce>"
		+	"<wsse:Created>" + createdS + "</wsse:Created>"
 
	+	"</wsse:UsernameToken>"

   +      "</wsse:Security>"
   
   +   "</SOAP-ENV:Header>"
  
   +   "<SOAP-ENV:Body>"
   
   

   +      "<SubmitDocument>"
    +        "<payload xsi:type=\"xsd:string\">" + getDCRDocument() + "</payload>"
    //+        "<Document>" + "" + "</Document>"
    +     "</SubmitDocument>"
   +   "</SOAP-ENV:Body>"
 + "</SOAP-ENV:Envelope>";
      
			
      //Create socket 
      String hostname = "vzwdemoappmgr.infospace.com";;// "vzwdemoappmgr.infospace.com"; //"localhost";
      int port = 80; //80; //8003;
      InetAddress  addr = InetAddress.getByName(hostname);
      Socket sock = new Socket(addr, port);
			
      //Send header
      String path = "/vzwhandler/default.asmx";
      BufferedWriter  wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(),"UTF-8"));
      // Use "UTF8" for compatibility with the Microsoft virtual machine.
      wr.write("POST " + path + " HTTP/1.1\r\n");
      wr.write("Host: vzwdemoappmgr.infospace.com\r\n");
      wr.write("Connection: Keep-Alive, TE\r\n");
      wr.write("TE: trailers, deflate, gzip, compress\r\n");
      
      wr.write("SOAPAction: http://www.infospace.com/vzw/import/SubmitDocument\r\n");
      wr.write("Accept-Encoding: gzip, x-gzip, compress, x-compress\r\n");
      
      wr.write("Content-Length: " + xmldata.length()+ "\r\n");
      wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
      wr.write("\r\n");
      
      

      /*       
       * Host: vzwdemoappmgr.infospace.com
       */
			
      //Send data
      wr.write(xmldata);
      wr.flush();
      //wr.close();
			
      // Response
      //BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      InputStream in = sock.getInputStream();
      int c;
      while ((c = in.read()) != -1) System.out.write(c);
      //in.close();


      /*
      
       StringWriter swriter = new StringWriter();
            int bufLen = 1024;
            char[] cbuf = new char[bufLen];
            int length = -1;
            while ((length = rd.read(cbuf, 0, bufLen)) != -1) {
                swriter.write(cbuf, 0, length);
            }
            rd.close();
            swriter.close();
            String query = swriter.toString(); 
            */
                      /*
      
      int lgth = rd.read();
      System.out.println(lgth);
      
      String fullLine = null;
      String line;
      while((line = rd.readLine()) != null)
      {
        fullLine = fullLine + line;
      }
      */
          //  System.out.println(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
   private static byte[] generateNonce() {
     //String nonce = Long.toString(new Date().getTime());
     //String nonce = "msJPTHku44rHAqPIRvbNQA==";
      byte [] decodeBytes = null;
      try
      {
         BASE64Decoder bd = new BASE64Decoder();
         decodeBytes = bd.decodeBuffer("N5DIyy7v7zYkZ/i/OrarwA=="); 
      }
        catch (Exception ignore)
      {
            
      }         
     return decodeBytes;
 }
/*
	//private static String generateNonce() {
  private static String generateNonce(){
		// TODO working Nonce generator "private byte[] generateNonce()"
		return "msJPTHku44rHAqPIRvbNQA==";
  
  //return "abcdefg";
	}  
*/
/*
	private static String generateTimestamp() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    Date now = new Date();
    TimeZone zone = TimeZone.getDefault();    
    long offset = zone.getOffset(now.getTime());
		return dateFormatter.format(new Date(System.currentTimeMillis() - offset));
	}
	*/
	private static String generateCreatedTimestamp(GregorianCalendar currentTime) {
  
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");		
    df.setTimeZone( TimeZone.getTimeZone("GMT+0:00"));
    Date time = new Date(currentTime.getTimeInMillis());
    return df.format(time);
    
    }
    
	private static String generateExpiredTimestamp(GregorianCalendar currentTime) {     
    currentTime.add(currentTime.MINUTE,5);
    
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");		
    df.setTimeZone( TimeZone.getTimeZone("GMT+0:00"));
    Date time = new Date(currentTime.getTimeInMillis());
    return df.format(time);
    }    
    
	private static GregorianCalendar generateCurrentTime() {
    GregorianCalendar currentTime = new GregorianCalendar ();      
    return currentTime;
    }    
	  
	private static byte[] utf8decode(String input) {
		// UTF-8 enc
		byte[] ret = null;
		try {
			ret = input.getBytes("UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

 private static String base64Encode(byte[] bytes) {
   // Use Sun's encoder in this sample.
   return new sun.misc.BASE64Encoder().encode(bytes);
 }
/*
	private static synchronized String getBase64Digest(byte[] nonce, byte[] created, byte[] password) {
		try {
			MessageDigest messageDigester = MessageDigest.getInstance("SHA-1");

			// SHA-1 ( nonce + created + password )
			messageDigester.reset();
			messageDigester.update(nonce);
			messageDigester.update(created);
			messageDigester.update(password);

			return Base64.encode(messageDigester.digest());
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
*/
 private static synchronized String getBase64Digest(byte[] nonce,
     byte[] created, byte[] password) {
   try {
     MessageDigest messageDigester = MessageDigest.getInstance("SHA-1");
     // SHA-1 ( nonce + created + password )
     messageDigester.reset();
     messageDigester.update(nonce);
     messageDigester.update(created);
     messageDigester.update(password);
     return base64Encode(messageDigester.digest());
   } catch (java.security.NoSuchAlgorithmException e) {
     throw new RuntimeException(e);
   }
 }

 
  public static String getDCRDocument() 
  {
    StringBuffer outputBuffer = new StringBuffer();
    
    /*
     * 
     * <payload>&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;DeckChangeRequest xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="C:\Documentsand Settings\mweber\Desktop\DCR.xsd"&gt;

     */
    
    outputBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> ")
                .append("<DeckChangeRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"C:/Documents and Settings/RQazi/Desktop/DCR.xsd\"> ")               
                .append(" <VendorData> ")               
                .append("  <VendorName>Mforma Americas, Inc.</VendorName> ")
                .append("  <VendorId>800003</VendorId> ")
                .append("  <VendorBusinessContact> ")
                .append("   <Name>Robin Chan</Name> ")
                .append("  <Title>Senior Manager, Account Development</Title> ")
                .append("   <Address> ")
                .append("    <Street>42 W. 24th Street</Street> ")
                .append("    <City>New York</City> ")
                .append("    <State>NY</State> ")
                .append("    <Zip>10010</Zip> ")
                .append("   </Address> ")
                .append("   <OfficePhone> ")
                .append("    <AreacodeAndPhone>(212)243-2278</AreacodeAndPhone> ")
                .append("   </OfficePhone> ")
                .append("   <MobilePhone> ")
                .append("    <AreacodeAndPhone>(917)797-1224</AreacodeAndPhone> ")
                .append("   </MobilePhone> ")
                .append("   <Email>rchan@mforma.com</Email> ")
                .append("  </VendorBusinessContact> ")
                .append("  <VendorTechnicalContact> ")
                .append("  <Name>Eric Bilange</Name> ")
                .append("   <Title>CTO</Title> ")
                .append("   <Address> ")
                .append("    <Street>5414 Oberlin Drive</Street> ")
                .append("    <City>San Diego</City> ")
                .append("    <State>CA</State> ")
                .append("    <Zip>92121</Zip> ")
                .append("   </Address> ")
                .append("   <OfficePhone> ")
                .append("    <AreacodeAndPhone>(858)677-0433</AreacodeAndPhone> ")
                .append("    <Extension>1002</Extension> ")
                .append("   </OfficePhone> ")
                .append("   <MobilePhone> ")
                .append("    <AreacodeAndPhone>(858)722-1273</AreacodeAndPhone> ")
                .append("   </MobilePhone> ")
                .append("   <Email>ebilange@mforma.com</Email> ")
                .append("  </VendorTechnicalContact> ")
                .append("  <VendorEscalationContact> ")
                .append("   <Name>Christine Giovanacci</Name> ")
                .append("   <Title>Senior Director, Customer Solutions</Title> ")
                .append("   <Address> ")
                .append("    <Street>5414 Oberlin Drive</Street> ")
                .append("    <City>San Diego</City> ")
                .append("    <State>CA</State> ")
                .append("    <Zip>92121</Zip> ")
                .append("   </Address> ")
                .append("   <OfficePhone> ")
                .append("    <AreacodeAndPhone>(858)677-0433</AreacodeAndPhone> ")
                .append("    <Extension>1005</Extension> ")
                .append("   </OfficePhone> ")
                .append("   <MobilePhone> ")
                .append("    <AreacodeAndPhone>(858)204-5093</AreacodeAndPhone> ")
                .append("   </MobilePhone> ")
                .append("   <Email>cgiovanacci@mforma.com</Email> ")
                .append("   <EscalationPhone> ")
                .append("    <AreacodeAndPhone>(888)849-7532</AreacodeAndPhone> ")
                .append("   </EscalationPhone> ")
                .append("  </VendorEscalationContact> ")
                .append("  <CustomerContact> ")
                .append("   <Name>Key N. Sar</Name> ")
                .append("   <OfficePhone> ")
                .append("    <AreacodeAndPhone>(240)568-1410</AreacodeAndPhone> ")
                .append("   </OfficePhone> ")
                .append("  </CustomerContact> ")          
                .append(" </VendorData> ")
                .append(" <ProductData> ")            
                .append("  <VendorProductCode>M411</VendorProductCode> ")
                .append("  <VendorProductVersion>1.0</VendorProductVersion> ")
                .append("  <ProductPresentationNameLargeScreens>(M)411</ProductPresentationNameLargeScreens> ")
                .append("  <ProductPresentationNameSmallScreens>(M)411</ProductPresentationNameSmallScreens> ")
                .append("  <LongDescription>(M)411 helps you get the information you need on the go. Access Yellow and White page info, generate turn-by-turn driving directions, view dynamic maps with nearby landmark information, and store your favorite locations in the address book.</LongDescription> ")
                .append("  <ShortDescription>(M)411, the ultimate mobile directory, helps you find businesses, people- even get turn-by-turn directions &amp; maps.</ShortDescription> ")
                .append("  <TestURL>http://vrz.test.mforma.com/mlet/xhtml411/go</TestURL> ")
                .append("  <AvailabilityDateForTestURL>2004-09-17</AvailabilityDateForTestURL> ")
                .append("  <ProductionURL>http://wap.vrz.mforma.com/mlet/xhtml411/go</ProductionURL> ")
                .append("  <AvailabilityDateForProductionURL>2004-09-23</AvailabilityDateForProductionURL> ")
                .append("  <UserProfile>MDN</UserProfile> ")
                .append("  <PremiumIndicator>true</PremiumIndicator> ")             
                .append(" </ProductData> ")              
                .append("</DeckChangeRequest> ");
     
     return outputBuffer.toString();
  }
  
   private static String getWSSEHeader(String username, String password, String created)
     throws Exception {

   byte[] nonceB = generateNonce();
   String nonce = base64Encode(nonceB);

   //String created = generateTimestamp();

   String password64 = getBase64Digest(nonceB, created.getBytes("UTF-8"),
       password.getBytes("UTF-8"));
   StringBuffer header = new StringBuffer("UsernameToken Username=\"");
   header.append(username);
   header.append("\", ");
   header.append("PasswordDigest=\"");
   header.append(password64);
   header.append("\", ");
   header.append("Nonce=\"");
   header.append(nonce);
   header.append("\", ");
   header.append("Created=\"");
   header.append(created);
   header.append("\"");
   return header.toString();
 }
 
     public static Document loadDocument(String uri) {
        Document d = null;
        try {
            DocumentBuilderFactory dbf = 
                DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File(uri);
            d = db.parse(f);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return (d);
    }
    
    public static String toString(Document document) {
        OutputFormat of = new OutputFormat();
        of.setIndenting(true);
        of.setMethod(Method.XML);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DOMSerializer serializer = new XMLSerializer(baos, of);
        try {
            serializer.serialize(document);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return (baos.toString());
    }


}