package com.sample.ch04;

import com.sample.utils.XMLBuilder;

public class GradeManagerImpl implements GradeManager {

    public String getGrades() {
        return XMLBuilder.getAllGrades();
    }

    public String getGradeSubjects(Integer grade) {
        return XMLBuilder.getAllSubjects(grade);
    }

    public String getSubjectTopics(Integer grade, String subject) {
        return XMLBuilder.getAllTopics(grade, subject);
    }

    public String getTopicContent(Integer grade, String subject, String topic) {
        return XMLBuilder.getTopicContent(grade, subject, topic);
    }
    
}
