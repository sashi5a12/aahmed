package com.netpace.aims.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.parsers.SAXParserFactory;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is Utility class for Premium Sms module of smsgw this class
 * contains following methods like printingXML to File Converting XML document
 * to String, Validating XML etc etc
 * 
 * @author - Salman Ahmed
 * @date - Wednesday September 24, 2003
 *       *****************************************************************************
 *       S.No - Mod/Date - Mod/By - Mod/Description
 *       *****************************************************************************
 *       1 September 26,2003 Kashif Rasheed Introduced a method prependData()
 *       -----------------------------------------------------------------------------
 *       2 July 29,2004 Amirlai modified this file to provide SAX Parser Functionality
 *       -----------------------------------------------------------------------------
 *  
 */

public class PsmsUtility {
	/*
	 * This method lets you print the XML document on the hard disk @ param
	 * Document doc @ param String sendFolderPath folder in which file needs to
	 * be stored @ param String filename name of the file which needs to be
	 * printed @ return void throws exception if not able to create an XML file
	 */
	public static void printXML(Document doc, String sendFolderPath,
			String fileName) throws Exception {

		try {
			org.apache.xml.serialize.OutputFormat outputFormat = new org.apache.xml.serialize.OutputFormat(
					doc);
			outputFormat.setLineSeparator("\n");
			outputFormat.setIndenting(true);
			outputFormat.setLineWidth(1000);

			FileOutputStream fos = new FileOutputStream(sendFolderPath
					+ File.separator + fileName);

			XMLSerializer serializer = new XMLSerializer(fos, outputFormat);
			serializer.serialize(doc);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * Creates XML in String format after serializing the XML
	 * 
	 * @param doc
	 *            The document to be converted
	 * @return the xml in String format throws exception if error occurs while
	 *         converting document to String
	 */
	public static String getXML(Document doc) throws Exception {
		try {
			org.apache.xml.serialize.OutputFormat outputFormat = new org.apache.xml.serialize.OutputFormat(
					doc);
			outputFormat.setLineSeparator("\n");
			outputFormat.setIndenting(true);
			StringWriter xmlString = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(xmlString,
					outputFormat);
			serializer.serialize(doc);
			return xmlString.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Validates XML against DTD schema
	 * 
	 * @param InputSource
	 *            xmlSrc passed
	 * @see org.xml.sax.InputSource
	 *  
	 */
	public static void checkXml(InputSource xmlSrc) {
		try {
			DOMParser parser = new DOMParser();
			parser.setFeature("http://xml.org/sax/features/validation", true);
			parser.setFeature(
					"http://apache.org/xml/features/validation/schema", true);
			parser.setErrorHandler(new ErrorChecker());
			parser.parse(xmlSrc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validates XML against DTD schema
	 * 
	 * @param InputSource
	 *            xmlSrc passed
	 * @see org.xml.sax.InputSource
	 *  
	 */
	public static void checkXml(InputSource xmlSrc, String schemaLoc) {
		try {
			DOMParser parser = new DOMParser();

            parser.setFeature("http://xml.org/sax/features/validation", true);
			parser.setFeature(
					"http://apache.org/xml/features/validation/schema", true);
			parser
					.setProperty(
							"http://apache.org/xml/properties/schema/external-schemaLocation",
							schemaLoc);
			parser.setErrorHandler(new ErrorChecker());
			parser.parse(xmlSrc);
            
		} catch (Exception e) {
            e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * checks XML for validity with DTD
	 * 
	 * @param Document
	 *            to be validated
	 *  
	 */
	public static void checkXml(Document doc) {
		try {
			String xmlString = PsmsUtility.getXML(doc);
			StringReader strReader = new StringReader(xmlString);
			checkXml(new InputSource(strReader));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * checks XML for validity with DTD
	 * 
	 * @param Document
	 *            to be validated
	 *  
	 */
	public static void checkXml(Document doc, String schemaLoc) {
		try {
            String xmlString = PsmsUtility.getXML(doc);
			StringReader strReader = new StringReader(xmlString);
			checkXml(new InputSource(strReader), schemaLoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a Calendar in a given time zone based on local date values.
	 * 
	 * <p>
	 * If a Date is created with a local hour of 5PM in EST, and GMT is
	 * specified for the time zone, the Calendar created will reflect 5PM in
	 * GMT.
	 * 
	 * <p>
	 * This is useful for databases with a date field and a separate timezone
	 * field. In these cases, the date is extracted as a Timestamp, and the
	 * timezone is used to create a TimeZone object.
	 * 
	 * <p>
	 * Both the Date and TimeZone are passed to this function to reconstruct the
	 * original date in the original time zone.
	 * 
	 * <p>
	 * It is fine to pass a java.sql.Timestamp in the date parameter since
	 * Timestamp derives from java.util.Date.
	 * 
	 * @param date
	 *            The local date used to extract its local hour, etc. This can
	 *            be a java.sql.Timestamp.
	 * 
	 * @param zone
	 *            The target timezone to add to the date.
	 * 
	 * @return A Calendar having the local hour, but specified timezone. If any
	 *         argument is null, null is returned.
	 */
	public static Calendar addTimeZone(Date date, TimeZone zone) {
		// Ensure valid arguments
		if (date == null || zone == null)
			return null;

		//
		// First extract the date components from Date by using a Calendar.
		//
		// Create a calendar and we will use it just to pull out the
		// components of the Date object. Note that we can do this because
		// it goes in and comes out the same way regardless of timezone.
		//
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int milli = calendar.get(Calendar.MILLISECOND);

		// Now that we extracted what we need, clear all time settings
		calendar.clear();

		// Set the timezone to the appropriate value specified
		calendar.setTimeZone(zone);

		//
		// Manually set the year, month, and day, hour, minute, second from
		// the saved values.
		//
		calendar.set(year, month - 1, day, hour, minute, second);

		//
		// Manually set the milliseconds from the saved value.
		//
		calendar.set(Calendar.MILLISECOND, milli);

		//
		// Force a compute on the internal calendar fields by getting its
		// time.
		//
		calendar.getTime();

		return calendar;
	}

	/**
	 * Just for testing
	 */
	public static void main(String args[]) {
		//checkXml(new
		// InputSource("/usr/java/Projects/smsgw/psms/sent_folder/NETPACE_PC_20030925_155009.xml"));
		try {
			String strDoc = "<?xml version=\"1.0\"?><!DOCTYPE RESPONSE SYSTEM \"xmlpurchase.dtd\"><RESPONSE version=\"2.0\" transID=\"00010151400008261\"><acctInqRsp><responseStatus status=\"successaa\"/><acct status=\"associated\" language=\"english\"><min>6502797674</min><expDate>08-Jul-2004 01:00:00 AM</expDate><cancelDate>07-Aug-2004 01:00:00 AM</cancelDate><balance>10.00</balance><securityCode>7674</securityCode><roamingAllowed>1</roamingAllowed><internationalAllowed>0</internationalAllowed><homeCarrierCode>00200</homeCarrierCode><messageLevel level=\"full\"/><serviceClass>5</serviceClass></acct></acctInqRsp></RESPONSE>";
			Document doc = getDocument(strDoc);
			Element e = (Element) doc.getElementsByTagName("min").item(0);
			String min = (String) e.getFirstChild().getNodeValue();
			System.out.println("MIN......" + min);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Return a document using the sml string passed
	 * 
	 * @param strDoc -
	 *            The xml String to be parsed
	 * @exception IOException -
	 *                the io exception created when parsing the string
	 * @exception SAXException -
	 *                exception created when parsing the xml string
	 */
	public static Document getDocument(String strDoc) throws IOException,
			SAXException, Exception {
		DOMParser parser = new DOMParser();
		parser
				.setFeature(
						"http://apache.org/xml/features/nonvalidating/load-external-dtd",
						false);
		parser.parse(new InputSource(
				new ByteArrayInputStream(strDoc.getBytes())));
		return parser.getDocument();
	}

	/**
	 * Loads the XML file
	 * 
	 * @param filename
	 *            The file to be reloaded
	 * @exception IOException -
	 *                the io exception created when reading the file
	 * @exception SAXException -
	 *                exception created when parsing the xml config file
	 */
	public static Document loadFile(String filename) throws IOException,
			SAXException, Exception {
		DOMParser parser = new DOMParser();
		Document doc = null;

		if (filename == null) {
			System.out.println("Error: File name is not specified..");
			return null;
		}
		try {
			parser.parse(filename);
			doc = parser.getDocument();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return doc;

	}//End of method loadFile

	/**
	 * Prepends number of characters with the specified characters. This method
	 * will widely be used making the generated XML conforming with the DTD
	 * schema if the data is less then the defined # of characters.
	 * 
	 * @param data -
	 *            Source that need to be transformed into max required length
	 * @param prepandChar -
	 *            Character with which prepend will be done
	 * @param minRequiredLength -
	 *            The lenght of the resultant data
	 * @exception Exception -
	 *                the general exception raised when performing data
	 *                prepending
	 */
	public static String prependData(String data, char prependChar,
			int minReqLength) throws Exception {
		String finalString = "";

		// Perform prepend operation if data is not null
		if (data != null) {
			String prependString = "";
			for (int a = 0; a < (minReqLength - data.length()); a++)
				prependString += prependChar;
			finalString = prependString + data;
		}//End of outer if block

		return finalString;
	}//End of method prependData

	public static String null2Str(Object obj) {
		if (null != obj)
			return obj.toString();
		else
			return "";
	}

	public static String null2Str(String str) {
		if (null != str)
			return str;
		else
			return "";
	}

	public static SAXParserFactory getSaxParserFactory() {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		return factory;
		
	}
	
	public static void startSAXParsing(DefaultHandler handler, String file, String schemaLoc) throws Exception{
		
		SAXParser parser = new SAXParser();
		
		parser.setFeature("http://xml.org/sax/features/validation", true);
		parser.setFeature("http://xml.org/sax/features/namespaces", true);
		parser.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
		parser.setFeature("http://apache.org/xml/features/validation/schema", true);

		parser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation",schemaLoc);		
		parser.setContentHandler(handler);
	
		parser.parse(file);
	}

}