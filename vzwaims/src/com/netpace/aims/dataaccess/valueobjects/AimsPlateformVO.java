
package com.netpace.aims.dataaccess.valueobjects;

/**
 * This class defines ValueObject ( read-only cache object ) for aims plateform.
 *
 * @author    Fawad Sikandar
 * @version   1.0
 */

public final class AimsPlateformVO implements ValueObject {

    private Long   plateformId;
    private String plateformName;
    private String plateformDesc;

    /**
     * @param id      Description of Parameter
     * @param name    Description of Parameter
     * @param desc    Description of Parameter
     */
    public  AimsPlateformVO(Long id, String name, String desc) {
	plateformId = id;
	plateformName = name;
	plateformDesc = desc;
    }

    public Object getSortFieldValue() {
      return this.plateformName;
    }

    /**
     * Gets the plateformDesc attribute of the AimsPlateformVO object
     *
     * @return   The plateformDesc value
     */
    public final String getPlateformDesc() {
	return plateformDesc;
    }

    /**
     * Gets the plateformName attribute of the AimsPlateformVO object
     *
     * @return   The plateformName value
     */
    public final String getPlateformName() {
	return plateformName;
    }

    /**
     * Gets the plateformId attribute of the AimsPlateformVO object
     *
     * @return   The plateformId value
     */
    public final Long getPlateformId() {
	return plateformId;
    }

    /**
     * Gets the value attribute of the AimsPlateformVO object
     *
     * @return   The value value
     */
    public final String getValue() {
        return this.plateformName;
    }

     /**
     * Gets the id attribute of the AimsPlateformVO object
     *
     * @return   The id value
     */
    public final String getID() {
	return String.valueOf(this.plateformId);
    }

}

