package com.netpace.vzdn.dao;

import java.io.Serializable;

import com.netpace.vzdn.model.VzdnNewsLetterOptOutRecipients;

public interface INewsLetterOptOutDao<T,PK extends Serializable> extends IGenericDAO<T, PK>{
	public VzdnNewsLetterOptOutRecipients getRecordByUserId(Integer userId) throws Exception;
}