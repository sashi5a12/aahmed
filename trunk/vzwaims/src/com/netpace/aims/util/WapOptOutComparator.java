package com.netpace.aims.util;

import java.util.Comparator;

import com.netpace.aims.model.wapoptout.AimsWapOptoutUrls;

public class WapOptOutComparator implements Comparator{

	public int compare(Object o1, Object o2) {
		try {
			if (o1 instanceof AimsWapOptoutUrls && o2 instanceof AimsWapOptoutUrls) {
				AimsWapOptoutUrls firstUrl = (AimsWapOptoutUrls) o1;
				AimsWapOptoutUrls secondUrl = (AimsWapOptoutUrls) o2;
				return firstUrl.getUrlId().compareTo(secondUrl.getUrlId());
			}			
		} catch (NullPointerException e) {
		}
		return 1;
	}	

}
