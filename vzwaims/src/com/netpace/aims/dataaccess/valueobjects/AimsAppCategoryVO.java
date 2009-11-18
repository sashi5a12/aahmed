
package com.netpace.aims.dataaccess.valueobjects;

/**
 * This class defines ValueObject ( read-only cache object ) for aims appCategory.
 *
 * @author    Fawad Sikandar
 * @version   1.0
 */

public final class AimsAppCategoryVO implements ValueObject {

    private Long   appCategoryId;
    private String appCategoryName;
    private String appCategoryDesc;

    /**
     * @param id      Description of Parameter
     * @param name    Description of Parameter
     * @param desc    Description of Parameter
     */
    public  AimsAppCategoryVO(Long id, String name, String desc) {
	appCategoryId = id;
	appCategoryName = name;
	appCategoryDesc = desc;
    }

    public Object getSortFieldValue() {
      return this.appCategoryName;
    }

    /**
     * Gets the appCategoryDesc attribute of the AimsAppCategoryVO object
     *
     * @return   The appCategoryDesc value
     */
    public final String getAppCategoryDesc() {
	return appCategoryDesc;
    }

    /**
     * Gets the appCategoryName attribute of the AimsAppCategoryVO object
     *
     * @return   The appCategoryName value
     */
    public final String getAppCategoryName() {
	return appCategoryName;
    }

    /**
     * Gets the appCategoryId attribute of the AimsAppCategoryVO object
     *
     * @return   The appCategoryId value
     */
    public final Long getAppCategoryId() {
	return appCategoryId;
    }

    /**
     * Gets the value attribute of the AimsAppCategoryVO object
     *
     * @return   The value value
     */
    public final String getValue() {
        return this.appCategoryName;
    }

     /**
     * Gets the id attribute of the AimsAppCategoryVO object
     *
     * @return   The id value
     */
    public final String getID() {
	return String.valueOf(this.appCategoryId);
    }

}

