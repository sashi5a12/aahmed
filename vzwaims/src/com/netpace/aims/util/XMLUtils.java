package com.netpace.aims.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.DOMSerializer;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils
{

    public static String docToString(Document document)
    {
        OutputFormat of = new OutputFormat();
        //of.setIndenting(true);
        of.setMethod(Method.XML);
        of.setEncoding("ISO-8859-1");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DOMSerializer serializer = new XMLSerializer(baos, of);
        try
        {
            serializer.serialize(document);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return (baos.toString());
    }

    public static Document loadFile(String filename) throws IOException, SAXException, Exception
    {
        DOMParser parser = new DOMParser();
        Document doc = null;

        if (filename == null)
        {
            System.out.println("Error: File name is not specified..");
            return null;
        }
        try
        {
            parser.parse(filename);
            doc = parser.getDocument();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        return doc;

    } //End of method loadFile

    public static Document loadXML(String xml) throws Exception
    {
        DOMParser parser = new DOMParser();
        Document doc = null;

        if (xml == null)
        {
            System.out.println("Error: XML is not specified..");
            return null;
        }
        try
        {
            StringReader in = new StringReader(xml);
            InputSource inputSource = new InputSource(in);
            parser.parse(inputSource);
            doc = parser.getDocument();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        return doc;

    } //End of method loadXML

    public static boolean updateTextNode(String nodeName, Element searchFrom, String data, int position) throws Exception
    {
        boolean result = false;
        Element currentElement = (Element) searchFrom.getElementsByTagName(nodeName).item(position);

        if (currentElement != null && currentElement.getNodeType() == Node.ELEMENT_NODE)
        {
            Text textNode = (Text) (currentElement.getFirstChild());
            textNode.setData(data);

            if (textNode != null && textNode.getNodeValue() == null)
                result = false;
            else
                result = true;
        }
        return result;
    }

    /**
    *  This method is used to send a XML request file to web server to process the request and return
    *  xml response containing event id.
    */
    public static String postXML(String postUrl, String xml) throws Exception
    {

        System.out.println("Connecting to Web Server .......");

        InputStream in = null;
        BufferedReader bufferedReader = null;
        OutputStream out = null;
        PrintWriter printWriter = null;
        StringBuffer responseMessageBuffer = new StringBuffer("");

        try
        {
            URL url = new URL(postUrl);
            URLConnection con = url.openConnection();

            // Prepare for both input and output
            con.setDoInput(true);
            con.setDoOutput(true);

            // Turn off caching
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "text/xml");
            out = con.getOutputStream();
            // Write the arguments as post data
            printWriter = new PrintWriter(out);

            printWriter.println(xml);
            printWriter.flush();

            //Process response and return back to caller function
            in = con.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String tempStr = null;

            int tempClearResponseMessageBufferCounter = 0;
            while ((tempStr = bufferedReader.readLine()) != null)
            {
                tempClearResponseMessageBufferCounter++;
                //clear the buffer for the first time
                if (tempClearResponseMessageBufferCounter == 1)
                    responseMessageBuffer.setLength(0);
                responseMessageBuffer.append(tempStr);
            }

        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            System.out.println("Calling finally in POSTXML");
            try
            {
                if (in != null)
                    in.close();
            }
            catch (Exception eex)
            {
                System.out.println("COULD NOT Close Input Stream in try");
            }
            try
            {
                if (out != null)
                    out.close();
            }
            catch (Exception eex)
            {
                System.out.println("COULD NOT Close Output Stream in try");
            }

        }

        return responseMessageBuffer.toString();
    }

    /**
    *  This method is used to send a XML request file to web server to process the request and return
    *  xml response containing event id.
    */
    public static String postXMLWithTimeout(String postUrl, String xml, int readTimeout) throws Exception
    {

        System.out.println("XMLUtils.postXMLWithTimeout: Connecting to Web Server .......");

        InputStream in = null;
        BufferedReader bufferedReader = null;
        OutputStream out = null;
        PrintWriter printWriter = null;
        StringBuffer responseMessageBuffer = new StringBuffer("");

        try
        {
            URL url = new URL(postUrl);
            URLConnection con = url.openConnection();

            // Prepare for both input and output
            con.setDoInput(true);
            con.setDoOutput(true);

            // Turn off caching
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "text/xml");
            con.setReadTimeout(readTimeout);
            out = con.getOutputStream();
            // Write the arguments as post data
            printWriter = new PrintWriter(out);

            printWriter.println(xml);
            printWriter.flush();

            //Process response and return back to caller function
            in = con.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String tempStr = null;

            int tempClearResponseMessageBufferCounter = 0;
            while ((tempStr = bufferedReader.readLine()) != null)
            {
                tempClearResponseMessageBufferCounter++;
                //clear the buffer for the first time
                if (tempClearResponseMessageBufferCounter == 1)
                    responseMessageBuffer.setLength(0);
                responseMessageBuffer.append(tempStr);
            }

        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            System.out.println("Calling finally in POSTXML");
            try
            {
                if (in != null)
                    in.close();
            }
            catch (Exception eex)
            {
                System.out.println("COULD NOT Close Input Stream in try");
            }
            try
            {
                if (out != null)
                    out.close();
            }
            catch (Exception eex)
            {
                System.out.println("COULD NOT Close Output Stream in try");
            }

        }
        System.out.println("XMLUtils.postXMLWithTimeout: end .......");
        return responseMessageBuffer.toString();
    }

    public static Node getNodeByXPath(Node parentNode, String xpath)
			throws Exception {
		Node node = null;
		if (parentNode != null) {
			parentNode.normalize();
			node = XPathAPI.selectSingleNode(parentNode, xpath);
			if (node != null)
				node.normalize();
		}
		return node;
	}

	public static NodeList getNodesByXPath(Node parentNode, String xpath)
			throws Exception {
		NodeList nodeList = null;
		if (parentNode != null) {
			parentNode.normalize();
			nodeList = XPathAPI.selectNodeList(parentNode, xpath);
		}
		return nodeList;
	}
}
