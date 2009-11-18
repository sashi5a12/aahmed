package com.netpace.aims.util;

import org.apache.commons.lang.StringUtils;

public class WapoptoutUtil {
	
    /**
     * Returns if the provided string is a valid top level domain suffix
     * for example : com, net,gov 	
     * @author mnauman
     * @param string to check
     * @return
     */
	public static boolean isTLDSuffex(String string) {
    	Object[] domains = AimsConstants.TOP_LEVEL_DOMAINS.keySet().toArray();
    	for (int i = 0; i < domains.length; i++) {
			if (string.equalsIgnoreCase(domains[i].toString())) {
				return true;
			}
		}
    	return false;
	}
    
    /**
     * Returns if the provided string is a valid country top level domain
     * for example : us,uk, ca, pk	
     * @author mnauman
     * @param string to check
     * @return
     */
    public static boolean isTLDCountrySuffex(String string) {
    	Object[] domains=  AimsConstants.TOP_LEVEL_COUNTRY_DOMAINS .keySet().toArray(); 
    	for (int i = 0; i < domains.length; i++) {
			if (string.equalsIgnoreCase(domains[i].toString())) {
				return true;
			}
		}
    	return false;
	}
        
    /**
     * Extracts the actual domain from the provided url.
     * URL must not contain sub domain with slashs 
     * for example extracts yahoo.co.uk from mail.yahoo.co.uk
     * 
     * @param completeDomain
     * @return
     */
	public static String extractDomain(String completeDomain) {


    	int	containsSubDomain=completeDomain.indexOf("/",0);

    	if(containsSubDomain == -1){
    		completeDomain=completeDomain;		
    	}else{
    		completeDomain=completeDomain.substring(0,completeDomain.indexOf("/",0));
    	}

    	// Strips * from the end ONLY e.g netpace.com* ==> netpace.com 
    	
    	if( completeDomain.endsWith("*")  ){
    		completeDomain=StringUtils. stripEnd(completeDomain, "*");
    	}
		
		
		String[] domain=StringUtils.split(completeDomain, ".");
		
		int index=domain.length-1;
		
		String urlDomain;
		String urlCountryTopLevelDomain="";
		String urlTopLevelDomain="";

		if(	 WapoptoutUtil.isTLDCountrySuffex(domain[index])  ){
			urlCountryTopLevelDomain=domain[index];
			index--;
		}
		if ( 	WapoptoutUtil.isTLDSuffex(domain[index])		) {
			urlTopLevelDomain=domain[index];
			index--;
		}
		
		if(index== domain.length-1){ // does not contains valid toplevel or country domain
			return  "";   
		}


		urlDomain = domain[index];
		
		StringBuffer name=new StringBuffer();

		if(!StringFuncs.isNullOrEmpty(urlDomain)){
			name.append(urlDomain);
		}
		if(!StringFuncs.isNullOrEmpty(urlTopLevelDomain)){
			name.append(".").append(urlTopLevelDomain);
		}
		if(!StringFuncs.isNullOrEmpty(urlCountryTopLevelDomain)){
			name.append(".").append(urlCountryTopLevelDomain);
		}
		return name.toString();
	}
    

}
