package com.netpace.aims.controller.webservices;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;

import oracle.jdeveloper.webservices.runtime.WrappedDocLiteralStub;

import org.apache.soap.Body;
import org.apache.soap.Constants;
import org.apache.soap.Envelope;
import org.apache.soap.Header;
import org.apache.soap.SOAPException;
import org.apache.soap.messaging.Message;
import org.apache.soap.transport.http.SOAPHTTPConnection;
import org.apache.soap.util.xml.XMLParserUtils;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.netpace.aims.controller.application.WapInfoSpaceSubmitDCRBean;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.ConfigEnvProperties;

/**
 * 
 * Date Created: Tue Oct 26 23:20:10 PDT 2004
 * 
 * Verizon Solution - VZWHandler
 */

public class VZWHandlerStub extends WrappedDocLiteralStub
{
    public VZWHandlerStub()
    {
        m_httpConnection = new SOAPHTTPConnection();
        m_httpConnection.setTimeout(30000);
        m_complexTypes.put("com.netpace.aims.controller.webservices.SubmitDocumentResponse", new SerialisationInfo("SubmitDocumentResponse", new String[] {"CallingUrl", "ErrorMsg"}));
        m_complexTypes.put("com.netpace.aims.controller.webservices.VendorInfo", new SerialisationInfo("VendorInfo", new String[] {"VendorId", "ProductId", "Version", "SummaryDescription", "DetailedDescription", "DemoUrl", "IconUrl"}));

        _envProperties = ConfigEnvProperties.getInstance();
        _endpoint = _envProperties.getProperty("infospace.endpoint.url");
    }

    public static void main(String[] args)
    {
        try
        {
            VZWHandlerStub stub = new VZWHandlerStub();

            StringBuffer xmlSubmitted = new StringBuffer();
            WapInfoSpaceSubmitDCRBean dcrBean = new WapInfoSpaceSubmitDCRBean();
            SubmitDocumentResponse res = stub.SubmitDocument(dcrBean, "", xmlSubmitted);

            System.out.println(res.getCallingUrl());
            System.out.println(res.getErrorMsg());
            // Add your own code here.
        }
        catch (SOAPException e) {
            System.err.println("Caught SOAPException (  ****** "
                            + e.getFaultCode() + " ******  ):  ******  GET MESSAGE ****** "
                            + e.getMessage() + "  ******  ");
         }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    /*****************
        //private String _endpoint = "http://localhost:2000/vzwhandler/default.asmx";

        //Staging URL
        //private String _endpoint = "http://vzwdemoappmgr.infospace.com/vzwhandler/default.asmx";

        //Production URL
        //private String _endpoint = "http://vzwappmgr.infospace.com/vzwhandler/default.asmx";
        //load this url from envProperties
    *****************/
    private ConfigEnvProperties _envProperties = null;
    private String _endpoint = null;

    public String getEndpoint()
    {
        return _endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        _endpoint = endpoint;
    }

    private SOAPHTTPConnection m_httpConnection = null;

    public SubmitDocumentResponse SubmitDocument(WapInfoSpaceSubmitDCRBean dcrBean, String theRealXSDPath, StringBuffer xmlSubmitted) throws Exception
    {
        GregorianCalendar currentTime = InfospaceUtils.generateCurrentTime();

        String usernameS = "NetPace";

        String password = "2bOr!2b";
        byte [] passwordB = InfospaceUtils.utf8decode(password);

        String createdS = InfospaceUtils.generateCreatedTimestamp(currentTime);
        byte[] createdB = InfospaceUtils.utf8decode(createdS);

        String expiredS = InfospaceUtils.generateExpiredTimestamp(currentTime);

        byte [] nonceB = InfospaceUtils.generateNonce(password);
        String nonceS = MiscUtils.base64Encode(nonceB);

        String passwordS = MiscUtils.getBase64Digest(nonceB, createdB, passwordB);

        URL endpointURL = new URL(_endpoint);

        Envelope requestEnv = new Envelope();

        requestEnv.declareNamespace("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        requestEnv.declareNamespace("wsa", "http://schemas.xmlsoap.org/ws/2004/03/addressing");
        requestEnv.declareNamespace("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

        Header header = new Header();
        String must = Constants.NS_PRE_SOAP_ENV + ":" + Constants.ATTR_MUST_UNDERSTAND;

        Vector entries = new Vector();
        DocumentBuilder builder = XMLParserUtils.getXMLDocBuilder();

        Document doc_action = builder.newDocument();
        Element elem_action = doc_action.createElement("wsa:Action");
        doc_action.appendChild(elem_action);
        Text txt_action = doc_action.createTextNode("");
        txt_action.setData("http://www.infospace.com/vzw/import/SubmitDocument");
        elem_action.appendChild(txt_action);
        entries.addElement(elem_action);

        Document doc_msg_id = builder.newDocument();
        Element elem_msg_id = doc_msg_id.createElement("wsa:MessageID");
        doc_msg_id.appendChild(elem_msg_id);
        Text txt_msg_id = doc_msg_id.createTextNode("");
        txt_msg_id.setData("uuid:78d671f8-7027-4f14-b095-73fc2779efd");
        elem_msg_id.appendChild(txt_msg_id);
        entries.addElement(elem_msg_id);

        Document doc_reply_to = builder.newDocument();
        Element elem_reply_to = doc_reply_to.createElement("wsa:ReplyTo");
        elem_reply_to.appendChild(doc_reply_to.createElement("wsa:Address"))
                     .appendChild((Text)doc_reply_to.createTextNode("http://schemas.xmlsoap.org/ws/2004/03/addressing/role/anonymous"));
        doc_reply_to.appendChild(elem_reply_to);
        entries.addElement(elem_reply_to);

        Document doc_to = builder.newDocument();
        Element elem_to = doc_to.createElement("wsa:To");

        /***********
            //Staging URL
            //elem_to.appendChild((Text)doc_to.createTextNode("http://vzwdemoappmgr.infospace.com/vzwhandler/default.asmx"));

            //Production URL
            //elem_to.appendChild((Text)doc_to.createTextNode("http://vzwappmgr.infospace.com/vzwhandler/default.asmx"));
            //load this url from envProperties
        ************/
        elem_to.appendChild((Text)doc_to.createTextNode(_envProperties.getProperty("infospace.appmgr.url")));

        doc_to.appendChild(elem_to);
        entries.addElement(elem_to);

        Document doc_security = builder.newDocument();
        Element elem_security = doc_security.createElement("wsse:Security");
        elem_security.setAttribute(must, "1");

        Element elem_timestamp = doc_security.createElement("wsu:Timestamp");
        elem_timestamp.setAttribute("wsu:Id", "Timestamp-1fa1a8b2-574d-40bc-bd1b-82394ea101c5");
        Element elem_created = doc_security.createElement("wsu:Created");
        elem_created.appendChild((Text)doc_security.createTextNode(createdS));
        Element elem_expires = doc_security.createElement("wsu:Expires");
        elem_expires.appendChild((Text)doc_security.createTextNode(expiredS));
        elem_timestamp.appendChild(elem_created);
        elem_timestamp.appendChild(elem_expires);

        elem_security.appendChild(elem_timestamp);

        Element elem_username_token = doc_security.createElement("wsse:UsernameToken");
        elem_username_token.setAttribute("xmlns:wsu","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        elem_username_token.setAttribute("wsu:Id","SecurityToken-cb186370-c137-4e28-8cb2-83f8bdc90d89");

        Element elem_username = doc_security.createElement("wsse:Username");
        elem_username.appendChild((Text)doc_security.createTextNode(usernameS));
        elem_username_token.appendChild(elem_username);

        Element elem_password = doc_security.createElement("wsse:Password");
        elem_password.setAttribute("Type","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
        //elem_password.setAttribute("Type","PasswordDigest");

        elem_password.appendChild((Text)doc_security.createTextNode(passwordS));
        elem_username_token.appendChild(elem_password);
        Element elem_nonce = doc_security.createElement("wsse:Nonce");
        elem_nonce.appendChild((Text)doc_security.createTextNode(nonceS));
        elem_username_token.appendChild(elem_nonce);
        Element elem_wsse_created = doc_security.createElement("wsu:Created");
        elem_wsse_created.appendChild((Text)doc_security.createTextNode(createdS));
        elem_username_token.appendChild(elem_wsse_created);

        elem_security.appendChild(elem_username_token);

        doc_security.appendChild(elem_security);
        entries.addElement(elem_security);

        header.setHeaderEntries(entries);
        requestEnv.setHeader(header);

        Body requestBody = new Body();
        Vector requestBodyEntries = new Vector();

        Document doc_body = builder.newDocument();
        Element elem_sub_document = doc_body.createElement("SubmitDocument");
        elem_sub_document.setAttribute("xmlns","http://www.infospace.com/vzw/import");

        Element elem_payload = doc_body.createElement("payload");

        String dcrPayLoad = GenerateDeckChangePayLoad.generate(dcrBean);

        //String dcrPayLoad = PostXML.getDCRDocument();
        System.out.println(" dcrPayLoad --> \n\n\n" + dcrPayLoad);
        xmlSubmitted.append(dcrPayLoad);

        //ValidatingXMLDOMParser.validateJAXPValidator(theRealXSDPath, dcrPayLoad);

        CDATASection elem_cdata = doc_body.createCDATASection(dcrPayLoad);
        elem_payload.appendChild(elem_cdata);

        elem_sub_document.appendChild(elem_payload);

        requestBodyEntries.addElement(elem_sub_document);

        requestBody.setBodyEntries(requestBodyEntries);
        requestEnv.setBody(requestBody);

        Message msg = new Message();
        m_httpConnection.setTimeout(60000);
        msg.setSOAPTransport(m_httpConnection);

        msg.send(endpointURL, "http://www.infospace.com/vzw/import/SubmitDocument", requestEnv);



        Envelope responseEnv = msg.receiveEnvelope();
        Body responseBody = responseEnv.getBody();
        Vector responseData = responseBody.getBodyEntries();


        return (SubmitDocumentResponse)fromElement((Element)responseData.elementAt(0), SubmitDocumentResponse.class);
    }
}