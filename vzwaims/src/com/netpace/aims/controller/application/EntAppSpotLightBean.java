package com.netpace.aims.controller.application;

import org.apache.log4j.Logger;
import java.util.*;


public class EntAppSpotLightBean {

    static Logger log = Logger.getLogger(EntAppSpotLightBean.class.getName());
    
    private PartnerDetailBean partnerDetail;
    private JMASolutionDetailBean solutionDetail;
			
	public PartnerDetailBean getPartnerDetail() {
	    return this.partnerDetail;
	}
		
	public void setPartnerDetail(PartnerDetailBean partnerDetail) {
	    this.partnerDetail = partnerDetail;
	}
		
    public JMASolutionDetailBean getSolutionDetail() {
        return this.solutionDetail;
    }
        
    public void setSolutionDetail(JMASolutionDetailBean solutionDetail) {
        this.solutionDetail = solutionDetail;
    }
		
    
}

