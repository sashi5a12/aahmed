package com.netpace.device.enums;

/**
 *
 * @author nraza
 */
public enum ActivationType {
    OFFER_TO_JOIN_COMPANY("OFFER_TO_JOIN_COMPANY"),
    OFFER_TO_JOIN_AS_ADMIN("OFFER_TO_JOIN_AS_ADMIN"),
    REGISTER_PARTNER_USER("REGISTER_PARTNER_USER");
    
    private String label;
    
    private ActivationType(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
    }
}
