/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.utils;

/**
 *
 * @author aahmed
 */
public class Utility {

    public final static String SERVER_ADDRESS = "http://localhost:8080/jersey/services";
    public final static String GET_GRADES = SERVER_ADDRESS + "/gradeservice/grades/";
    public final static String GET_GRADE = SERVER_ADDRESS + "/gradeservice/grade/{grade}";
    public final static String GET_SUBJECT = SERVER_ADDRESS + "/gradeservice/grade/{grade}/subject/{subject}";
    public final static String GET_TOPIC = SERVER_ADDRESS + "/gradeservice/grade/{grade}/subject/{subject}/topic/{topic}";
}
