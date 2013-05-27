package com.packtpub.ch11.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.packtpub.springhibernate.model.Student;

public interface StudentDao {
	public List getAllStudents() throws HibernateException;

	public Student getStudent(Long stdId) throws HibernateException;

	public Student saveStudent(Student std) throws HibernateException;

	public Student removeStudent(Student std) throws HibernateException;

	public Student updateStudent(Student std) throws HibernateException;
}
