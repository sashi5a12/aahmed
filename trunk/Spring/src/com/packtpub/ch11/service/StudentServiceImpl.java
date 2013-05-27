package com.packtpub.ch11.service;

import java.util.List;

import com.packtpub.ch11.dao.StudentDao;
import com.packtpub.ch11.exception.ServiceException;
import com.packtpub.ch11.notification.NotificationService;
import com.packtpub.springhibernate.model.Student;

public class StudentServiceImpl implements StudentService {
	StudentDao studentDao;
	NotificationService notificationService;

	// setter methods for dependency injection
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	public List getAllStudents() throws ServiceException {
		return studentDao.getAllStudents();
		/*try {
		} catch (HibernateException e) {
			notificationService.notify(e.getMessage());
			throw new ServiceException(e);
		}*/
	}

	@Override
	public Student getStudent(Long stdId) throws ServiceException {
		Student std=studentDao.getStudent(stdId);
//		std.getStudentId();
		return std;
		/*try {
		} catch (HibernateException e) {
			notificationService.notify(e.getMessage());
			throw new ServiceException(e);
		}*/
	}

	@Override
	public Student saveStudent(Student std) throws ServiceException {
		return studentDao.saveStudent(std);
		/*try {
		} catch (HibernateException e) {
			notificationService.notify(e.getMessage());
			throw new ServiceException(e);
		}*/
	}

	@Override
	public Student removeStudent(Student std) throws ServiceException {
		return studentDao.removeStudent(std);
		/*try {
		} catch (HibernateException e) {
			notificationService.notify(e.getMessage());
			throw new ServiceException(e);
		}*/
	}

	@Override
	public Student updateStudent(Student std) throws ServiceException {
		return studentDao.updateStudent(std);
		/*try {
		} catch (HibernateException e) {
			notificationService.notify(e.getMessage());
			throw new ServiceException(e);
		}*/
	}

}
