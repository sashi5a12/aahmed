package com.ch06.RestfulClient;

import javax.xml.ws.Service;
import javax.xml.namespace.QName;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.Dispatch;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import java.net.URL;
import java.util.Map;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

class DispatchClient {
    private static String xml =
        "<?xml version = '1.0' encoding = 'UTF-8' ?>" + "\n" +
        "<uri:RequestDocument xmlns:uri = 'urn:RequestDocumentNS'>" + "\n" +
        "  <operation>f2c</operation>" + "\n" +
        "  <operand>40</operand>" + "\n" +
        "</uri:RequestDocument>" + "\n";

    public static void main(String[ ] args) throws Exception {
        QName qname = new QName("", "");
        String url_string = "http://127.0.0.1:8080/jwsur/restful/";
        URL url = new URL(url_string);

        // Create the service and add a port
        Service service = Service.create(qname);
        service.addPort(qname, HTTPBinding.HTTP_BINDING, url_string);

        Dispatch<Source> dispatcher = service.createDispatch(qname,
                                                       Source.class,
                                                       javax.xml.ws.Service.Mode.MESSAGE);
        Map<String, Object> rc = dispatcher.getRequestContext();
        rc.put(MessageContext.HTTP_REQUEST_METHOD, "POST");
        Source result = dispatcher.invoke(new StreamSource(new StringReader(xml)));
        parse_response(result);
    }

    private static void parse_response(Source res) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        StreamResult sr = new StreamResult(bao);
        transformer.transform(res, sr);

        ByteArrayInputStream bai = new ByteArrayInputStream(bao.toByteArray());
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document root = db.parse(bai);
        NodeList nodes = root.getElementsByTagName("return");
        Node node = nodes.item(0); // should be only one <return> element

        System.out.println("Request document:\n" + xml);
        System.out.println("Return value: " + node.getFirstChild().getNodeValue());
    }
}