
package com.netpace.aims.dataaccess.valueobjects;

/**
 * This class defines ValueObject ( read-only cache object ) for aims platform.
 *
 * @author    Fawad Sikandar
 * @version   1.0
 */

public final class AimsPlatformVO implements ValueObject {

    private Long   platformId;
    private String platformName;
    private String platformDesc;
    private Long   ringNumber;
    
    /**
     * @param id      Description of Parameter
     * @param name    Description of Parameter
     * @param desc    Description of Parameter
     */
    public  AimsPlatformVO(Long id, String name, String desc, Long ringNumber) {
	platformId = id;
	platformName = name;
	platformDesc = desc;
	this.ringNumber = ringNumber;
    }

    public Object getSortFieldValue() {
      return this.platformName;
    }

    /**
     * Gets the platformDesc attribute of the AimsPlateformVO object
     *
     * @return   The platformDesc value
     */
    public final String getPlatformDesc() {
	return platformDesc;
    }

    /**
     * Gets the platformName attribute of the AimsPlatformVO object
     *
     * @return   The platformName value
     */
    public final String getPlatformName() {
	return platformName;
    }

    /**
     * Gets the platformId attribute of the AimsPlatformVO object
     *
     * @return   The platformId value
     */
    public final Long getPlatformId() {
	return platformId;
    }

    /**
     * Gets the value attribute of the AimsPlatformVO object
     *
     * @return   The value value
     */
    public final String getValue() {
        return this.platformName;
    }

     /**
     * Gets the id attribute of the AimsPlatformVO object
     *
     * @return   The id value
     */
    public final String getID() {
	return String.valueOf(this.platformId);
    }

	public Long getRingNumber() {
		return ringNumber;
	}
}

