/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.ch01;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import java.util.List;
import java.util.ArrayList;

public class MySAXParser {

    public static void main(String[] args) {
        try {
            MySAXParser mySAXParser = new MySAXParser();
            //To print the data from java objects
            List<Student> studentsList = mySAXParser.getStudentData();
            for (Student student : studentsList) {
                System.out.println(" Id: " + student.getId());
                System.out.println(" firstName: " + student.getFirstName());
                System.out.println(" lastName: " + student.getLastName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Parse the output xml to get the result
    private List<Student> getStudentData() throws Exception {
        //Parse the output xml to get the result
        StudentInfoXmlSchemaParser studentOutputParser = new StudentInfoXmlSchemaParser();
        parseOutputXML(studentOutputParser);
        List<Student> studentsList = studentOutputParser.studentsList;
        return studentsList;
    }

    //Parse the xml file using XMLReader
    private void parseOutputXML(DefaultHandler xmlHandler) throws Exception {
        try {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setContentHandler(xmlHandler);
            xmlReader.setErrorHandler(new SAXErrorHandler());
            xmlReader.parse("/Users/aahmed/SkyDrive/eclipse-projects/WebServices/Imbibing_JWS/src/main/java/com/sample/ch01/students.xml");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("ErrorParsing Result", ex);
        }
    }

    //Parse the xml file, extract the data from XML and populate to java objects.
    private class StudentInfoXmlSchemaParser extends AbstractXmlSchemaParser {

        List<Student> studentsList = new ArrayList<Student>();
        Student student = null;

        @Override
        protected void startTag(String name) {
            if ("student".equalsIgnoreCase(name)) {
                student = new Student();
                studentsList.add(student);
            }
        }

        @Override
        protected void endTag(String name, String tagContent) {
            if (tagContent != null) {
                if ("id".equalsIgnoreCase(name)) {
                    student.setId(tagContent);
                } else if ("firstName".equalsIgnoreCase(name)) {
                    student.setFirstName(tagContent);
                } else if ("lastName".equalsIgnoreCase(name)) {
                    student.setLastName(tagContent);
                }
            }
        }
    }
}