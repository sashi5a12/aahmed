package com.packtpub.ch11.service;

import java.util.List;

import com.packtpub.ch11.exception.ServiceException;
import com.packtpub.springhibernate.model.Student;

public interface StudentService {
    public List getAllStudents() throws ServiceException;
    public Student getStudent(Long stdId) throws ServiceException;
    public Student saveStudent(Student std) throws ServiceException;
    public Student removeStudent(Student std) throws ServiceException;
    public Student updateStudent(Student std) throws ServiceException;
}