package com.sample.utils;

import java.util.List;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultDocument;

public class XMLBuilder {

    private final static String SERVER_ADDRESS="http://localhost:8080/cxf/services";
    
    public static String getAllGrades() {
        Document doc = new DefaultDocument();
        doc.addElement("grades");
        Element rootElement = doc.getRootElement();

        List<String> grades = getGrades();
        for (String grade : grades) {
            Element gradeElement = rootElement.addElement("grade");
            gradeElement.addAttribute("id", grade);
            gradeElement.addAttribute("href", SERVER_ADDRESS+"/gradeservice/grade/" + grade);
        }
        return doc.asXML();
    }

    public static String getAllSubjects(Integer grade) {
        Document doc = new DefaultDocument();
        doc.addElement("subjects");
        Element rootElement = doc.getRootElement();

        List<String> subjects = getSubjects(grade);
        for (String subject : subjects) {
            Element subjectElement = rootElement.addElement("subject");
            subjectElement.addAttribute("id", subject);
            subjectElement.addAttribute("href", SERVER_ADDRESS+"/gradeservice/grade/" + grade
                    + "/subject/" + subject);
        }
        return doc.asXML();
    }

    public static String getAllTopics(Integer grade, String subject) {
        Document doc = new DefaultDocument();
        doc.addElement("topics");
        Element rootElement = doc.getRootElement();

        List<String> topics = getTopics(subject);
        for (String topic : topics) {
            Element topicElement = rootElement.addElement("topic");
            topicElement.addAttribute("id", topic);
            topicElement.addAttribute("href", SERVER_ADDRESS+"/gradeservice/grade/" + grade +
                    "/subject/" + subject + "/topic/" + topic);
        }
        return doc.asXML();
    }

    public static String getTopicContent(Integer grade, String subject, String topic) {
        Document doc = new DefaultDocument();
        doc.addElement("content");
        Element rootElement = doc.getRootElement();

        Element contentElement = rootElement.addElement("content");
        contentElement.addAttribute("id", "content");
        contentElement.addAttribute("href", SERVER_ADDRESS+"/gradeservice/grade/" + grade +
                "/subject/" + subject + "/topic/" + topic + "/content/" + topic + ".pdf");

        return doc.asXML();
    }

    private static List<String> getGrades() {
        List<String> gradesList = new ArrayList<String>();
        gradesList.add("1");
        gradesList.add("2");
        gradesList.add("3");
        gradesList.add("4");
        gradesList.add("5");
        gradesList.add("6");
        gradesList.add("10");
        return gradesList;
    }

    private static List<String> getSubjects(Integer grade) {
        List<String> subList = new ArrayList<String>();
        switch (grade) {
            case 10:
                subList.add("Math");
                subList.add("Reading");
                subList.add("Biology");
                return subList;
            case 1:
            case 2:
            case 3:
            case 4:
                subList.add("Math");
                subList.add("Art");
                subList.add("Lit");
                subList.add("Photograpy");
                return subList;
            default:
                subList.add("Java");
                subList.add(".Net");
                return subList;
        }
    }

    private static List<String> getTopics(String subject) {
        List<String> topicList = new ArrayList<String>();
        if ("Java".equalsIgnoreCase(subject)) {
            topicList.add("An Overview of Java");
            topicList.add("Introduction to classes");
            topicList.add("Packages and Interfaces");
            topicList.add("Exception Handling");
            topicList.add("Mutithreading");
            topicList.add("String Handling");
            topicList.add("Colletions Framework");
        }

        if ("Math".equalsIgnoreCase(subject)) {
            topicList.add("Mathematics and art");
            topicList.add("Philosophy of mathematics");
            topicList.add("Algebra");
            topicList.add("Trignometry");
            topicList.add("Calculus");
        }
        return topicList;
    }

}
