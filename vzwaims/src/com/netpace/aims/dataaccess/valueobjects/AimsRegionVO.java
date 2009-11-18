
package com.netpace.aims.dataaccess.valueobjects;

/**
 * This class defines ValueObject ( read-only cache object ) for aims region.
 *
 * @author    Fawad Sikandar
 * @version   1.0
 */

public final class AimsRegionVO implements ValueObject {

    private Long   regionId;
    private String regionName;
    private String regionDesc;

    /**
     * @param id      Description of Parameter
     * @param name    Description of Parameter
     * @param desc    Description of Parameter
     */
    public  AimsRegionVO(Long id, String name, String desc) {
	regionId = id;
	regionName = name;
	regionDesc = desc;
    }

    public Object getSortFieldValue() {
      return this.regionName;
    }

    /**
     * Gets the regionDesc attribute of the AimsRegionVO object
     *
     * @return   The regionDesc value
     */
    public final String getRegionDesc() {
	return regionDesc;
    }

    /**
     * Gets the regionName attribute of the AimsRegionVO object
     *
     * @return   The regionName value
     */
    public final String getRegionName() {
	return regionName;
    }

    /**
     * Gets the regionId attribute of the AimsRegionVO object
     *
     * @return   The regionId value
     */
    public final Long getRegionId() {
	return regionId;
    }

    /**
     * Gets the value attribute of the AimsRegionVO object
     *
     * @return   The value value
     */
    public final String getValue() {
        return this.regionName;
    }

     /**
     * Gets the id attribute of the AimsRegionVO object
     *
     * @return   The id value
     */
    public final String getID() {
	return String.valueOf(this.regionId);
    }

}

