
package com.netpace.aims.dataaccess.valueobjects;

/**
 * This class defines ValueObject ( read-only cache object ) for aims lifecycle phases.
 *
 * @author    Ahson Imtiaz
 * @version   1.0
 */

public final class AimsLifecycleVO implements ValueObject {

    private Long   phaseId;
    private String phaseName;

    /**
     * @param id      Description of Parameter
     * @param name    Description of Parameter
     */
    public  AimsLifecycleVO(Long id, String name) {
	phaseId = id;
	phaseName = name;
    }

    public Object getSortFieldValue() {
      return this.phaseName;
    }

    /**
     * 
     *
     * @return   The phaseName value
     */
    public final String getPhaseName() {
	return phaseName;
    }

    /**
     * Gets the platformId attribute of the AimsPlatformVO object
     *
     * @return   The phaseId value
     */
    public final Long getPhaseId() {
	return phaseId;
    }

    /**
     * 
     *
     * @return   The value value
     */
    public final String getValue() {
        return this.phaseName;
    }

     /**
     *
     * @return   The id value
     */
    public final String getID() {
	return String.valueOf(this.phaseId);
    }

}

