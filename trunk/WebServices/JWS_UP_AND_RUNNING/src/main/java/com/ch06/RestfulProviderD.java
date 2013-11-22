package com.ch06;

import javax.xml.ws.Provider;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.annotation.Resource;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPException;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.http.HTTPBinding;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

@WebServiceProvider
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(value = HTTPBinding.HTTP_BINDING)
public class RestfulProviderD implements Provider<Source> {
    @Resource
    protected WebServiceContext ws_context;
    protected Document document; // DOM tree

    public Source invoke(Source request) {
        try {
           if (ws_context == null) throw new RuntimeException("No ws_context.");
           MessageContext msg_context = ws_context.getMessageContext();

           // Check the HTTP request verb. In this case, only POST is supported.
           String http_verb = (String) msg_context.get(MessageContext.HTTP_REQUEST_METHOD);
           if (!http_verb.toUpperCase().trim().equals("POST"))
               throw new HTTPException(405); // bad verb exception

           build_document(request);
           String operation = extract_node("operation").trim();
           String operand = extract_node("operand").trim();

           if (operation.equals("fib"))      return fib_response(operand);
           else if (operation.equals("c2f")) return c2f_response(operand);
           else if (operation.equals("f2c")) return (f2c_response(operand));

           throw new HTTPException(404); // client error
        }
        catch(Exception e) { throw new HTTPException(500); }
    }

    // Build a DOM tree from the XML source for later lookups.
    private void build_document(Source request) {
        try {
           Transformer transformer = TransformerFactory.newInstance().newTransformer();
           this.document =
               DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
           Result result = new DOMResult(this.document);
           transformer.transform(request, result);
        }
        catch(Exception e) { this.document = null; }
    }

    // Extract a node's value from the DOM tree given the node's tag name.
    private String extract_node(String tag_name) {
        try {
            NodeList nodes = this.document.getElementsByTagName(tag_name);
            Node node = nodes.item(0);
            return node.getFirstChild().getNodeValue().trim();
        }
        catch(Exception e) { return null; }
    }

    // Prepare a response Source in which obj refers to the return value.
    private Source prepare_source(Object obj) {
        String xml =
            "<uri:restfulProvider xmlns:uri = 'http://foo.bar.baz'>" +
            "<return>" + obj + "</return>" +
            "</uri:restfulProvider>";
        return new StreamSource(new ByteArrayInputStream(xml.getBytes()));
    }

    private Source fib_response(String num) {
        try {
           int n = Integer.parseInt(num.trim());
           int fib = 1;
           int prev = 0;

           for (int i = 2; i <= n; ++i) {
               int temp = fib;
               fib += prev;
               prev = temp;
           }
           return prepare_source(fib);
        }
        catch(Exception e) { throw new HTTPException(500); }
    }

    private Source c2f_response(String num) {
        try {
           float c = Float.parseFloat(num.trim());
           float f = 32.0f + (c * 9.0f / 5.0f);
           return prepare_source(f);
        }
        catch(Exception e) { throw new HTTPException(500); }
    }

    // Compute f2c(c)
    private Source f2c_response(String num) {
        try {
           float f = Float.parseFloat(num.trim());
           float c = (5.0f / 9.0f) * (f - 32.0f);
           return prepare_source(c);
        }
        catch(Exception e) { throw new HTTPException(500); }
    }
}
